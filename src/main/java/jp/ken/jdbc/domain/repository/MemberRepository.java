package jp.ken.jdbc.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.domain.mapper.MemberRowMapper;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    private final MemberRowMapper rowMapper = new MemberRowMapper();

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** ユーザー名で1件取得（ログイン用） */
    public MemberEntity findByUserName(String userName) {
        String sql = """
            SELECT *
            FROM users_table
            WHERE user_name = ?
            ORDER BY user_id
            LIMIT 1
        """;

        return jdbcTemplate.queryForObject(sql, rowMapper, userName);
    }

    /** メール重複チェック */
    public int countByEmail(String email) {
        String sql = """
            SELECT COUNT(*)
            FROM users_table
            WHERE email = ?
        """;

        return jdbcTemplate.queryForObject(sql, Integer.class, email);
    }
    
    /** 電話番号重複チェック */
    public int countByPhoneNumber(String phoneNumber) {
        String sql = """
            SELECT COUNT(*)
            FROM users_table
            WHERE phone_number = ?
        """;

        return jdbcTemplate.queryForObject(sql, Integer.class, phoneNumber);
    }

    /** 会員登録 */
    public void insert(MemberEntity member) {
        String sql = """
            INSERT INTO users_table
            (user_name, email, phone_number, address, postal_code, password_hash, authority_id, plan_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        jdbcTemplate.update(
            sql,
            member.getUserName(),
            member.getEmail(),
            member.getPhoneNumber(),
            member.getAddress(),
            member.getPostalCode(),
            member.getPasswordHash(),
            member.getAuthorityId(),
            member.getPlanId()
        );
    }

    /** 全件取得 */
    public List<MemberEntity> findAll() {
        String sql = "SELECT * FROM users_table ORDER BY user_id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    /** ID検索 */
    public List<MemberEntity> findByUserId(Integer userId) {
        String sql = "SELECT * FROM users_table WHERE user_id = ?";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    /** 名前LIKE検索 */
    public List<MemberEntity> findByUserNameLike(String keyword) {
        String sql = "SELECT * FROM users_table WHERE user_name LIKE ?";
        return jdbcTemplate.query(sql, rowMapper, "%" + keyword + "%");
    }

    /** 権限変更 */
    public void updateAuthority(Integer userId, Integer authorityId) {
        String sql = "UPDATE users_table SET authority_id = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, authorityId, userId);
    }

    /** プラン変更するやつ */
    public void updatePlan(Integer userId, Integer planId) {
        String sql = "UPDATE users_table SET plan_id = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, planId, userId);
    }
}
