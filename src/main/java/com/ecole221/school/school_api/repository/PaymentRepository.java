package com.ecole221.school.school_api.repository;


import com.ecole221.school.school_api.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    // RUBY join table Inscription where  student_id = id
    // Get last payment
    Optional<Payment> findTopByInscriptionStudentIdOrderByPeriodeNumeroDesc(UUID studentId);
}
