package biz.jovido.seed.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Stephan Grundner
 */
@Configuration
//@Deprecated
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Bean
//    public UserDetailsService userDetailsService(SecurityService securityService) {
//        return securityService;
//    }

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
}
