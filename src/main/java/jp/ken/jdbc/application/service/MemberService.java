package jp.ken.jdbc.application.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 新規会員登録
     */
    @Transactional
    public void register(MemberRegistForm form) {

        if (form == null) {
            throw new IllegalArgumentException("フォームがnullです");
        }

        if (memberRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("このメールアドレスは既に登録されています");
        }

        String encodedPassword = passwordEncoder.encode(form.getPassword());

        MemberEntity member = new MemberEntity();

        member.setUserName(trim(form.getUserName()));
        member.setEmail(trim(form.getEmail()));
        member.setPhoneNumber(trim(form.getPhoneNumber()));
        member.setAddress(trim(form.getAddress()));
        member.setPostalCode(trim(form.getPostalCode()));
        member.setPasswordHash(encodedPassword);

        // 権限登録(1 = 一般ユーザー)
        member.setAuthorityId(1);

        // プラン登録(1 = お試しプラン)
        member.setPlanId(1);

        memberRepository.save(member);
    }

    /**
     * ログイン処理
     */
    public MemberEntity login(LoginForm form) {

        if (form == null) {
            throw new IllegalArgumentException("フォームがnullです");
        }

        MemberEntity member = memberRepository.findByEmail(form.getEmail());
        if (member == null) {
            return null;
        }

        if (!passwordEncoder.matches(form.getPassword(), member.getPasswordHash())) {
            return null;
        }

        return member;
    }

    /**
     * メールアドレス重複チェック
     */
    public boolean emailExists(String email) {
        return memberRepository.existsByEmail(email);
    }

    /**
     * trim + null safe
     */
    private String trim(String value) {
        return value == null ? null : value.trim();
    }


    // ▼▼▼ ここから管理画面用に追加 ▼▼▼

    /**
     * 会員一覧（全件）
     */
    public List<MemberEntity> findAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * 会員検索（ID or 名前）
     */
    public List<MemberEntity> search(String keyword) {

        if (keyword == null || keyword.isEmpty()) {
            return memberRepository.findAll();
        }

        // 数字なら ID 検索
        try {
            Integer id = Integer.parseInt(keyword);
            return memberRepository.findByUserId(id);

        // 数字でなければ名前検索（部分一致）
        } catch (NumberFormatException e) {
            return memberRepository.findByUserNameLike(keyword);
        }
    }
    public void changeAuthority(Integer userId, Integer authorityId) {
        memberRepository.updateAuthority(userId, authorityId);
    }

    // ▲▲▲ 追加ここまで ▲▲▲

}