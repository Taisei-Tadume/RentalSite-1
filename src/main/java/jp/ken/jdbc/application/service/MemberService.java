package jp.ken.jdbc.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.domain.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
 // ★ これを追加 ★
    public MemberEntity findByUserName(String userName) {
        return memberRepository.findByUserName(userName);
    }

    /* 全会員取得 */
    public List<MemberEntity> findAllMembers() {
        return memberRepository.findAll();
    }

    /* 検索 */
    public List<MemberEntity> search(String keyword) {
        // 数字ならID検索、違えば名前検索
        try {
            Integer id = Integer.parseInt(keyword);
            return memberRepository.findByUserId(id);
        } catch (NumberFormatException e) {
            return memberRepository.findByUserNameLike(keyword);
        }
    }

    /* 権限変更 */
    public void changeAuthority(Integer userId, Integer authorityId) {
        memberRepository.updateAuthority(userId, authorityId);
    }

    /* ✅ プラン変更（今回追加） */
    public void changePlan(Integer userId, Integer planId) {
        memberRepository.updatePlan(userId, planId);
    }
}
