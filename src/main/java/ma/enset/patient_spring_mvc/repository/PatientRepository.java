package ma.enset.patient_spring_mvc.repository;

import ma.enset.patient_spring_mvc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    Page<Patient> findByNameContains(String s, Pageable pageable);
}
