package com.last.study.vos;

import java.util.Date;

public class BoardVo {
    private final int index;
    private final String boardKategorie;
    private final String title;
    private final String userNickname;
    private final Date Datetime;
    private final int views;
    private final boolean admin;

    public BoardVo(int index, String boardKategorie, String title, String userNickname, Date Datetime, int views) {
        this.index = index;
        this.boardKategorie = boardKategorie;
        this.title = title;
        this.userNickname = userNickname;
        this.Datetime = Datetime;
        this.views = views;
        this.admin = false;
    }

    public int getIndex() {
        return index;
    }

    public String getBoardKategorie() {
        return boardKategorie;
    }

    public String getTitle() {
        return title;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public Date getDatetime() {
        return Datetime;
    }

    public int getViews() {
        return views;
    }

    public boolean isAdmin() {
        return admin;
    }
}
