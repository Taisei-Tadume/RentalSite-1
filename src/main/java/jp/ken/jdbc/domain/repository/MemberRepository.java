package jp.ken.jdbc.domain.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.domain.mapper.MemberRowMapper;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 新規会員登録
    public void save(MemberEntity member) {

        String sql = """
            INSERT INTO USERS_TABLE 
            (user_name, email, phone_number, address, postal_code, password_hash, authority_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;


        jdbcTemplate.update(sql,
                member.getUserName(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getAddress(),

                member.getPostalCode(),
                member.getPasswordHash(),
                member.getAuthorityId());
    }
    
    // メールアドレスでユーザー取得（ログイン用）
    public MemberEntity findByEmail(String email) {
        String sql = "SELECT * FROM USERS_TABLE WHERE email = ?";
        return jdbcTemplate.query(sql, new MemberRowMapper(), email)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM USERS_TABLE WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}
