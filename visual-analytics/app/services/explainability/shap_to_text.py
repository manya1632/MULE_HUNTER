from typing import List, Dict
from app.services.explainability.explanation_mapper import (
    FEATURE_EXPLANATIONS,
    ANOMALY_SCORE_THRESHOLD,
)


def generate_human_explanations(
    shap_data: List[Dict]
) -> List[Dict]:
    """
    Converts SHAP feature impacts into human-readable
    fraud explanations.

    Guarantees:
    - Every node gets at least one explanation
    - Normal nodes are NEVER accused
    - Explanations align with anomaly logic
    """

    if not shap_data:
        return []

    explanations_output = []

    for node in shap_data:
        node_id = node.get("node_id")
        score = node.get("anomaly_score", 0.0)
        top_factors = node.get("top_factors", [])

        reasons = []

       
        # Case 1: Normal node
        
        if score < ANOMALY_SCORE_THRESHOLD:
            reasons = [
                "No anomalous transaction behavior detected",
                "Account activity is consistent with normal usage patterns",
            ]

       
        # Case 2: Anomalous node
        
        else:
            for factor in top_factors:
                feature = factor.get("feature")
                impact = factor.get("impact", 0)

                if feature not in FEATURE_EXPLANATIONS:
                    continue

                # For anomalous nodes, any strong contributing feature is risky
                explanation = FEATURE_EXPLANATIONS[feature]["positive"]

                if explanation not in reasons:
                    reasons.append(explanation)

            # Fallback (important)
            if not reasons:
                reasons.append(
                    "Unusual transaction patterns detected compared to similar accounts"
                )

            # Reinforce severity
            if score >= 0.8:
                reasons.append("High overall anomaly score indicates elevated risk")

        explanations_output.append({
            "node_id": node_id,
            "reasons": reasons,
            "source": "shap-mapper"
        })

    return explanations_output
