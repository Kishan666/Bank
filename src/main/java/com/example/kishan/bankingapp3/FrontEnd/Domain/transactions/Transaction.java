package com.example.kishan.bankingapp3.FrontEnd.Domain.transactions;

import java.util.Map;

/**
 * Created by Kishan on 2017-10-26.
 */
public abstract class Transaction {
    public Transaction nextTransaction;

    public void setNextTransaction(Transaction nextTransaction){
        this.nextTransaction = nextTransaction;
    }

    public abstract Transaction handleRequest(String request);
    public abstract boolean performTransaction(Map<String, Object> data);
}