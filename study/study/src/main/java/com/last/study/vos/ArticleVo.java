package com.last.study.vos;

public class ArticleVo {
    private final int articleId;
    private final String title;
    private final String content;
    private final String writer;
    private final String writtenAt;
    private final int hit;

    public ArticleVo(int articleId, String title, String content, String writer, String writtenAt, int hit) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.writtenAt = writtenAt;
        this.hit = hit;
    }

    public int getArticleId() {
        return articleId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public String getWrittenAt() {
        return writtenAt;
    }

    public int getHit() {
        return hit;
    }
}
