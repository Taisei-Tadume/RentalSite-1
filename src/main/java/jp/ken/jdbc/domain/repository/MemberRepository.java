package jp.ken.jdbc.domain.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.MemberEntity;

@Repository
public class MemberRepository {

	private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 新規会員登録
    public void save(MemberEntity member) {
        String sql = "INSERT INTO users (user_name, email, phone_number, address, login_id, password_hash, authority_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                member.getUserName(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getAddress(),
                member.getPasswordHash(),
                member.getAuthorityId());
    }

    // メールアドレス重複チェック
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    // ログインID重複チェック
    public boolean existsByLoginId(String loginId) {
        String sql = "SELECT COUNT(*) FROM users WHERE login_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, loginId);
        return count != null && count > 0;
    }
}
