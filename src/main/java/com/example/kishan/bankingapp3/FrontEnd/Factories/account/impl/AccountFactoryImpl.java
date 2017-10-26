package com.example.kishan.bankingapp3.FrontEnd.Factories.account.impl;



import com.example.kishan.bankingapp3.FrontEnd.Domain.account.Account;
import com.example.kishan.bankingapp3.FrontEnd.Domain.client.Client;
import com.example.kishan.bankingapp3.FrontEnd.Factories.account.AccountFactory;

import java.util.Map;

/**
 * Created by Ferin on 2016-08-28.
 */
public class AccountFactoryImpl implements AccountFactory {

    @Override
    public Account createAccount(Map<String, Object> accountDetails) {
        return new Account.Builder()
                .accountNumber(accountDetails.get("accountNumber").toString())
                .accountType(accountDetails.get("accountType").toString())
                .balance(accountDetails.get("balance").toString())
                .limit(accountDetails.get("limit").toString())

                .client((Client) accountDetails.get("client"))
                .id(Long.parseLong(accountDetails.get("id").toString()))
                .build();
    }
}
