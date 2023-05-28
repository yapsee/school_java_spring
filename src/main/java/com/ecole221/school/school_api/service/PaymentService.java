package com.ecole221.school.school_api.service;

import com.ecole221.school.school_api.model.Inscription;
import com.ecole221.school.school_api.model.Payment;
import com.ecole221.school.school_api.model.Periode;
import com.ecole221.school.school_api.repository.PaymentRepository;
import com.ecole221.school.school_api.repository.PeriodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service

public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PeriodeRepository periodeRepository;



    public void  proceedPayments(Inscription registration, Double initialDeposit, Double minimumDeposit , Double  mensualite) {

        List<Payment> payments = new ArrayList<>();
        // Create payment for November
        Optional<Periode> November = periodeRepository.findByNumero(1);
        Payment paymentNov = new Payment();
        paymentNov.setInscription(registration);
       // paymentNov.setMois("November");
        paymentNov.setLibelle(November.get().getLibelle());
        paymentNov.setAmount(mensualite);
        paymentNov.setPeriode(November.get());
        payments.add(paymentNov);


        // Create payment for November
//        Payment paymentNov = new Payment();
//        paymentNov.setInscription(registration);
//        paymentNov.setMois("November");
//        paymentNov.setLibelle("Mensualite");
//        paymentNov.setAmount(mensualite);
//        payments.add(paymentNov);
//
////        Payment paymentFrais = new Payment();
////        paymentFrais.setInscription(registration);
////        paymentFrais.setMois("November");
////        paymentNov.setLibelle("Frais Inscription et autres ");
////        paymentFrais.setAmount(minimumDeposit);
////        payments.add(paymentFrais);


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
                calendar.add(Calendar.MONTH, 1); // increment month after nov
                payment.setMois(new SimpleDateFormat("MMMM").format(calendar.getTime()));
                payment.setAmount(mensualite);
                payments.add(payment);

            }
        }
        // Save payments
        createPayments(payments);
    }


    public void  launchPayments(Inscription registration, Double initialDeposit, Double minimumDeposit , Double  mensualite) {

        List<Payment> payments = new ArrayList<>();
        // Create payment for November
        Optional<Periode> november = periodeRepository.findByNumero(1);
        Payment paymentNov = new Payment();
        paymentNov.setInscription(registration);
        paymentNov.setLibelle("Mensualite");
        paymentNov.setMois(november.get().getLibelle());
        paymentNov.setAmount(mensualite);
        paymentNov.setPeriode(november.get());
        payments.add(paymentNov);



        // Check amount after november payment
        Double remainingBalanceAfterNov = initialDeposit - minimumDeposit;
        if (remainingBalanceAfterNov >= mensualite) {
            // Check number of month with the remaining amount
            int remainingMonths = (int) Math.floor(remainingBalanceAfterNov / mensualite);
            // See if there is a remaining so i can add to student solde
            Double remainingAmount = remainingBalanceAfterNov % mensualite;
            // Incrémenter le solde de l'étudiant
            registration.getStudent().setSolde(registration.getStudent().getSolde() + remainingAmount);
            // Créer des paiements pour les mois restant
            for (int i = 1; i <= remainingMonths; i++) {

                Optional<Periode> mois = periodeRepository.findByNumero(i+1);
                Payment payment = new Payment();
                payment.setInscription(registration);
                payment.setLibelle("Mensualite");
                payment.setMois(mois.get().getLibelle());
                payment.setAmount(mensualite);
                payment.setPeriode(mois.get());
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
