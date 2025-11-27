package jp.ken.jdbc.application.service;

import org.springframework.stereotype.Service;

import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.domain.repository.MemberRepository;
import jp.ken.jdbc.presentation.form.LoginForm;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // ▼ ログイン処理
    public MemberEntity login(LoginForm form) {

        MemberEntity user = memberRepository.findByEmail(form.getEmail());

        // 該当ユーザなし
        if (user == null) return null;

        // パスワード一致判定
        if (!user.getPassword().equals(form.getPassword())) return null;

        return user;
    }
}
