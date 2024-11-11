//package com.example.POD_BookingSystem.Configuration;
//
//import com.example.POD_BookingSystem.Entity.User;
//import com.example.POD_BookingSystem.Mapper.UserMapper;
//import com.example.POD_BookingSystem.Repository.UserRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Map;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class OAuth2LoginHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
//    private final UserRepository userRepository;
//    private final IProfileService profileService;
//    private final JwtUtils jwtUtils;
//    private final UserMapper userMapper;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
//        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
//        if ("google".equals(token.getAuthorizedClientRegistrationId())) {
//            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
//            Map<String, Object> attributes = principal.getAttributes();
//            String email = attributes.getOrDefault("email", "").toString();
//            String name = attributes.getOrDefault("name", "").toString();
//            String imageUrl = attributes.getOrDefault("picture", "").toString();
//            User user = userRepository.findByEmail(email).orElseGet(() -> {
//                User newUser = userMapper.maptoUserGoogle(email, name);
//                newUser.setPassword(JwtUtils.generateRandomPassword());
//                userRepository.save(newUser);
//                newUser.setBlogs(new HashSet<>());
//                newUser.setReminders(new ArrayList<>());
//                newUser.setNotifications(new ArrayList<>());
//                newUser.setOrders(new ArrayList<>());
//                newUser.setKoiPondList(new ArrayList<>());
//                newUser.setUserProfile(profileService.createProfileOauth(newUser, imageUrl));
//                return userRepository.save(newUser);
//            });
//
//            String jwtToken = jwtUtils.generateToken(user);
//            String role = user.getRole().toString();
//            String username = user.getUsername();
//            long id = user.getId();
//
//            this.setDefaultTargetUrl("http://localhost:5173/login?token=" + jwtToken + "&role=" + role + "&username=" + username + "&id=" + id);
//            this.setAlwaysUseDefaultTargetUrl(true);
//        }
//
//        new DefaultRedirectStrategy().sendRedirect(request, response, this.getDefaultTargetUrl());
//        this.setAlwaysUseDefaultTargetUrl(true);
//        super.onAuthenticationSuccess(request, response, authentication);
//    }
//}
