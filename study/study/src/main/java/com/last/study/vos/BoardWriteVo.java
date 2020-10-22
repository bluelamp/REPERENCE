package com.last.study.vos;

import com.last.study.interfaces.IBoardIdImpl;

public class BoardWriteVo implements IBoardIdImpl {
    private final String id;
    private final String title;
    private final String content;
    private final String userEmail;

    public BoardWriteVo(String id, String title, String content, String userEmail) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userEmail = userEmail;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
