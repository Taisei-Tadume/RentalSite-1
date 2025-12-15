package jp.ken.jdbc.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.domain.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* ===== 会員登録系 ===== */

    /** メールアドレス重複チェック */
    public boolean emailExists(String email) {
        return memberRepository.countByEmail(email) > 0;
    }

    /** 会員登録 */
    @Transactional
    public void register(MemberEntity member) {
        memberRepository.insert(member);
    }

    /* ===== 管理・検索系 ===== */

    public MemberEntity findByUserName(String userName) {
        return memberRepository.findByUserName(userName);
    }

    public List<MemberEntity> findAllMembers() {
        return memberRepository.findAll();
    }

    public List<MemberEntity> search(String keyword) {
        try {
            Integer id = Integer.parseInt(keyword);
            return memberRepository.findByUserId(id);
        } catch (NumberFormatException e) {
            return memberRepository.findByUserNameLike(keyword);
        }
    }

    public void changeAuthority(Integer userId, Integer authorityId) {
        memberRepository.updateAuthority(userId, authorityId);
    }

    public void changePlan(Integer userId, Integer planId) {
        memberRepository.updatePlan(userId, planId);
    }
}
