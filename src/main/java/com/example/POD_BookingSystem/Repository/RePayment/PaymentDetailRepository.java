package com.example.POD_BookingSystem.Repository.RePayment;

import com.example.POD_BookingSystem.Entity.EPayment.PaymentDetail;
import com.example.POD_BookingSystem.Entity.ETransaction.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail,String> {
    @Query(value = "Select payment_detail_id from payment_detail order by payment_detail_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();
}
