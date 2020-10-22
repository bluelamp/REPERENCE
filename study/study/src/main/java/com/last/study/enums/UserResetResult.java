package com.last.study.enums;

public enum UserResetResult {
    NO_MATCHING_USER,       //1단계에서 일치하는 회원을 찾을수 없었을때
    CODE_SENT,               //1단계에서 일치하는 회원을 찾아서, 인증번호를 전송한 후.
    CODE_GOOD,              //2단계에서 올바른 코드를 적은 경우
    CODE_NONO,               //2단계에서 코드를 잘못 적은 경우
    MODIFY_FAILURE,         //3단계에서 모종의 이유로 수정 실패했을 경우,
    MODIFY_SUCCESS          //3단계에서 최종 수정 완료.
}
