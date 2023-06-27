package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BookstoreUserAuthorizationTests {

    private final BookstoreUserRegister userRegister;
    private final PasswordEncoder passwordEncoder;
    private RegistrationForm registrationForm;
    private ContactConfirmationPayload payload;


    private String email = "test@mail1.org";
    private String pass = "iddqd1";
    private String name = "Tester1";
    private String phone = "90312323231";
    private String jwtToken = "qwerty123";

    @MockBean
    private AuthenticationManager authenticationManagerMock;

    @MockBean
    private JWTUtil jWTUtil;

    @MockBean
    private BookstoreUserDetailsService bookstoreUserDetailsServiceMock;


    @Autowired
    public BookstoreUserAuthorizationTests(BookstoreUserRegister userRegister, PasswordEncoder passwordEncoder) {
        this.userRegister = userRegister;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() {
        payload = new ContactConfirmationPayload();
        registrationForm = new RegistrationForm();
        registrationForm.setMail(email);
        registrationForm.setName(name);
        registrationForm.setPass(pass);
        registrationForm.setPhone(phone);

        payload.setCode(pass);
        payload.setContact(email);
    }

    @AfterEach
    void tearDown() {
        registrationForm = null;
    }

    @Test
    void authorizationLogin() {
        ContactConfirmationResponse response = userRegister.login(payload);
        assertNotNull(response);
        assertEquals("true", response.getResult(), "Response is True");
        Mockito.verify(authenticationManagerMock, Mockito.times(1))
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void authorizationLoginFail() {
        Mockito.doThrow(BadCredentialsException.class)
                .when(authenticationManagerMock)
                .authenticate(Mockito.any());

        assertThrows(BadCredentialsException.class, () -> userRegister.login(payload));
    }

    @Test
    void authorizationLoginJwt() {
        Mockito.doReturn(jwtToken)
                .when(jWTUtil)
                .generateToken(Mockito.any(UserDetails.class));
        Mockito.doReturn(new BookstoreUserDetails(new UserEntity()))
                .when(bookstoreUserDetailsServiceMock)
                .loadUserByUsername(Mockito.any(String.class));
        ContactConfirmationResponse response = userRegister.jwtLogin(payload);
        assertNotNull(response);
        assertEquals(jwtToken, response.getResult(), "Jwt token is valid");
        Mockito.verify(authenticationManagerMock, Mockito.times(1))
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(bookstoreUserDetailsServiceMock, Mockito.times(1))
                .loadUserByUsername(Mockito.any(String.class));
        Mockito.verify(jWTUtil, Mockito.times(1))
                .generateToken(Mockito.any(UserDetails.class));
    }

    @Test
    void authorizationLoginJwtFail() {
        Mockito.doReturn(jwtToken)
                .when(jWTUtil)
                .generateToken(Mockito.any(UserDetails.class));
        Mockito.doThrow(UsernameNotFoundException.class)
                .when(bookstoreUserDetailsServiceMock)
                .loadUserByUsername(Mockito.any(String.class));
        assertThrows(UsernameNotFoundException.class, () -> userRegister.jwtLogin(payload));
    }
}