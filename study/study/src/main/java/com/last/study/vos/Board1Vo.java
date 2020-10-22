package com.last.study.vos;

import com.last.study.interfaces.IBoardIdImpl;

public class Board1Vo implements IBoardIdImpl {
    private final String id;
    private final int page;

    public Board1Vo(String id, String page) {
        this.id = id;

        int pageNum;
        try{
            pageNum = Integer.parseInt(page);
        } catch(NumberFormatException ignored) {
            pageNum = 1;
        }
        this.page = pageNum;
    }

    @Override
    public String getId() {
        return id;
    }

    public int getPage() {
        return page;
    }
}
