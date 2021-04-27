package com.example.sprmethodsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder encoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().and().authorizeRequests().antMatchers("/hello").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated();

        //видимо, h2 имеет свою собственную аутентификацию и блокирует доступ к веб-консоли. Нашёл вот эту конструкцию:
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        for (String line : readFile()){
            String userName = line.split(":")[0];
            String password = line.split(":")[1];
            String permission = line.split(":")[2];
            auth.inMemoryAuthentication().withUser(userName)
                    .password(encoder().encode(password))
                    .authorities(permission);
        }
    }

    private String[] readFile() throws IOException {
        var fileName = "permissions.txt";
        var content = Files.lines(Paths.get(fileName)).reduce("", String::concat);
        return content.split(";");
    }
}
