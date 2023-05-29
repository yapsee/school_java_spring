package com.ecole221.school.school_api.service;

import com.ecole221.school.school_api.model.*;
import com.ecole221.school.school_api.repository.PaymentRepository;
import com.ecole221.school.school_api.repository.PeriodeRepository;
import com.ecole221.school.school_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service

public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PeriodeRepository periodeRepository;

    @Autowired
    private StudentRepository studentRepository;



    public void  launchPayments(Inscription registration, Double initialDeposit, Double minimumDeposit , Double  mensualite) {

        List<Payment> payments = new ArrayList<>();
        // Create payment for November
        Optional<Periode> novemberPeriod = periodeRepository.findByNumero(1);
        Payment paymentNov = createPayment(registration, "Mensualite", novemberPeriod.get().getLibelle(), mensualite, novemberPeriod.get());
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


                Optional<Periode> periode = periodeRepository.findByNumero(i + 1);
                Payment payment = createPayment(registration, "Mensualite", periode.get().getLibelle(), mensualite, periode.get());
                payments.add(payment);

            }
        }
        // Save payments
        createPayments(payments);
    }
    private Payment createPayment(Inscription registration, String libelle, String mois, Double amount, Periode periode) {
        Payment payment = new Payment();
        payment.setInscription(registration);
        payment.setLibelle(libelle);
        payment.setMois(mois);
        payment.setAmount(amount);
        payment.setPeriode(periode);
        return payment;
    }

    public void createPayments(List<Payment> payments) {
        paymentRepository.saveAll(payments);
    }



    public void makePartialPaymentForStudent(UUID studentId, Double amount) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            Double solde = student.getSolde();



            // Calculate the last paid month by the student

            Optional <Payment> lastPayment = paymentRepository.findTopByInscriptionStudentIdOrderByPeriodeNumeroDesc(studentId);

            Classe classe = lastPayment.get().getInscription().getClasse();

            int lastPaidMonth = lastPayment.map(payment -> payment.getPeriode().getNumero()).orElse(0);

//            int periodLines = (int) periodeRepository.count();
//
//            int numberMonthsToPay = periodLines -  lastPaidMonth;


             // Calculate the number of payments to be made
            Double updatedSolde = solde + amount;
            int numberOfPayments = (int) Math.floor(updatedSolde / classe.getMensualite());


//            if( numberOfPayments > numberMonthsToPay){
//                throw new IllegalArgumentException("The amount exceed the total of class payments");
//
//            }

            // Create payments starting from the next month after the last paid month
            for (int i = lastPaidMonth + 1; i <= lastPaidMonth + numberOfPayments; i++) {
                Optional<Periode> periode = periodeRepository.findByNumero(i);
                if (periode.isPresent()) {
                    Payment payment = new Payment();
                    payment.setInscription(lastPayment.get().getInscription());
                    payment.setLibelle("Mensualite");
                    payment.setMois(periode.get().getLibelle());
                    payment.setAmount(classe.getMensualite());
                    payment.setPeriode(periode.get());
                    paymentRepository.save(payment);
                } else {
                    throw new IllegalArgumentException("The amount exceed the total of class payments");
                }
            }

        } else {
            throw new IllegalArgumentException("Student with ID " + studentId + " not found.");
        }
    }


}
