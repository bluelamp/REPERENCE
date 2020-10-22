package com.last.study.utility;

import com.last.study.vos.UserVo;

import javax.servlet.http.HttpServletRequest;

public class Variable {
    private Variable(){}

    public static UserVo getUserVo(HttpServletRequest request) {
        Object userVoObject = request.getSession().getAttribute("UserVo");
        UserVo userVo = null;
        if (userVoObject instanceof UserVo) {
            userVo = (UserVo) userVoObject;
        }
        return userVo;
    }

    public static UserVo getAnonymousUserVo() {
        UserVo userVo = new UserVo(15,"nan@nan", "nan", "비회원", "비회원", "nan", 10);
        return userVo;
    }

    public static boolean isSigned(HttpServletRequest request) {
        return Variable.getUserVo(request) != null;
    }
}
