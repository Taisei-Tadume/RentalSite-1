package jp.ken.jdbc.domain.repository;

import jp.ken.jdbc.domain.entity.MemberEntity;

public interface MemberRepository {

    MemberEntity findByEmail(String email);

}
