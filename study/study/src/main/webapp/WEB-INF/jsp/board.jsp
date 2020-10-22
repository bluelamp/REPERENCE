<%@ page import="com.last.study.vos.BoardVo" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
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

    Object jsonboardVoObject = session.getAttribute("jsonBoardVo");
    JSONArray boardVoArray = null;
    if(jsonboardVoObject instanceof JSONArray) {
        boardVoArray = (JSONArray) jsonboardVoObject;
    }
    session.setAttribute("jsonBoardVo", null);
%> 
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판</title>
    <script defer src="/scripts/board.js"></script>
    <style>
        body > table  {
            border-collapse: collapse;
        }
        body > table > thead > tr, body > table > tbody > tr ,body > table > thead > tr > th, body > table > tbody > tr > td {
            border: 1px solid #000;
        }
    </style>
</head>
<body>
    <table id="myboard">
        <thead>
            <tr>
                <th>No</th>
                <th>분류</th>
                <th>제목</th>
                <th>게시자</th>
                <th>게시일자</th>
                <th>조회수</th>
                <th class="adminClass">관리자권한 수정</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>1</td>
                <td>공지사항</td>
                <td>안녕하십니까 게시판입니다</td>
                <td>관리자</td>
                <td>2020-10-07</td>
                <td>1234</td>
                <td class="adminClass">관리자권한 수정</td>
            </tr>
            <tr>
                <td>2</td>
                <td>공지사항</td>
                <td>안녕하십니까 게시판입니다</td>
                <td>관리자</td>
                <td>2020-10-07</td>
                <td>1234</td>
                <td class="adminClass">관리자권한 수정</td>
            </tr>
            <tr>
                <td>3</td>
                <td>공지사항</td>
                <td>안녕하십니까 게시판입니다</td>
                <td>관리자</td>
                <td>2020-10-07</td>
                <td>1234</td>
                <td class="adminClass">관리자권한 수정</td>
            </tr>
            <tr>
                <td>4</td>
                <td>공지사항</td>
                <td>안녕하십니까 게시판입니다</td>
                <td>관리자</td>
                <td>2020-10-07</td>
                <td>1234</td>
                <td class="adminClass">관리자권한 수정</td>
            </tr>
            <tr>
                <td>5</td>
                <td>공지사항</td>
                <td>안녕하십니까 게시판입니다</td>
                <td>관리자</td>
                <td>2020-10-07</td>
                <td>1234</td>
                <td class="adminClass">관리자권한 수정</td>
            </tr>
        </tbody>
    </table>
    <form method="post" id="form-hidden" onSubmit="return checkFields()">
        <input type="hidden" name="page" value="1">
        <input type="button" value="1">
        <input type="button" value="2">
        <input type="button" value="3">
        <input type="button" value="4">
        <input type="button" value="5">
    </form>

    <form method="post" id="form-myboard">
        <input type="button" value="수정"  name="editPost">
        <input type="button" value="삭제"  name="delPost">
        <input type="button" value="글쓰기" name="addPost">
    </form>
    <script>
        console.log('<%=boardVoArray%>');
        let jsonArray = JSON.parse('<%=boardVoArray%>');
    </script>
    
</body>
</html>