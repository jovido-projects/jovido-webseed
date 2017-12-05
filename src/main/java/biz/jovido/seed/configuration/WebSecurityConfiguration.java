package biz.jovido.seed.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Stephan Grundner
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    @ConfigurationProperties(prefix = "seed.security.admin")
    public AdminProperties adminProperties() {
        return new AdminProperties();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/admin/**").authenticated()
//                .anyRequest().permitAll()
//                .and().formLogin().loginPage("/login")
//                .and().logout().logoutSuccessUrl("/").permitAll()
//                .and().csrf().disable();
        http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
    }

//    @Bean
//    AuthenticationProvider authenticationProvider(SecurityService securityService, PasswordEncoder passwordEncoder) {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(securityService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder);
//        return authenticationProvider;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        SecurityService securityService = getApplicationContext().getBean(SecurityService.class);
//        auth.userDetailsService(securityService);
//
//        AuthenticationProvider authenticationProvider = getApplicationContext().getBean(AuthenticationProvider.class);
//        auth.authenticationProvider(authenticationProvider);
//    }
}
