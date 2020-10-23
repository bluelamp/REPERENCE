package com.memsys.web.vos;

import java.util.Date;

public class UserVo {
    private final int index;
    private final String email;
    private final String name;
    private final String nickname;
    private final String contact;
    private final String address;
    private final String birth;
    private final boolean isadmin;
    private final String userstatus;
    private final Date userCreatedAt;
    private final Date userSingedAt;
    private final Date userStatusChangedAt;
    private final Date userPasswordModifiedAt;

    public UserVo(int index, String email, String name, String nickname, String contact, String address, String birth, boolean isadmin, String userstatus, Date userCreatedAt, Date userSingedAt, Date userStatusChangedAt, Date userPasswordModifiedAt) {
        this.index = index;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.contact = contact;
        this.address = address;
        this.birth = birth;
        this.isadmin = isadmin;
        this.userstatus = userstatus;
        this.userCreatedAt = userCreatedAt;
        this.userSingedAt = userSingedAt;
        this.userStatusChangedAt = userStatusChangedAt;
        this.userPasswordModifiedAt = userPasswordModifiedAt;
    }

    public int getIndex() { return this.index; }

    public String getEmail() {
        return this.email;
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

    public boolean isAdmin() {
        return this.isadmin;
    }

    public String getUserstatus() {
        switch(this.userstatus) {
            case "ADM":
                return "관리자";
            case "OKY":
                return "정상";
            case "SUS":
                return "정지";
            case "DEL":
                return "삭제";
            default:
                return "";

        }

    }

    public Date getUserCreatedAt() {
        return this.userCreatedAt;
    }

    public Date getUserSingedAt() {
        return this.userSingedAt;
    }

    public Date getUserStatusChangedAt() {
        return this.userStatusChangedAt;
    }

    public Date getUserPasswordModifiedAt() {
        return this.userPasswordModifiedAt;
    }
}
