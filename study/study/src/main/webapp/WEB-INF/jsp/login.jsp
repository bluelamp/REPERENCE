<%@ page import="com.last.study.enums.UserLoginResult" %>
<%@ page import="com.last.study.vos.UserVo" %>
<%@ page import="com.last.study.vos.UserLoginVo"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%
Object userVoObject = session.getAttribute("UserVo");
UserVo userVo = null;
if(userVoObject instanceof UserVo) {
    userVo = (UserVo) userVoObject;
}
if(userVo != null) {
    response.sendRedirect("/board1");
    return;
}

Object userLoginVoObject = session.getAttribute("UserLoginVo");
UserLoginVo userLoginVo = null;
String predefinedEmail = "admin@sample.com";
String predefinedPassword = "test1234";
if(userLoginVoObject instanceof UserLoginVo) {
    userLoginVo = (UserLoginVo) userLoginVoObject;
}
if(userLoginVo != null) {
    predefinedEmail = userLoginVo.getEmail();
    predefinedPassword = userLoginVo.getPassword();
}
session.setAttribute("UserLoginVo", null);

Object userLoginResultObject = session.getAttribute("UserLoginResult");
UserLoginResult userLoginResult = null;
if(userLoginResultObject instanceof UserLoginResult) {
    userLoginResult = (UserLoginResult) userLoginResultObject;
}
session.setAttribute("UserLoginResult", null);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>안녕하세요</title>
    <script defer src="/scripts/login.js"></script>
</head>
<body>
    <!-- form 요소의 action 속성 = 값을 전달 해줄 목적지 주소(생략시 현재 주소) -->
    <form method="POST" id="login-form">
        <label for="login-email">이메일</label>
        <input type="email" name="email" id="login-email" placeholder="이메일" maxlengtho="100" autofocus value="<%= predefinedEmail %>">
        <label for="login-password">비밀번호</label>
        <input type="password" name="password"  id="login-password" placeholder="비밀번호" maxlength="100" value="<%=predefinedPassword%>">
        <input type="submit" value="로그인">
    </form>
    <a href="/register" target="_self">회원가입</a>
    <a href="/reset" >비밀번호 분실</a>

    <%
    if(userLoginResult != null) {
        if(userLoginResult != UserLoginResult.USER_LOGIN_RESULT_SUCCESS) {
        %>
        <script>alert("올바르지않은 이메일 혹은 비밀번호입니다.");</script>
        <%
        }
    }
    
    %>
</body>
</html>