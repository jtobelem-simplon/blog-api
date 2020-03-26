package co.simplon.blog.security;

import co.simplon.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;
import java.util.Optional;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .authorizeRequests()
//                .antMatchers("/home/**").permitAll()
//                .antMatchers("/admin/**").hasAuthority("admin")
                .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                .and()
                    .oauth2ResourceServer().jwt();
        //@formatter:on
    }

//    @Override
//    protected UserDetailsService userDetailsService() {
//        return username -> {
//            System.out.println("yo");
//            Optional<co.simplon.blog.model.User> user = userRepository.findByName(username);
//
//            return User
//                    .withUsername(username)
//                    .password(user.get().getPassword())
//                    .authorities(Arrays.asList(user.get().getRole()))
//                    .accountExpired(false)
//                    .accountLocked(false)
//                    .credentialsExpired(false)
//                    .disabled(false)
//                    .build();
//        };
//    }


}
