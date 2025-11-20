package com.example.Splitter.Model;

import com.example.Splitter.Enum.SplitType;
import lombok.Data;

import java.util.List;

@Data
public class TransactionRequest {
    private String groupId;
    private String senderId;
    private Double amount;
    private List<Double> amounts;
    private String desc;
    private List<String> receiverIds;
    private SplitType splitType;
}
