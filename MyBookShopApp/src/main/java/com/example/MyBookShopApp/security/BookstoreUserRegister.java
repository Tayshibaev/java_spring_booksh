package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.UserRepository;
import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BookstoreUserRegister {

    private final BookstoreUserRepository bookstoreUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final JWTUtil jwtUtil;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final UserRepository userRepository;

    @Autowired
    public BookstoreUserRegister(BookstoreUserRepository bookstoreUserRepository, PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 BookstoreUserDetailsService bookstoreUserDetailsService, JWTUtil jwtUtil, OAuth2AuthorizedClientService authorizedClientService, UserRepository userRepository) {
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.authorizedClientService = authorizedClientService;
        this.userRepository = userRepository;
    }

    public void registerNewUser(RegistrationForm registrationForm) {

        if (bookstoreUserRepository.findBookstoreUserByEmail(registrationForm.getMail()) == null) {
            BookstoreUser user = new BookstoreUser();
            UserEntity userMain = new UserEntity();
            userMain.setBalance(ThreadLocalRandom.current().nextInt(0, 1000));
            userMain.setName(registrationForm.getName());
            userMain.setRegTime(new Date());
            userMain.setHash(registrationForm.getName().replaceAll(" ","").toLowerCase()
                    + "_" + ThreadLocalRandom.current().nextInt(0, 100));


            user.setName(registrationForm.getName());
            user.setEmail(registrationForm.getMail());
            user.setPhone(registrationForm.getPhone());
            user.setPassword(passwordEncoder.encode(registrationForm.getPass()));

            user.setUserId(userMain);
            userMain.setUserInfoAdditional(user);

            bookstoreUserRepository.save(user);
            //userRepository.save(userMain);
        }
    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                        payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public void saveInfoClientFromFacebook(Map<String, String> userAttributes) {
        String email = userAttributes.get("email");
        String name = userAttributes.get("name");
        String password = "";
        String phone = "";
        if (bookstoreUserRepository.findBookstoreUserByEmail(email) == null) {
            BookstoreUser user = new BookstoreUser();
            UserEntity userMain = new UserEntity();
            userMain.setBalance(ThreadLocalRandom.current().nextInt(0, 1000));
            userMain.setName(name);
            userMain.setRegTime(new Date());
            userMain.setHash(name.replaceAll(" ","").toLowerCase()
                    + "_" + ThreadLocalRandom.current().nextInt(0, 100));

            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPassword(passwordEncoder.encode(password));

            user.setUserId(userMain);
            userMain.setUserInfoAdditional(user);

            bookstoreUserRepository.save(user);
           // userRepository.save(userMain);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,
                ""));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Object getCurrentUser() {
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getBookstoreUser();
    }
}
