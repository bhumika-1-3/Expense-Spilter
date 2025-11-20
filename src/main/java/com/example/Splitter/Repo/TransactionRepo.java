package com.example.Splitter.Repo;

import com.example.Splitter.Entity.AppTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<AppTransaction,String> {
}
