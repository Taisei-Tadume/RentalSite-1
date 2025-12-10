package jp.ken.jdbc.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.jdbc.domain.entity.MemberEntity;

public class MemberRowMapper implements RowMapper<MemberEntity> {

    @Override
    public MemberEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberEntity member = new MemberEntity();

        member.setUserId(rs.getInt("user_id"));
        member.setUserName(rs.getString("user_name"));
        member.setEmail(rs.getString("email"));
        member.setPhoneNumber(rs.getString("phone_number"));
        member.setAddress(rs.getString("address"));
        member.setPostalCode(rs.getString("postal_code"));
        member.setPasswordHash(rs.getString("password_hash"));
        member.setAuthorityId(rs.getInt("authority_id"));
        member.setPlanId(rs.getInt("plan_id"));

        return member;
    }
}
