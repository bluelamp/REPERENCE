package com.last.study.vos;

import javax.servlet.http.HttpSession;

public class UserLoginVo {
    private final String email;
    private final String password;
    private final HttpSession session;

    public UserLoginVo(String email, String password, HttpSession session) {
        this.email = email;
        this.password = password;
        this.session = session;
    }


    public HttpSession getSession() {
        return session;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
