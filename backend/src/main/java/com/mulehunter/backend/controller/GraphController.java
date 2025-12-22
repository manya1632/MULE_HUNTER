package com.mulehunter.backend.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mulehunter.backend.DTO.GraphLinkDTO;
import com.mulehunter.backend.DTO.GraphNodeDTO;
import com.mulehunter.backend.DTO.GraphResponseDTO;
import com.mulehunter.backend.model.Transaction;
import com.mulehunter.backend.repository.TransactionRepository;

@RestController
@RequestMapping("/api/graph")
public class GraphController {

    private final TransactionRepository transactionRepository;

    public GraphController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping
    public GraphResponseDTO getGraph() {

        List<Transaction> txs = (List<Transaction>) transactionRepository.findAll();

        // Build links (edges)
        List<GraphLinkDTO> links = txs.stream()
                .map(t -> new GraphLinkDTO(
                        t.getSourceAccount(),
                        t.getTargetAccount(),
                        t.getAmount()
                ))
                .toList();

        // Build nodes (unique accounts)
        Map<String, Boolean> fraudMap = new HashMap<>();

        for (Transaction t : txs) {
            fraudMap.put(t.getSourceAccount(), t.isSuspectedFraud());
            fraudMap.put(t.getTargetAccount(), t.isSuspectedFraud());
        }

        List<GraphNodeDTO> nodes = fraudMap.entrySet()
                .stream()
                .map(e -> new GraphNodeDTO(
                        e.getKey(),
                        e.getValue()
                ))
                .toList();

        return new GraphResponseDTO(nodes, links);
    }
    @GetMapping("/node/{id}")
    public Map<String, Object> getNodeDetails(@PathVariable String id) {

        List<Transaction> txs = (List<Transaction>) transactionRepository.findAll();

        BigDecimal totalIn = txs.stream()
                .filter(t -> id.equals(t.getTargetAccount()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOut = txs.stream()
                .filter(t -> id.equals(t.getSourceAccount()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        boolean suspected = txs.stream()
                .anyMatch(t ->
                        id.equals(t.getSourceAccount()) ||
                        id.equals(t.getTargetAccount())
                );

        return Map.of(
                "accountId", id,
                "totalIn", totalIn,
                "totalOut", totalOut,
                "suspectedFraud", suspected
        );
    }

}
