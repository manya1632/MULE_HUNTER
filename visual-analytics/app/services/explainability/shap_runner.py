import pandas as pd
import shap
from sklearn.ensemble import RandomForestClassifier
from typing import List, Dict



FEATURE_COLS = [
    "in_degree",
    "out_degree",
    "total_incoming",
    "total_outgoing",
    "risk_ratio",
    "tx_velocity",
    "account_age_days",
    "balance",
]

TOP_K = 3
MIN_SAMPLES = 10
MIN_ANOMALIES = 2




def run_shap(scored_nodes: List[Dict]) -> List[Dict]:
    """
    Generates SHAP explanations for anomalous nodes only
    using a surrogate RandomForest model.

    scored_nodes:
        normalized nodes with:
        - node_id
        - anomaly_score
        - is_anomalous
    """

    if not scored_nodes:
        return []

    df = pd.DataFrame(scored_nodes)

    

    required_cols = FEATURE_COLS + ["node_id", "is_anomalous", "anomaly_score"]
    missing = [c for c in required_cols if c not in df.columns]
    if missing:
        raise ValueError(f"Missing required columns for SHAP: {missing}")

    
    if len(df) < MIN_SAMPLES or df["is_anomalous"].sum() < MIN_ANOMALIES:
        return []

   

    X = df[FEATURE_COLS].fillna(0)
    y = df["is_anomalous"]

    
    # Train surrogate model
    

    model = RandomForestClassifier(
        n_estimators=200,
        max_depth=6,
        random_state=42,
        class_weight="balanced",
        n_jobs=-1,
    )
    model.fit(X, y)

  
    # SHAP
   

    explainer = shap.TreeExplainer(model)
    shap_values = explainer.shap_values(X)

    # Handle multi-class / binary safely
    if isinstance(shap_values, list):
        shap_anomaly = shap_values[1]
    elif len(shap_values.shape) == 3:
        shap_anomaly = shap_values[:, :, 1]
    else:
        shap_anomaly = shap_values

   
    # Build explanations
    

    explanations = []

    anomalous_indices = df.index[df["is_anomalous"] == 1].tolist()

    for idx in anomalous_indices:
        impacts = shap_anomaly[idx]

        top_features = sorted(
            zip(FEATURE_COLS, impacts),
            key=lambda x: abs(x[1]),
            reverse=True
        )[:TOP_K]

        row = df.iloc[idx]

        explanations.append({
            "node_id": int(row["node_id"]),
            "anomaly_score": round(float(row["anomaly_score"]), 6),
            "top_factors": [
                {
                    "feature": feature,
                    "impact": round(float(impact), 6)
                }
                for feature, impact in top_features
            ],
            "model": "rf_surrogate",
            "source": "shap",
        })

    return explanations
