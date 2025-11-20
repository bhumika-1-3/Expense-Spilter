package com.example.Splitter.Service;

import com.example.Splitter.Entity.AppGroup;
import com.example.Splitter.Entity.AppTransaction;
import com.example.Splitter.Entity.AppUser;
import com.example.Splitter.Entity.TransactionSplit;
import com.example.Splitter.Enum.SplitType;
import com.example.Splitter.Model.*;
import com.example.Splitter.Repo.GroupRepo;
import com.example.Splitter.Repo.TransactionRepo;
import com.example.Splitter.Repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public String addTransaction(TransactionRequest req){
        try{
            AppGroup group = groupRepo.findById(req.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group not found"));
            AppUser sender = userRepo.findById(req.getSenderId())
                    .orElseThrow(() -> new RuntimeException("Sender not found"));

//                check if all users exist in group
            List<AppUser> receivers = new ArrayList<>();
            for (String id : req.getReceiverIds()) {
                AppUser user = userRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Receiver not found: " + id));
                receivers.add(user);
            }

            AppTransaction transaction = new AppTransaction();
            transaction.setGroup(group);
            transaction.setSender(sender);
            List<TransactionSplit> splits = new ArrayList<>();

            double totalAmount = 0.0;
            if(req.getSplitType() == SplitType.EQUAL){
                double splitAmount = req.getAmount() / receivers.size(); // equal split for now
                for (AppUser receiver : receivers) {
                    TransactionSplit split = new TransactionSplit();
                    split.setTransaction(transaction);
                    split.setOwedByUser(receiver);
                    split.setOwedToUser(sender);
                    split.setAmount(splitAmount);
                    splits.add(split);
                    totalAmount+=splitAmount;
                }
            }
            else if(req.getSplitType() == SplitType.UNEQUAL){
                List<Double> splitAmount = req.getAmounts();
                if(splitAmount.size() != req.getReceiverIds().size()) throw new RuntimeException("Unequal amount and Receiver ids");
                int i = 0;
                for (AppUser receiver : receivers) {
                    TransactionSplit split = new TransactionSplit();
                    double amountForReceiver = splitAmount.get(i);
                    split.setTransaction(transaction);
                    split.setOwedByUser(receiver);
                    split.setOwedToUser(sender);
                    split.setAmount(amountForReceiver);
                    splits.add(split);
                    totalAmount+=amountForReceiver;
                    i++;
                }
            }

            transaction.setDesc(req.getDesc());
            transaction.setAmount(totalAmount);
            transaction.setSplits(splits);
            transactionRepo.save(transaction);

            return "Transaction added";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<AllTransactionInGroup> allTransactionInTheGroup(String id){
        try{
            AppGroup group = groupRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Group not found"));


            List<AppTransaction> appTransactions = group.getTransactions();

            List<AllTransactionInGroup> allTransactionInGroups = new ArrayList<>();
            for(AppTransaction transaction : appTransactions){
                AllTransactionInGroup allTransactionInGroup = getAllTransactionInGroup(transaction, group);
                allTransactionInGroups.add(allTransactionInGroup);
            }
            return allTransactionInGroups;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private static AllTransactionInGroup getAllTransactionInGroup(AppTransaction transaction, AppGroup group) {
        AllTransactionInGroup allTransactionInGroup = new AllTransactionInGroup();
        List <String> receiverNames = new ArrayList<>();
        allTransactionInGroup.setAmount(transaction.getAmount());
        allTransactionInGroup.setSenderName(transaction.getSender().getName());
        allTransactionInGroup.setDesc(transaction.getDesc());
        for(TransactionSplit split : transaction.getSplits()){
            receiverNames.add(split.getOwedByUser().getName());
        }

        allTransactionInGroup.setReceiverNames(receiverNames);
        return allTransactionInGroup;
    }

}
