package com.memsys.web.vos;

import java.util.ArrayList;

public class GetUserVo {
    private final int startPage;
    private final int endPage;
    private final int requestPage;
    private final ArrayList<UserVo> userVos;

    public GetUserVo(int startPage, int endPage, int requestPage, ArrayList<UserVo> userVos) {
        this.startPage = startPage;
        this.endPage = endPage;
        this.requestPage = requestPage;
        this.userVos = userVos;
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

    public ArrayList<UserVo> getUserVos() {
        return userVos;
    }
}
