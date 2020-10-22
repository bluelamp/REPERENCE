package com.last.study.vos;

import com.last.study.enums.CommentResult;

import java.util.ArrayList;

public class CommentResponseVo {
    private final CommentResult commentResult;
    private final ArrayList<CommentVo> commentVoArrayList;

    public CommentResponseVo(CommentResult commentResult, ArrayList<CommentVo> commentVoArrayList) {
        this.commentResult = commentResult;
        this.commentVoArrayList = commentVoArrayList;
    }

    public CommentResult getCommentResult() {
        return commentResult;
    }

    public ArrayList<CommentVo> getCommentVoArrayList() {
        return commentVoArrayList;
    }
}
