<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%
    Object userResetIndexObject = session.getAttribute("userResetIndex");
    int userResetIndex = -1;
    try{
        userResetIndex = Integer.parseInt(String.valueOf(userResetIndexObject));
    }catch(NumberFormatException ex) {
        userResetIndex = -1;
    }
    if( userResetIndex == -1) {
        response.sendRedirect("/reset");
        return;
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 재설정</title>
    <script defer src="/scripts/reset.js"></script>
</head>
<body>
    <form id="reset-code-form" method="POST">
        <input type="text" name="code" placeholder="인증번호" maxlength="6" autofocus>
        
        <input type="submit" value="확인">
    </form>
    <a href="/login">로그인페이지로 돌아가기</a>
</body>
</html>