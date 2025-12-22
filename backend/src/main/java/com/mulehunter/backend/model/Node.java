package com.mulehunter.backend.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "nodes")
public class Node {

    @Id
    private String id; //mongo_id

    private Integer nodeId;

    private Integer accountAgeDays;
    private Double balance;
    private Double inOutRatio;
    private Double pagerank;
    private Double txVelocity;
    private Integer inDegree;
    private Integer outDegree;
    private Double totalIncoming;
    private Double totalOutgoing;
    private Double riskRatio;

    private Integer isAnomalous;
    private List<String> reasons;
    private List<ShapFactor> shapFactors;

}
