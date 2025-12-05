package jp.ken.jdbc.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.domain.repository.MemberRepository;

@Service
public class MemberService {

	private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(MemberEntity member) {

        // パスワードハッシュ化
        member.setPasswordHash(passwordEncoder.encode(member.getPasswordHash()));

        // 権限設定（1 = 一般ユーザー）
        member.setAuthorityId(1);

        // DB に保存
        memberRepository.save(member);
    }

    public boolean emailExists(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean loginIdExists(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }
}
