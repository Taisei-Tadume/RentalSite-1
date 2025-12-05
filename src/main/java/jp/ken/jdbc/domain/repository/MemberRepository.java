package jp.ken.jdbc.domain.repository;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.domain.mapper.MemberRowMapper;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    private final MemberRowMapper memberRowMapper = new MemberRowMapper();

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<MemberEntity> findByLoginIdOrEmail(String loginIdOrEmail) {
        String sql = "SELECT * FROM USERS_TABLE WHERE login_id = ? OR email = ?";
        return jdbcTemplate.query(sql, memberRowMapper, loginIdOrEmail, loginIdOrEmail)
                           .stream()
                           .findFirst();
    }

    public int register(MemberEntity member) {
        String sql = "INSERT INTO USERS_TABLE " +
                     "(user_name, email, phone_number, zipcode, address, login_id, password_hash, authority_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                member.getUserName(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getZipcode(),
                member.getAddress(),
                member.getLoginId(),
                member.getPasswordHash(),
                member.getAuthorityId());
    }
}

