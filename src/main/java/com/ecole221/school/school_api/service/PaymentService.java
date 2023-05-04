package com.ecole221.school.school_api.service;

import com.ecole221.school.school_api.model.Inscription;
import com.ecole221.school.school_api.model.Payment;
import com.ecole221.school.school_api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service

public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;



    public void  proceedPayments(Inscription registration, Double initialDeposit, Double minimumDeposit , Double  mensualite) {

        List<Payment> payments = new ArrayList<>();
        // Create payment for November
        Payment paymentNov = new Payment();
        paymentNov.setInscription(registration);
        paymentNov.setMois("November");
        paymentNov.setLibelle("Mensualite");
        paymentNov.setAmount(mensualite);
        payments.add(paymentNov);
//
//        Payment paymentFrais = new Payment();
//        paymentFrais.setInscription(registration);
//        paymentFrais.setMois("November");
//        paymentNov.setLibelle("Frais Inscription et autres ");
//        paymentFrais.setAmount(minimumDeposit);
//        payments.add(paymentFrais);


        // Check amount after november payment
        Double remainingBalanceAfterNov = initialDeposit - minimumDeposit;
        if (remainingBalanceAfterNov >= mensualite) {
            // Check number of month with the remaining amount
            int remainingMonths = (int) Math.floor(remainingBalanceAfterNov / mensualite);
            // See if there is a remaining so i can add to student solde
            Double remainingAmount = remainingBalanceAfterNov % mensualite;
            // Incrémenter le solde de l'étudiant
            registration.getStudent().setSolde(registration.getStudent().getSolde() + remainingAmount);
            // Créer des paiements pour les mois restants
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, 11); // Set month to november
            for (int i = 0; i < remainingMonths; i++) {
                Payment payment = new Payment();
                payment.setInscription(registration);
                calendar.add(Calendar.MONTH, i); // increment month after nov
                payment.setMois(new SimpleDateFormat("MMMM").format(calendar.getTime()));
                payment.setAmount(mensualite);
                payments.add(payment);

            }
        }
        // Save payments
        createPayments(payments);
    }

    public void createPayments(List<Payment> payments) {
        paymentRepository.saveAll(payments);
    }


}
