package ma.enset.patient_spring_mvc.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig{
    @Autowired
    private PasswordEncoder passwordEncoder;

    //@Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                /*HERE WE CAN LOAD THE USERS FROM DB*/
                return null;
            }
        };
    }
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        /*{noop} => no password encoder*/
        UserDetails userDetails1 = User.builder().username("user1").password(passwordEncoder.encode("user1")).roles("USER").build();
        UserDetails userDetails2 = User.builder().username("user2").password(passwordEncoder.encode("user2")).roles("ADMIN").build();
        UserDetails userDetails3 = User.builder().username("user3").password(passwordEncoder.encode("user3")).roles("USER","ADMIN").build();

        return new InMemoryUserDetailsManager(userDetails1,userDetails2,userDetails3);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/index");
        httpSecurity.rememberMe();
        httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**").permitAll();
        //httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
        //httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");
        return  httpSecurity.build();

    }
}
