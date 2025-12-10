package jp.ken.jdbc.domain.entity;

import java.time.LocalDate;

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
    
    private String postalCode;
    
    private Integer planId;
    
    private LocalDate planStartDate;
    
    private LocalDate planEndDate;
}
