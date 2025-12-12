package jp.ken.jdbc.domain.repository;

import java.util.List;

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
                (user_name, email, phone_number, address, postal_code, password_hash, authority_id, plan_id)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                member.getUserName(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getAddress(),
                member.getPostalCode(),
                member.getPasswordHash(),
                member.getAuthorityId(),
                member.getPlanId());
    }

    // メールアドレス取得（ログイン用）
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

    // ▼▼▼ 管理画面用（追加）▼▼▼

    // 全件取得
    public List<MemberEntity> findAll() {
        String sql = "SELECT * FROM USERS_TABLE ORDER BY user_id";
        return jdbcTemplate.query(sql, new MemberRowMapper());
    }

    // ID検索（完全一致）
    public List<MemberEntity> findByUserId(Integer id) {
        String sql = "SELECT * FROM USERS_TABLE WHERE user_id = ?";
        return jdbcTemplate.query(sql, new MemberRowMapper(), id);
    }

    // 名前検索（部分一致）
    public List<MemberEntity> findByUserNameLike(String keyword) {
        String sql = "SELECT * FROM USERS_TABLE WHERE user_name LIKE ? ORDER BY user_id";
        String like = "%" + keyword + "%";
        return jdbcTemplate.query(sql, new MemberRowMapper(), like);
    }

    // 権限変更
    public void updateAuthority(Integer userId, Integer authorityId) {
        String sql = "UPDATE USERS_TABLE SET authority_id = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, authorityId, userId);
    }

    // ▲▲▲ 追加ここまで ▲▲▲
}
