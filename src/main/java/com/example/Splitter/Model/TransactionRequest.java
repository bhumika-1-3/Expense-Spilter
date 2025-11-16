package com.example.Splitter.Model;

import lombok.Data;

import java.util.List;

@Data
public class TransactionRequest {
    private String groupId;
    private String senderId;
    private Double amount;
    private String desc;
    private List<String> receiverIds;
}
