package com.last.study.vos;

import com.last.study.enums.BoardResponseResult;

import java.util.ArrayList;

public class BoardResponseVo {
    private final BoardResponseResult boardResponseResult;
    private final ArrayList<ArticleVo> articles;
    private final int requestPage;
    private final int maxPage;
    private final int startPage;
    private final int endPage;

    public BoardResponseVo(BoardResponseResult boardResponseResult, ArrayList<ArticleVo> articles, int requestPage, int maxPage) {
        this.boardResponseResult = boardResponseResult;
        this.articles = articles;
        this.requestPage = requestPage;
        this.maxPage = maxPage % 10 == 0 ? (int)Math.ceil(maxPage / 10):(int)Math.ceil(maxPage / 10)+1;
        this.startPage = this.requestPage >= 6 ? this.requestPage - 5 : 1;
        this.endPage = Math.min(this.requestPage + 5, this.maxPage);
    }

    public BoardResponseVo(BoardResponseResult boardResponseResult, ArrayList<ArticleVo> articles) {
        this.boardResponseResult = boardResponseResult;
        this.articles = articles;
        this.requestPage = 0;
        this.maxPage = 0;
        this.startPage = 0;
        this.endPage = 0;
    }

    public BoardResponseResult getBoardResponseResult() {
        return boardResponseResult;
    }

    public ArrayList<ArticleVo> getArticles() {
        return articles;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public int getRequestPage() {
        return requestPage;
    }
}
