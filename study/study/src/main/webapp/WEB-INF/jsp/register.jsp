<%@ page import="com.last.study.enums.UserRegisterResult" %>
<%@ page import="com.last.study.vos.UserVo" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%
UserRegisterResult userRegisterResult = null;
Object ObjuserRegisterResult = session.getAttribute("UserRegisterResult");
if(ObjuserRegisterResult instanceof UserRegisterResult) {
    userRegisterResult = (UserRegisterResult) ObjuserRegisterResult;
}
UserVo userRegisterVo = null;
Object ObjuserRegisterVo = session.getAttribute("UserRegisterVo");
if(ObjuserRegisterVo instanceof UserVo) {
    userRegisterVo = (UserVo) ObjuserRegisterVo;
}

if(userRegisterResult == UserRegisterResult.USER_REGISTER_RESULT_FAILURE) {
%><script>alert("회원가입을 실패하였습니다.");</script> <%
} else if( userRegisterResult == UserRegisterResult.USER_REGISTER_RESULT_FAILURE_EMAIL_DUPLICATED ||
userRegisterResult == UserRegisterResult.USER_REGISTER_RESULT_FAILURE_NICKNAME_DUPLICATED ||
userRegisterResult == UserRegisterResult.USER_REGISTER_RESULT_FAILURE_CONTACT_DUPLICATED) {
%><script>
    alert("이미 가입된 회원정보 중에 중복된 값이 있습니다. 다시 확인해주십시오.");
    
    // document.querySelectorAll("#registerform > input").forEach((element, index) => {
    //     element.value = "";
    //     console.log(element, index);
    // });
</script>
<% 
    
} else if( userRegisterResult == UserRegisterResult.USER_REGISTER_RESULT_SUCCESS) {
    UserVo userVo = userRegisterVo;
    session.setAttribute("UserVo", userVo);
    response.getWriter().print("<script>alert(\"회원가입을 축하드립니다.\");</script>");
    response.getWriter().print("<script>window.location.href=\"login\";</script>");
    
} 
session.setAttribute("UserRegisterResult", null);

%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <script defer src="/scripts/register.js"></script>
</head>
<body>
    <form id="register-form" method="post">
        <label for="input-email"></label>
        <input id="input-email" name="email" type="email" maxlength="100" placeholder="이메일" autofocus required value='<%= userRegisterVo != null ? userRegisterVo.getEmail() : "" %>'>
        <label for="input-password"></label>
        <input id="input-password" name="password" type="password" maxlength="100" placeholder="비밀번호" required>
        <label for="input-re-password"></label>
        <input id="input-re-password" name="repassword" type="password" maxlength="100" placeholder="비밀번호확인" required>
        <label for="input-name"></label>
        <input id="input-name" name="name" type="text" maxlength="20" placeholder="이름" required>
        <label for="input-nickname"></label>
        <input id="input-nickname" name="nickname" type="text" maxlength="20" placeholder="별명" required>
        <label for="input-contact"></label>
        <input id="input-contact" name="contact" type="number" maxlength="11" placeholder="연락처" required>
        <input type="reset" value="다시입력">
        <input type="submit" value="회원가입">
    </form>
</body>
</html>