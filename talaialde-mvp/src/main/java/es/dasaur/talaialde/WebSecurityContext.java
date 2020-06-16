package es.dasaur.talaialde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import es.dasaur.talaialde.users.security.DbAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityContext extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .anyRequest().permitAll();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
            ObjectPostProcessor<Object> opp, DbAuthenticationProvider authenticationProvider) throws Exception {
        return new AuthenticationManagerBuilder(opp)
            .authenticationProvider(authenticationProvider)
            .build();
    }
    
}