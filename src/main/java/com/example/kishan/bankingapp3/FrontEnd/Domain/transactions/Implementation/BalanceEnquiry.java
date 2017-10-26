package com.example.kishan.bankingapp3.FrontEnd.Domain.transactions.Implementation;



import com.example.kishan.bankingapp3.FrontEnd.Domain.account.Account;
import com.example.kishan.bankingapp3.FrontEnd.Domain.transactions.Transaction;

import java.util.Map;

/**
 * Created by Kishan on 2017-10-26.
 */
public class BalanceEnquiry extends Transaction {

    public BalanceEnquiry(){}

    @Override
    public boolean performTransaction(Map<String, Object> data) {
        Account account = (Account) data.get("account");

        System.out.println("Balance Enquiry for: " + account.getClient().getName());

        return account.getAccountNumber() != null;
    }

    @Override
    public Transaction handleRequest(String request) {
        if(request.equalsIgnoreCase("balanceEnquiry")){
            return new BalanceEnquiry();
        }
        else{
            if(nextTransaction != null){
                return nextTransaction.handleRequest(request);
            }
        }
        return null;
    }

}
