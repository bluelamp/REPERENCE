<%@ page import="com.last.study.vos.UserVo" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%
    Object userVoObject = session.getAttribute("UserVo");
    UserVo userVo = null;
    if(userVoObject instanceof UserVo) {
        userVo = (UserVo) userVoObject;
    }
    if(userVo == null) {
        response.sendRedirect("/login");
        return;
    } else {
        %>
        <script>
            console.log('<%= userVo.getName() %>');
            sessionStorage.clear();
            sessionStorage.setItem("UserName", '<%= userVo.getName() %>');
        </script>
        <%
    }
    String requestId = request.getParameter("id") == null ? "ntc": (String)request.getParameter("id");
    String searchPage = request.getParameter("page") == null ? "1" : (String)request.getParameter("page");


/*
    Object boardWriteVoObject = session.getAttribute("BoardWriteVo");
    BoardWriteVo boardWriteVo = null;
    if(boardWriteVoObject instanceof BoardWriteVo) {
        boardWriteVo = (BoardWriteVo) boardWriteVoObject;
    }

   Object boardWriteResultObject = session.getAttribute("BoardWriteResult");
    BoardWriteResult boardWriteResult = null;
    if(boardWriteResultObject instanceof BoardWriteResult) {
        boardWriteResult = (BoardWriteResult) boardWriteResultObject;
    }
    if(boardWriteResult == null) {
        response.sendRedirect("/write");
        %>
        <script>alert("다시 작성해주십시오")</script>
        <%
    } else {

    }
*/
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script defer src="/scripts/write.js"></script>
    <title>글 작성 페이지</title>
</head>
<body>
    <form id="write-form" method="post">
        <input type="hidden" name="page" value="<%=searchPage%>">
        <label for="id">게시판</label> <br/>
        <select name="idBoard">
            <% 
            if(requestId.equals("ntc")) {
                out.println("<option value=\"ntc\" selected>공지사항</option>");
                out.println("<option value=\"fre\">자유게시판</option>");
            } else {
                out.println("<option value=\"ntc\">공지사항</option>");
                out.println("<option value=\"fre\" selected>자유게시판</option>");
            }
            %>
        </select> <br/>
        <label for="title">제목</label> <br/>
        <input type="text" name="title" id="title" placeholder="제목" maxlength="500" required autofocus> <br/>
        <label for="content">내용</label> <br/>
        <textarea name="content" id="content" cols="30" rows="10" maxlength="10000"></textarea> <br/>
        <input type="submit" value="작성">
    </form>
    <a href="/board1?id=<%= requestId %>&page=<%=searchPage%>">돌아가기</a>
</body>
</html>