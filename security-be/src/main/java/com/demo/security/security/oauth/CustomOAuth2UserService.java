package com.demo.security.security.oauth;

import com.demo.security.constants.SecurityConstants;
import com.demo.security.entities.Provider;
import com.demo.security.entities.ProviderBuilder;
import com.demo.security.entities.Role;
import com.demo.security.entities.User;
import com.demo.security.repositories.RoleRepository;
import com.demo.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Loading oauth user");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> registerNewUser(attributes, registrationId));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(SecurityConstants.ROLE_PREFIX + user.getRole().getName())),
                attributes, userNameAttributeName);
    }

    private User registerNewUser(Map<String, Object> attributes, String registrationId) {
        Role role = roleRepository.findByName(Role.USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Provider provider = ProviderBuilder.fromString(registrationId);
        return userRepository.save(User.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .provider(provider)
                .role(role)
                .build());
    }
}
