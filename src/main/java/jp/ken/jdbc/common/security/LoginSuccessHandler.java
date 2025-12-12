package jp.ken.jdbc.common.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.ken.jdbc.application.service.MemberService;
import jp.ken.jdbc.domain.entity.MemberEntity;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final MemberService memberService;

    public LoginSuccessHandler(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        // principal = user_name
        String userName = authentication.getName();

        // DB からユーザー情報を取得
        MemberEntity user = memberService.findByUserName(userName);

        // セッションに保存
        request.getSession().setAttribute("loginUser", user);

        // 権限も保存
        if (user.getAuthorityId() == 2) {
            request.getSession().setAttribute("role", "admin");
        } else {
            request.getSession().setAttribute("role", "user");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}