package com.example.Splitter.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class AppTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private AppGroup group;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private AppUser sender;
//    private SplitType splitType;
    private String desc;
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<TransactionSplit> splits;
}
