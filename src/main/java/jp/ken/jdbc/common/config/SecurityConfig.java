package jp.ken.jdbc.common.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import jp.ken.jdbc.common.security.LoginSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private final DataSource dataSource;

	public SecurityConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http,
			LoginSuccessHandler loginSuccessHandler // ✅ ここで受け取る（依存しない）
	) throws Exception {

		http.authorizeHttpRequests(authz -> authz
				.requestMatchers("/top", "/login", "/search", "/regist", "/cart",
						"/plan", "/payment/**", "/detail/**")
				.permitAll()
				.requestMatchers("/css/**", "/js/**", "/images/**", "/picture/**").permitAll()
				.requestMatchers("/order/**", "/logout", "/member/**").authenticated()
				.requestMatchers("/employee/**", "/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated());

		http.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.successHandler(loginSuccessHandler) // ✅ ここで使うだけ
				.failureUrl("/login?error")
				.permitAll());

		http.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID"));

		http.sessionManagement(session -> session
				.maximumSessions(1)
				.maxSessionsPreventsLogin(true));

		return http.build();
	}

	@Bean
	public JdbcUserDetailsManager userDetailsManager() {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(this.dataSource);

		manager.setUsersByUsernameQuery(
				"SELECT user_name AS username, password_hash AS password, true AS enabled " +
						"FROM users_table WHERE email = ?");

		manager.setAuthoritiesByUsernameQuery(
				"SELECT u.user_name AS username, a.authority_name AS authority " +
						"FROM users_table u " +
						"JOIN users_authority_table a ON u.authority_id = a.authority_id " +
						"WHERE u.user_name = ?");

		return manager;
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}