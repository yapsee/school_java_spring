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
    public ResponseEntity<String> createPayment(@RequestParam("studentId") UUID studentId, @RequestParam("amount") Double amount) {
        try {
            paymentService.makePartialPaymentForStudent(studentId, amount);
            return ResponseEntity.ok("Payment made successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the payment process.");
        }
    }
}
