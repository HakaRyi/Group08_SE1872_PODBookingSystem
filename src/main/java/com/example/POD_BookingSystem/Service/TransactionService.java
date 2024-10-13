package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.Repository.ReTransaction.TransactionDetailRepository;
import com.example.POD_BookingSystem.Repository.ReTransaction.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionDetailRepository transactionDetailRepository;

    private String GenerateTransactionId(){
        String id = transactionRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(3))+1;
            return String.format("T-%02d", number);
        }
        return "T-01";
    }
    private String GenerateTransactionDetailId(){
        String id = transactionDetailRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(4))+1;
            return String.format("TD-%02d", number);
        }
        return "TD-01";
    }
}
