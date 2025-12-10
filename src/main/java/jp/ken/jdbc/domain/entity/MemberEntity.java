package jp.ken.jdbc.domain.entity;

import lombok.Data;

@Data
public class MemberEntity {

	private Integer userId;

    private String userName;

    private String email;

    private String phoneNumber;

    private String address;

    private String passwordHash;

    private Integer authorityId;
    
    private String postlCode;
}
