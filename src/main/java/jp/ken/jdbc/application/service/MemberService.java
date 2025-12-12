package jp.ken.jdbc.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        
        //権限登録(1 = 一般ユーザー)
        member.setAuthorityId(1);
        
        //プラン登録(1 = お試しプラン)
        member.setPlanId(1);

        memberRepository.save(member);
    }
    
    /**
     * user_name でユーザー取得（← LoginSuccessHandler が使用）
     */
    public MemberEntity findByUserName(String userName) {
        return memberRepository.findByUserName(userName);
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
}
