package jp.ken.jdbc.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.domain.repository.MemberRepository;
import jp.ken.jdbc.presentation.form.LoginForm;
import jp.ken.jdbc.presentation.form.MemberRegistForm;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 新規会員登録
    public void register(MemberRegistForm form) {

        MemberEntity member = new MemberEntity();

        member.setUserId(null); // AUTO_INCREMENT
        member.setUserName(form.getUserName());
        member.setEmail(form.getEmail());
        member.setPhoneNumber(form.getPhoneNumber());
        member.setAddress(form.getAddress());
        member.setPostalCode(form.getPostalCode());

        // パスワードをハッシュ化
        member.setPasswordHash(passwordEncoder.encode(form.getPassword()));

        // 権限ID（1 = 一般ユーザー）
        member.setAuthorityId((long) 1);

        memberRepository.save(member);
    }

    // メールアドレス重複チェック
    public boolean emailExists(String email) {
        return memberRepository.existsByEmail(email);
    }

    // ログイン処理
    public MemberEntity login(LoginForm form) {

        // メールアドレスでユーザー取得
        MemberEntity user = memberRepository.findByEmail(form.getEmail());

        if (user == null) {
            return null;
        }

        // パスワード判定
        if (!passwordEncoder.matches(form.getPassword(), user.getPasswordHash())) {
            return null;
        }

        // 認証成功
        return user;
    }
}
