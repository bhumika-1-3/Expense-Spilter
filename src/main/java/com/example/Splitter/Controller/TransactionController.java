package com.example.Splitter.Controller;

import com.example.Splitter.Model.AppTransaction;
import com.example.Splitter.Model.TransactionRequest;
import com.example.Splitter.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public String addTransaction(@RequestBody TransactionRequest req){
        try{
            return transactionService.addTransaction(req);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
