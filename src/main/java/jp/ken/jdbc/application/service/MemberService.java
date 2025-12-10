package jp.ken.jdbc.application.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

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

    // ★★ これが登録処理の完成形 ★★
    public void register(MemberRegistForm form) {

        MemberEntity member = new MemberEntity();

        member.setUserName(form.getUserName());
        member.setEmail(form.getEmail());
        member.setPhoneNumber(form.getPhoneNumber());
        member.setAddress(form.getAddress());
        member.setPostalCode(form.getPostalCode());

        // パスワードハッシュ化
        member.setPasswordHash(passwordEncoder.encode(form.getPassword()));

        // 権限ID（1 = 一般ユーザー）
        member.setAuthorityId(1);
        
        // プランID（1 = お試しプラン）
        member.setPlanId(1);
        
        // プラン期間(自動でプランの開始日と終了日が入る)
        member.setPlanStartDate(LocalDate.now());
        member.setPlanEndDate(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));

        // DBへ保存
        memberRepository.save(member);
    }
    
    //メールアドレス重複チェック
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
