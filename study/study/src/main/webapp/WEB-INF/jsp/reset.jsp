<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 재설정</title>
    <script defer src="/scripts/reset.js"></script>
</head>
<body>
    <form id="reset-form" method="POST">
        <input type="email" name="email" placeholder="이메일" maxlength="100" autofocus>
        <input type="tel" name="contact" placeholder="연락처" maxlength="11">

        <input type="submit" value="비밀번호 찾기">
    </form>
    <a href="/login">로그인페이지로 돌아가기</a>
</body>
</html>