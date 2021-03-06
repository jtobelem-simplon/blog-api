package co.simplon.blog.configuration;

import co.simplon.blog.jwt.JwtTokenFilter;
import co.simplon.blog.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Global security configuration for our HTTP Rest API.
 *
 * @author Josselin Tobelem
 */
@AllArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Method that configures HTTP security.
     *
     * @param http the HttpSecurity object to configure.
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors();

        // Disable CSRF (cross site request forgery as our token will be stored in session storage)
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests()//
                .antMatchers("/api/sign-in", "/api/sign-up").permitAll()//
                .antMatchers("/api/init").permitAll()//
                .antMatchers(HttpMethod.GET, "/api/posts").permitAll()//
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/v2/api-docs", "/webjars/**", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html/**").permitAll()
//                .antMatchers("/").permitAll() // TODO ajouter une page racine
//                .antMatchers("/csrf").permitAll()

                // Disallow everything else...
                .anyRequest().authenticated();

        // Apply JWT
        http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().disable();

    }

}
