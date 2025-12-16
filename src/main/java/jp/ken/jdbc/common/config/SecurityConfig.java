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
			LoginSuccessHandler loginSuccessHandler) throws Exception {

		http.authorizeHttpRequests(authz -> authz

				/* ==========================
				 * 公開ページ（ログイン不要）
				 * ========================== */
				.requestMatchers("/", "/top", "/login", "/regist/**").permitAll()
				.requestMatchers("/search/**").permitAll()
				.requestMatchers("/detail/**").permitAll()

				/* ==========================
				 * 決済ページ（ログイン不要）
				 * ========================== */
				.requestMatchers("/payment").permitAll()
				.requestMatchers("/payment/**").permitAll()

				/* ==========================
				 * プラン選択（ログイン不要）
				 * ========================== */
				.requestMatchers("/plan/**").permitAll()

				/* ==========================
				 * カート（ログイン不要）
				 * ========================== */
				.requestMatchers("/cart", "/cart/**").permitAll()

				/* ==========================
				 * 静的リソース
				 * ========================== */
				.requestMatchers("/css/**", "/js/**", "/images/**", "/picture/**").permitAll()

				/* ==========================
				 * ログイン必須ページ
				 * ========================== */
				.requestMatchers("/order/**", "/member/**").authenticated()

				/* ==========================
				 * 管理者専用
				 * ========================== */
				.requestMatchers("/employee/**", "/admin/**").hasRole("ADMIN")

				/* ==========================
				 * その他は認証必須
				 * ========================== */
				.anyRequest().authenticated());

		/* 403 */
		http.exceptionHandling(ex -> ex.accessDeniedPage("/error"));

		/* ログイン設定 */
		http.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.successHandler(loginSuccessHandler)
				.failureUrl("/login?error")
				.permitAll());

		/* ログアウト設定 */
		http.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID"));

		/* セッション管理（同時ログイン1件） */
		http.sessionManagement(session -> session
				.maximumSessions(1)
				.maxSessionsPreventsLogin(true));

		return http.build();
	}

	/* JDBC 認証 */
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

	/* セッションイベント */
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	/* パスワードハッシュ */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}