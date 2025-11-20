package com.example.Splitter.Model;


import lombok.Data;

import java.util.List;

@Data
public class AllTransactionInGroup {
    private Double amount;
    private String senderName;
    private String desc;
    private List<String> receiverNames;
}
