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

				/* ✅ 公開ページ（ログイン不要） */
				/* - トップ、ログイン、会員登録 */
				/* - 検索はクエリ付きURLも許可するため /search/** */
				/* - カートはセッション方式なのでログイン不要 → /cart/** */
				.requestMatchers("/top", "/login", "/regist").permitAll()
				.requestMatchers("/search/**").permitAll() // ★ 最適化：検索はすべて公開
				.requestMatchers("/cart/**").permitAll() // ★ 最適化：カート操作をすべて許可
				.requestMatchers("/detail/**").permitAll() // ★ 商品詳細ページを公開
				.requestMatchers("/payment/**").permitAll() // ★ 決済選択ページを公開

				/* ✅ 静的リソース（必ず公開） */
				.requestMatchers("/css/**", "/js/**", "/images/**", "/picture/**").permitAll()

				/* ✅ ログイン必須ページ */
				/* - 注文、会員ページ、ログアウト */
				.requestMatchers("/order/**", "/logout", "/member/**").authenticated()

				/* ✅ 管理者専用ページ */
				.requestMatchers("/employee/**", "/admin/**").hasRole("ADMIN")

				/* ✅ その他はすべて認証必須 */
				.anyRequest().authenticated());

		/* ✅ 403（権限不足）発生時の遷移先を設定 */
		http.exceptionHandling(ex -> ex
				.accessDeniedPage("/error") // ★ 追加：403 Forbidden → error.html に飛ばす
		);

		/* ✅ ログイン設定 */
		http.formLogin(form -> form
				.loginPage("/login") // ログイン画面
				.loginProcessingUrl("/login") // 認証処理URL
				.usernameParameter("email") // フォームの name="email"
				.passwordParameter("password") // フォームの name="password"
				.successHandler(loginSuccessHandler) // ★ ログイン成功時の処理
				.failureUrl("/login?error") // 失敗時
				.permitAll());

		/* ✅ ログアウト設定 */
		http.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID"));

		/* ✅ セッション管理（同時ログイン1件） */
		http.sessionManagement(session -> session
				.maximumSessions(1)
				.maxSessionsPreventsLogin(true));

		return http.build();
	}

	/* ✅ JDBC 認証（ユーザ情報を DB から取得） */
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

	/* ✅ セッションイベント（同時ログイン制御に必要） */
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	/* ✅ パスワードハッシュ（BCrypt） */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}