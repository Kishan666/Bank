package com.example.kishan.bankingapp3.FrontEnd.Factories.transaction;


import com.example.kishan.bankingapp3.FrontEnd.Domain.transactions.Implementation.BalanceEnquiry;
import com.example.kishan.bankingapp3.FrontEnd.Domain.transactions.Implementation.Deposit;
import com.example.kishan.bankingapp3.FrontEnd.Domain.transactions.Transaction;

/**
 * Created by Kishan on 2017-10-26.
 */
public class TransactionFactory {
    public static Transaction getTransaction(String value){
        Transaction chain = setUpchain();
        return chain.handleRequest(value);
    }

    public static Transaction setUpchain(){
        Transaction balanceEnquiry = new BalanceEnquiry();
        Transaction deposit = new Deposit();


        balanceEnquiry.setNextTransaction(deposit);


        return balanceEnquiry;
    }
}
