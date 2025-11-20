package com.example.Splitter.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
//one sender multiple receiver
public class TransactionSplit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String splitId;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    @JsonIgnore
    private AppTransaction transaction;

    @ManyToOne
    @JoinColumn(name = "owed_to_user_id")
    private AppUser owedToUser;  // payer

    @ManyToOne
    @JoinColumn(name = "owed_by_user_id")
    private AppUser owedByUser;  // receiver

    private Double amount;

}
