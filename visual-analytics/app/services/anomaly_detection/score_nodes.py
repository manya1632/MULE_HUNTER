from typing import Dict, List

from app.services.anomaly_detection.eif_detector import (
    train_isolation_forest,
    score_nodes as _score_nodes,
)


def score_single_node(
    enriched_node: Dict,
    reference_nodes: List[Dict],
) -> float:
    """
    Scores ONE node relative to a reference population.

    Args:
        enriched_node   → normalized target node (snake_case)
        reference_nodes → normalized population (excluding target)

    Returns:
        anomaly_score (float)
    """

    # Safety: unsupervised ML is meaningless on tiny populations
    if not reference_nodes or len(reference_nodes) < 10:
        return 0.0

    
    # Train model on reference population
    
    model, scaler = train_isolation_forest(reference_nodes)

   
    # Score ONLY the target node
   
    scores = _score_nodes(
        model=model,
        scaler=scaler,
        nodes=[enriched_node],
    )

    if not scores:
        return 0.0

    return float(scores[0]["anomaly_score"])
