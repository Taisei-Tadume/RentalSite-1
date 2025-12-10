package jp.ken.jdbc.domain.entity;

import lombok.Data;

@Data
public class MemberEntity {

    private Long userId;          
    private String userName;      
    private String email;         
    private String phoneNumber;   
    private String address;       
    private String postalCode;    
    private String passwordHash;  
    private Long authorityId;     

}
