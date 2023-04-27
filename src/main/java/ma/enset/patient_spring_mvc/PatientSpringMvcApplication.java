package ma.enset.patient_spring_mvc;

import ma.enset.patient_spring_mvc.entities.Patient;
import ma.enset.patient_spring_mvc.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientSpringMvcApplication implements CommandLineRunner {

    @Autowired
    PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(PatientSpringMvcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Patient patient = Patient.builder()
//                .name("Karam")
//                .birthDate(new Date())
//                .sick(false)
//                .score(45)
//                .build();
//        patientRepository.save(patient);
//        Patient patient1 = Patient.builder()
//                .name("Riad")
//                .birthDate(new Date())
//                .sick(false)
//                .score(18)
//                .build();
//        patientRepository.save(patient1);
//        Patient patient2 = Patient.builder()
//                .name("Aiman")
//                .birthDate(new Date())
//                .sick(true)
//                .score(20)
//                .build();
//        patientRepository.save(patient2);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
