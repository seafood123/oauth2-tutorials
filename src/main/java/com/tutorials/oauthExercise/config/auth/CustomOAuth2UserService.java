package com.tutorials.oauthExercise.config.auth;

import com.tutorials.oauthExercise.config.auth.dto.OAuthAttributes;
import com.tutorials.oauthExercise.config.auth.dto.SessionUser;
import com.tutorials.oauthExercise.entity.User;
import com.tutorials.oauthExercise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(oAuth2UserRequest);

        // 이게 지금 구글인지 네이번지 카카오인지 클라이언트 네임 가져오기
        String registeredId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        System.out.println("현재 : "+registeredId);

        // OAuth2 로그인 진행 시 키가 되는 필드 값 ( PK )
        // 구글 기본코드는 sub,  네이버,카카오 등은 지원 x
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        System.out.println("Pk ---> " + userNameAttributeName);

        // OAuth2User 의 attribute 담는 객체
        OAuthAttributes attributes = OAuthAttributes
                .of(registeredId,userNameAttributeName,oAuth2User.getAttributes());

        System.out.println(attributes.toString());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user",new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );

    }

    private User saveOrUpdate(OAuthAttributes oAuthAttributes){
        User user = userRepository.findByEmail(oAuthAttributes.getEmail())
                .map(entity -> entity.update(oAuthAttributes.getName(), oAuthAttributes.getPicture()))
                .orElse(oAuthAttributes.toEntity());
        return userRepository.save(user);
    }
}
