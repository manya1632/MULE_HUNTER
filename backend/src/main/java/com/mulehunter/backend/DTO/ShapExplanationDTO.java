package com.mulehunter.backend.DTO;

import java.util.List;
import java.util.Map;

public class ShapExplanationDTO {

    private Long nodeId;
    private Double anomalyScore;
    private List<Map<String, Double>> topFactors;
    private String model;
    private String source;

    public ShapExplanationDTO() {
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public double getAnomalyScore() {
        return anomalyScore;
    }

    public void setAnomalyScore(double anomalyScore) {
        this.anomalyScore = anomalyScore;
    }

    public List<Map<String, Double>> getTopFactors() {
        return topFactors;
    }

    public void setTopFactors(List<Map<String, Double>> topFactors) {
        this.topFactors = topFactors;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
