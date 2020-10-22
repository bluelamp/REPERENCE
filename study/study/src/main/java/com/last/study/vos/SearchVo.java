package com.last.study.vos;

import com.last.study.interfaces.IBoardIdImpl;

public class SearchVo implements IBoardIdImpl {
    private final String id;
    private final int page;
    private final String what;
    private final String keyword;

    public SearchVo(String id, int page, String what, String keyword) {
        this.id = id;
        this.page = page;
        this.what = what;
        this.keyword = keyword;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getWhat() {
        return what;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getPage() {
        return page;
    }
}
