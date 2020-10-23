package com.memsys.web.vos;

public class LoginAttemptVo {
    private final String ip;
    private final String email;
    private final String password;
    private final String loginResult;


    public LoginAttemptVo(String ip, String email, String password, String loginResult) {
        this.ip = ip;
        this.email = email;
        this.password = password;
        this.loginResult = loginResult;
    }

    public String getIp() {
        return this.ip;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getLoginResult() {
        return this.loginResult;
    }
}
