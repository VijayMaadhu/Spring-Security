package com.example.Spring.Security.ConfigClass;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/public/**").permitAll() // Public endpoints
                .anyRequest().authenticated() // All other endpoints require authentication
                .and()
            .httpBasic() // Use HTTP Basic authentication
                .and()
            .csrf().disable(); // Disable CSRF for simplicity

        return http.build();
    }
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.builder()
                .username("user") // Ensure the username is set
                .password(passwordEncoder().encode("password")) // Password is encoded
                .roles("USER")
                .build();

	        return new InMemoryUserDetailsManager(user);
	    }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
}
