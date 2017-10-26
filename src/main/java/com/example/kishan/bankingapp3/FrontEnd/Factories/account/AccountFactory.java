package com.example.kishan.bankingapp3.FrontEnd.Factories.account;


import com.example.kishan.bankingapp3.FrontEnd.Domain.account.Account;

import java.util.Map;

/**
 * Created by Ferin on 2016-08-28.
 */
public interface AccountFactory {
    Account createAccount(Map<String, Object> accountDetails);
}
