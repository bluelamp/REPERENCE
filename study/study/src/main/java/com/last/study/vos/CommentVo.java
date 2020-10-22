package com.last.study.vos;

import java.text.SimpleDateFormat;

public class CommentVo {
    private final String articleId;
    private final String text;
    private final String writtenAt;
    private final String UserNickname;

    public CommentVo(String articleId, String text, String writtenAt, String userNickname) {
        this.articleId = articleId;
        this.text = text;
        this.writtenAt = writtenAt;
        this.UserNickname = userNickname;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getText() {
        return text;
    }

    public String getWrittenAt() {
        return writtenAt;
    }

    public String getUserNickname() {
        return UserNickname;
    }
}
