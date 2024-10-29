package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.DTO.Request.Authentication.GetUserInfoRequest;
import com.example.POD_BookingSystem.DTO.Request.Authentication.IntrospectRequest;
import com.example.POD_BookingSystem.DTO.Request.Authentication.LogoutRequest;
import com.example.POD_BookingSystem.DTO.Response.AuthenticationResponse;
import com.example.POD_BookingSystem.DTO.Response.IntrospectResponse;
import com.example.POD_BookingSystem.Entity.InvalidatedToken;
import com.example.POD_BookingSystem.Repository.InvalidatedTokenRepository;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.POD_BookingSystem.DTO.Request.Authentication.AuthenticationRequest;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signKey}")
    protected String SIGNER_KEY;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));


        boolean authenticated = passwordEncoder.matches(request.getPassword(),
                user.getPassword());

        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user.getUsername(),user.getRole_id().getRoleName());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();


    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean valid = true;

        try {
            verifyToken(token);
        } catch (AppException e) {
            valid = false;
        }
        return IntrospectResponse.builder()
                .valid(valid)
                .build();
    }

    private String generateToken(String username, String roleName) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);  //build header

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()  // build payload
                .subject(username)
                .issuer("POD_Booking_system")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("Role", roleName) //chèn role vào token
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));  //MACSigner là thuật toán kí khóa để kí và khóa giải mã phải trùng nhau,
            //cần sử dụng SIGNER_KEY để verify cái token
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    public void logout(String token) throws ParseException, JOSEException {
        var signToken = verifyToken(token);

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);


        return signedJWT;

    }
    public String getUsernameFromToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(token); // Xác thực token

        // Lấy username từ claims
        String username = signedJWT.getJWTClaimsSet().getSubject();

        if (username == null || username.isEmpty()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED); // Nếu không tìm thấy username, throw exception
        }

        return username; // Trả về username
    }
    public String getRoleFromToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(token); // Xác thực token

        // Lấy role từ claims
        String roleName = (String) signedJWT.getJWTClaimsSet().getClaim("Role");

        if (roleName == null || roleName.isEmpty()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return roleName; // Trả về role
    }


}