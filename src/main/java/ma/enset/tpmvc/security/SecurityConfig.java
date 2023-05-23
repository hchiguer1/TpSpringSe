package ma.enset.tpmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        String pwd=passwordEncoder.encode("1234");

        InMemoryUserDetailsManager inMemoryUserDetailsManager=new InMemoryUserDetailsManager(
                //User.withUsername("user1").password("{noop}1234").roles("USER").build(),//{noop} ==>no password encoder
                User.withUsername("user1").password(pwd).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder.encode("3333")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("2222")).roles("USER","ADMIN").build()
        );
        return inMemoryUserDetailsManager;
    }
    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);
        return jdbcUserDetailsManager;
    }



    //@Bean//--> exécuter cette methode au démarrage de l'application
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //httpSecurity.csrf().disable();//===>désactivé la protection en utilisant le csrf
        httpSecurity.formLogin();//utiliser une formulaire pour s'autentifier
        //httpSecurity.authorizeHttpRequests().requestMatchers("/delete/**","/savePatient/**","/editPatient/**").hasRole("ADMIN");
        //httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        //httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();// On doit s'autentifier pour tous les requets
        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");
        return httpSecurity.build();
    }
}
