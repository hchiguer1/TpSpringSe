package ma.enset.tpmvc;

import ma.enset.tpmvc.entities.Patient;
import ma.enset.tpmvc.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;
@SpringBootApplication
public class TpMvcApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(TpMvcApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        patientRepository.save(new Patient(null,"zakia",new Date(),true,555));
        patientRepository.save(new Patient(null,"inass",new Date(),true,777));
        patientRepository.save(new Patient(null,"adam",new Date(),true,333));

        /*Builder
        Patient patient1=Patient.builder()
                .name("Imane")
                .dateNaissance(new Date())
                .malade(false)
                .score(555)
                .build();
        */
    }
    @Bean
    PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
    }

    //@Bean
    CommandLineRunner commandLineRunnerjdbcUsers(JdbcUserDetailsManager jdbcUserDetailsManager){
        return args -> {
            jdbcUserDetailsManager.createUser(
                    User.withUsername("user1").password(passwordEncoder().encode("11111")).roles("USER").build()
            );
            jdbcUserDetailsManager.createUser(
                    User.withUsername("user2").password(passwordEncoder().encode("22222")).roles("USER").build()
            );
            jdbcUserDetailsManager.createUser(
                    User.withUsername("admin").password(passwordEncoder().encode("33333")).roles("USER","ADMIN").build()
            );
        };
    }
}
