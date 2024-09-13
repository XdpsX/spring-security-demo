package com.demo.security.security.oauth;

import com.demo.security.entities.User;
import com.demo.security.repositories.UserRepository;
import com.demo.security.security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Value("${frontend.url}")
    private String FRONTEND_URL;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwtToken = tokenProvider.generateToken(user);

        String targetUrl = determineTargetUrl(request, response, jwtToken);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // Generate frontend url with token in query parameter
    private String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, String token) {
        // Get URL that user try to access before redirecting to login page
//        String targetUrl = super.determineTargetUrl(request, response);
//        if (targetUrl == null || targetUrl.isEmpty()) {
//            targetUrl = FRONTEND_URL;
//        }

        // Add token in query parameter
        return UriComponentsBuilder.fromUriString(FRONTEND_URL + "/oauth2/redirect")
                .queryParam("token", token)
                .build().toUriString();
    }
}
