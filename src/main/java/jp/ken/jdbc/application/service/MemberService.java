package jp.ken.jdbc.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.domain.repository.MemberRepository;
import jp.ken.jdbc.presentation.form.MemberRegistForm;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ★★ これが登録処理の完成形 ★★
    public void register(MemberRegistForm form) {

        MemberEntity member = new MemberEntity();

        member.setUserName(form.getUserName());
        member.setEmail(form.getEmail());
        member.setPhoneNumber(form.getPhoneNumber());
        member.setAddress(form.getAddress());
        member.setPostlCode(form.getPostalCode());

        // パスワードハッシュ化
        member.setPasswordHash(passwordEncoder.encode(form.getPassword()));

        // 権限ID（1 = 一般ユーザー）
        member.setAuthorityId(1);

        // DBへ保存
        memberRepository.save(member);
    }

    public boolean emailExists(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean loginIdExists(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }
}
