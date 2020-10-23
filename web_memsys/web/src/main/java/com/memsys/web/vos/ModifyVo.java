package com.memsys.web.vos;

import com.memsys.utility.Sha512;

import java.util.Date;

public class ModifyVo {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+(?:[a-zA-Z]{2}|aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel)$";
    private static final String PASSWORD_REGEX = "^([0-9a-zA-Z~!@#$%^&*()\\\\-_=+\\\\[{\\\\]}\\\\\\\\|;:'\\\",<.>/?]{4,100})$";
    private final int index;
    private final String email;
    private final String password;
    private final String hashedpassword;
    private final String name;
    private final String nickname;
    private final String contact;
    private final String address;
    private final String birth;
    private final boolean isadmin;
    private final String userstatus;
    private boolean isNormalization = false;

    public ModifyVo(int index, String email, String password, String name, String nickname, String contact, String address, String birth, boolean isadmin, String userstatus) {
        if(email.matches(ModifyVo.EMAIL_REGEX) && password.matches(ModifyVo.PASSWORD_REGEX)) {
            this.index = index;
            this.email = email;
            this.password = password;
            this.hashedpassword = Sha512.hash(password);
            this.name = name;
            this.nickname = nickname;
            this.contact = contact;
            this.address = address;
            this.birth = birth;
            this.isadmin = isadmin;
            this.userstatus = userstatus;
            this.isNormalization = true;
        } else {
            this.index = 0;
            this.email = null;
            this.password = null;
            this.hashedpassword = null;
            this.name = null;
            this.nickname = null;
            this.contact = null;
            this.address = null;
            this.birth = null;
            this.isadmin = false;
            this.userstatus = null;
        }
    }

    public RegisterVo getRegisterVo() {
        return new RegisterVo(
            this.email,
            this.password,
            this.name,
            this.nickname,
            this.contact,
            this.address,
            this.birth,
            this.isadmin,
            this.userstatus
        );
    }

    public int getIndex() {
        return index;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getHashedpassword() {
        return hashedpassword;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getBirth() {
        return birth;
    }

    public boolean isIsadmin() {
        return isadmin;
    }

    public String getUserstatus() {
        return userstatus;
    }
}
