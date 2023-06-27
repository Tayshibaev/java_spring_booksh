package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.user.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public class PhoneNumberUserDetails extends BookstoreUserDetails {

    public PhoneNumberUserDetails(UserEntity bookstoreUser) {
        super(bookstoreUser);
    }

    @Override
    public String getUsername() {
        return getBookstoreUser().getPhone();
    }
}
