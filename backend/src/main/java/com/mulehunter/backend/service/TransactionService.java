package com.mulehunter.backend.service;

import org.springframework.stereotype.Service;

import com.mulehunter.backend.client.FraudClient;
import com.mulehunter.backend.client.FraudResponse;
import com.mulehunter.backend.model.Transaction;
import com.mulehunter.backend.model.TransactionRequest;
import com.mulehunter.backend.repository.TransactionRepository;
import com.mulehunter.backend.websocket.FraudAlertEvent;
import com.mulehunter.backend.websocket.FraudAlertPublisher;

import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final FraudClient fraudClient;
    private final FraudAlertPublisher alertPublisher;

    public TransactionService(TransactionRepository repository,
                              FraudClient fraudClient,
                              FraudAlertPublisher alertPublisher) {
        this.repository = repository;
        this.fraudClient = fraudClient;
        this.alertPublisher = alertPublisher;
    }

    public Mono<Transaction> createTransaction(TransactionRequest request) {

        Transaction tx = Transaction.from(request);

        int nodeId = Integer.parseInt(tx.getSourceAccount());

        return fraudClient.checkFraud(nodeId)
                .onErrorReturn(new FraudResponse()) // fallback handled below
                .flatMap(response -> {

                    boolean isFraud = tx.isSuspectedFraud(); 
                    tx.setSuspectedFraud(isFraud);

                    if (isFraud) {
                        FraudAlertEvent event = new FraudAlertEvent(
                                tx.getSourceAccount(),
                                response.getRisk_score(),
                                response.getVerdict()
                        );
                        alertPublisher.publish(event);
                        

                    }

                    return repository.save(tx);
                });
    }
}
