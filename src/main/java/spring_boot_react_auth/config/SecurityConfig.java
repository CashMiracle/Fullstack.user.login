package spring_boot_react_auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder){
		UserDetails admin = User
			.withUsername("Cash")
			.password(encoder.encode("cash"))
			.roles("ADMIN", "USER")
			.build();

		UserDetails user = User
			.withUsername("Mae")
			.password(encoder.encode("mae"))
			.roles("USER")
			.build();
		return new InMemoryUserDetailsManager(admin, user);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/auth/welcome").permitAll()
				.requestMatchers("/auth/user/**").hasRole("USER")
				.requestMatchers("/auth/admin/**").hasRole("ADMIN")
					.anyRequest()
					.authenticated())
					.formLogin(form -> form.defaultSuccessUrl("/auth/welcome", true))
				.httpBasic(basic -> {});
		return http.build();
	}

}
