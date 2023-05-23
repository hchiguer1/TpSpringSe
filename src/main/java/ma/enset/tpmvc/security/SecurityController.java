package ma.enset.tpmvc.security;

import lombok.AllArgsConstructor;
import ma.enset.tpmvc.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller@AllArgsConstructor
public class SecurityController {
    private PatientRepository patientRepository;
    @GetMapping("/notAuthorized")
    public String notAuthorized(){
        return"403";
    }

}
