package com.last.study.enums;

public enum BoardWriteResult {
    NO_MATCHING_ID,     // 일치하는 게시판 없음.
    FAILURE,  // 게시글 실패
    NOT_ALLOWED,            //허가 되지 않음.
    SUCCESS             // 게시글 쓰기 성공
}
