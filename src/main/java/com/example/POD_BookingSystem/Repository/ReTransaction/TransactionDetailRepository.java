package com.example.POD_BookingSystem.Repository.ReTransaction;

import com.example.POD_BookingSystem.Entity.ETransaction.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail,String> {
    @Query(value = "Select transaction_detail_id from transaction_detail order by transaction_detail_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();
}
