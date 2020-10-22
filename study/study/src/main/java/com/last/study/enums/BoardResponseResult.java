package com.last.study.enums;

public enum BoardResponseResult {
    NO_MATCHING_ID,     // 일치하는 게시판 없음.
    NOT_AUTHORIZED,     // 게시판 읽을 권한이 없음.
    NOT_AUTHORIZED_WRITTEN, //게시판 쓰기 권한이 없음.
    OKAY                // 정상
}
