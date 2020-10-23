package com.memsys.web.vos;

import com.memsys.utility.Sha512;

import java.util.Date;

public class RegisterVo {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+(?:[a-zA-Z]{2}|aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel)$";
    private static final String PASSWORD_REGEX = "^([0-9a-zA-Z~!@#$%^&*()\\\\-_=+\\\\[{\\\\]}\\\\\\\\|;:'\\\",<.>/?]{4,100})$";

    private final String email;
    private final String password;
    private final String hashedPassword;
    private final String name;
    private final String nickname;
    private final String contact;
    private final String address;
    private final String birth;
    private final boolean isadmin;
    private final String status;
    private boolean isNormalization = false;

    public RegisterVo(String email, String password, String name, String nickname, String contact, String address, String birth, boolean isadmin, String status) {
        if(email.matches(RegisterVo.EMAIL_REGEX) && password.matches(RegisterVo.PASSWORD_REGEX)) {
            this.email = email;
            this.password = password;
            this.hashedPassword = Sha512.hash(password);
            this.name = name;
            this.nickname = nickname;
            this.contact = contact;
            this.address = address;
            this.birth = birth;
            this.isadmin = isadmin;
            if(this.isadmin == true)
                this.status = "ADM";
            else
                this.status = status;
            this.isNormalization = true;
        } else {
            this.email = null;
            this.password = null;
            this.hashedPassword = null;
            this.name = null;
            this.nickname = null;
            this.contact = null;
            this.address = null;
            this.birth = null;
            this.isadmin = false;
            this.status = null;
        }
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public String getName() {
        return this.name;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getContact() {
        return this.contact;
    }

    public String getAddress() {
        return this.address;
    }

    public String getBirth() {
        return this.birth;
    }

    public boolean isIsadmin() {
        return this.isadmin;
    }

    public String getStatus() {
        return this.status;
    }
}
