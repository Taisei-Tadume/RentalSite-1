package jp.ken.jdbc.infrastructure.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.domain.repository.MemberRepository;

@Repository
public class MemberDao implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MemberEntity findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";

        var list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            MemberEntity e = new MemberEntity();
            e.setMemberId(rs.getInt("member_id"));
            e.setName(rs.getString("name"));
            e.setEmail(rs.getString("email"));
            e.setPassword(rs.getString("password"));
            return e;
        }, email);

        return list.isEmpty() ? null : list.get(0);
    }
}
