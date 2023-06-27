package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.UserRepository;
import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BookstoreUserRegister {

    private final UserRepository bookstoreUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final JWTUtil jwtUtil;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final UserRepository userRepository;

    @Autowired
    public BookstoreUserRegister(UserRepository bookstoreUserRepository, PasswordEncoder passwordEncoder,
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

    public UserEntity registerNewUser(RegistrationForm registrationForm) {

        UserEntity byEmail = bookstoreUserRepository.findBookstoreUserByEmail(registrationForm.getMail());
        UserEntity byPhone = bookstoreUserRepository.findBookstoreUserByPhone(registrationForm.getPhone());

        if (byEmail == null && byPhone == null) {
            UserEntity userMain = new UserEntity();
            String name = registrationForm.getName() == null ? "" : registrationForm.getName();
            userMain.setBalance(ThreadLocalRandom.current().nextInt(0, 1000));
            userMain.setName(name);
            userMain.setRegTime(new Date());

            userMain.setHash(name.replaceAll(" ", "").toLowerCase()
                    + "_" + ThreadLocalRandom.current().nextInt(0, 100));


            userMain.setEmail(registrationForm.getMail());
            userMain.setPhone(registrationForm.getPhone());
            userMain.setPassword(passwordEncoder.encode(registrationForm.getPass()));


//            userRepository.save(userMain);
            bookstoreUserRepository.save(userMain);
            return userMain;
            //
        } else {
            return byPhone;
        }
//        return null;
    }

    public UserEntity changeDataClient(RegistrationForm registrationForm) {
        String name = registrationForm.getName() == null ? "" : registrationForm.getName();
        UserEntity user = (UserEntity) getCurrentUser();

        user.setEmail(registrationForm.getMail());
        user.setPhone(registrationForm.getPhone());
        user.setName(name);
        user.setPassword(passwordEncoder.encode(registrationForm.getPass()));
        bookstoreUserRepository.save(user);
        return user;
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
            UserEntity userMain = new UserEntity();
            userMain.setBalance(ThreadLocalRandom.current().nextInt(0, 1000));
            userMain.setName(name);
            userMain.setRegTime(new Date());
            userMain.setHash(name.replaceAll(" ", "").toLowerCase()
                    + "_" + ThreadLocalRandom.current().nextInt(0, 100));

            userMain.setEmail(email);
            userMain.setPhone(phone);
            userMain.setPassword(passwordEncoder.encode(password));

            bookstoreUserRepository.save(userMain);
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

    public ContactConfirmationResponse jwtLoginByPhoneNumber(ContactConfirmationPayload payload) {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setPhone(payload.getContact());
        registrationForm.setPass(payload.getCode());
        registerNewUser(registrationForm);
        UserDetails userDetails = bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }
}
