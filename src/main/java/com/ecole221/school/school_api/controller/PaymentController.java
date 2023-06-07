package com.ecole221.school.school_api.controller;

import com.ecole221.school.school_api.model.Payment;
import com.ecole221.school.school_api.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;


    @PostMapping("")
    public ResponseEntity<Payment> createPayment(@RequestParam UUID studentId, @RequestParam Double amount) {
        try {
            Payment payment = paymentService.makePartialPaymentForStudent(studentId, amount);
            return ResponseEntity.ok(payment);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }
}
