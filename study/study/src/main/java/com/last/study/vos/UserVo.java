package com.last.study.vos;

public class UserVo {
    private final int index;
    private final String email;
    private final String password;
    private final String name;
    private final String nickname;
    private final String contact;
    private final int level;

    public UserVo(int index, String email, String password, String name, String nickname, String contact, int level) {
        this.index = index;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.contact = contact;
        this.level = level;
    }

    public UserVo(String email, String password, String name, String nickname, String contact) {
        this.index = 0;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.contact = contact;
        this.level = 10;
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

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContact() {
        return contact;
    }

    public int getLevel() {
        return level;
    }
}
