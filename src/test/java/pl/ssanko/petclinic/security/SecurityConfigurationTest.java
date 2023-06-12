package pl.ssanko.petclinic.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.ssanko.petclinic.views.login.LoginView;

@EnableWebSecurity
@TestConfiguration
public class SecurityConfigurationTest extends VaadinWebSecurity{

        @Override
        protected void configure(HttpSecurity http) throws Exception {

        }

}

