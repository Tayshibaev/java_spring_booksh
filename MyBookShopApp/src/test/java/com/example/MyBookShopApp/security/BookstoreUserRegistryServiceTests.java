package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookstoreUserRegistryServiceTests {

    private final BookstoreUserDetailsService bookstoreUserDetailsService;

    private String email = "test@mail1.org";

    @MockBean
    private UserRepository bookstoreUserRepositoryMock;


    @Autowired
    public BookstoreUserRegistryServiceTests(BookstoreUserDetailsService bookstoreUserDetailsServiceMock) {
        this.bookstoreUserDetailsService = bookstoreUserDetailsServiceMock;
    }

    @Test
    void authorizationRegistryService() {
        Mockito.doReturn(new UserEntity())
                .when(bookstoreUserRepositoryMock)
                .findBookstoreUserByEmail(Mockito.any());

        UserDetails userDet = bookstoreUserDetailsService.loadUserByUsername(email);
        assertNotNull(userDet);
        Mockito.verify(bookstoreUserRepositoryMock, Mockito.times(1))
                .findBookstoreUserByEmail(Mockito.any());
    }

    @Test
    void authorizationRegistryServiceFail() {
        Mockito.doReturn(null)
                .when(bookstoreUserRepositoryMock)
                .findBookstoreUserByEmail(Mockito.any(String.class));
        assertThrows(UsernameNotFoundException.class, () -> {
            bookstoreUserDetailsService.loadUserByUsername(email);
        });
    }
}