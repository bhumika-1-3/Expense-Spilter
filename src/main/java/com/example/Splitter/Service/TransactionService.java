package com.example.Splitter.Service;

import com.example.Splitter.Model.*;
import com.example.Splitter.Repo.GroupRepo;
import com.example.Splitter.Repo.TransactionRepo;
import com.example.Splitter.Repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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

}
