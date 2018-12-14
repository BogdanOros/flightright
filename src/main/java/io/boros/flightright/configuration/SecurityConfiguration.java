package io.boros.flightright.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static io.boros.flightright.member.MemberController.MEMBER_API;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable()
                .authorizeRequests()
                .antMatchers(MEMBER_API + "/**")
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncored() {
        return new BCryptPasswordEncoder();
    }

}
