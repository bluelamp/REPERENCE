package com.memsys.web.vos;

import com.memsys.utility.Sha512;

public class LoginVo {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+(?:[a-zA-Z]{2}|aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel)$";
    private static final String PASSWORD_REGEX = "^([0-9a-zA-Z~!@#$%^&*()\\\\-_=+\\\\[{\\\\]}\\\\\\\\|;:'\\\",<.>/?]{4,100})$";
    private final String email;
    private final String password;
    private final String hashedpassword;
    private boolean isNormalization = false;

    public LoginVo(String email, String password) {
        if(email.matches(LoginVo.EMAIL_REGEX) && password.matches(LoginVo.PASSWORD_REGEX)) {
            this.email = email;
            this.password = password;
            this.hashedpassword = Sha512.hash(password);
            this.isNormalization = true;
        } else {
            this.email = null;
            this.password = null;
            this.hashedpassword = null;
        }
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getHashedpassword() {
        return this.hashedpassword;
    }

    public boolean isNormalization() {
        return this.isNormalization;
    }
}
