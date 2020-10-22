<%@ page import="com.last.study.enums.BoardResponseResult" %>
<%@ page import="com.last.study.vos.ArticleVo" %>
<%@ page import="com.last.study.vos.CommentVo" %>
<%@ page import="com.last.study.vos.CommentResponseVo" %>
<%@ page import="com.last.study.vos.UserVo" %>
<%@ page import="java.util.ArrayList" %>
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

    Object commentResponseVoObject = session.getAttribute("CommentResponseVo");
    CommentResponseVo commentResponseVo = null;
    if(commentResponseVoObject instanceof CommentResponseVo || commentResponseVoObject != null) {
        commentResponseVo = (CommentResponseVo) commentResponseVoObject;
    }
    
    String requestId = request.getParameter("id") == null ? "ntc": (String)request.getParameter("id");
    String search = request.getParameter("searchmode") == null ? "false" : (String)request.getParameter("searchmode");
    String keyword = request.getParameter("keyword") == null ? "": (String)request.getParameter("keyword");
    String what = request.getParameter("what") == null ? "title": (String)request.getParameter("what");
    String searchPage = request.getParameter("page") == null ? "1" : (String)request.getParameter("page");
    
    String ArticleId = request.getParameter("article_id");
    Object articleVoObject = session.getAttribute("ArticleVo");
    ArticleVo resultArticle = null;
    if(articleVoObject instanceof ArticleVo) {
        resultArticle = (ArticleVo) articleVoObject;
    }
    
    
    String title = resultArticle.getTitle();
    String Writer = resultArticle.getWriter();
    String WrittenAt = resultArticle.getWrittenAt();
    int Hit = resultArticle.getHit();
    String Content = resultArticle.getContent();

    String result = request.getParameter("result");
    if(result != null) {
        out.print("<script>alert("+result+");</script>");
    }

%>



<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글</title>
</head>
<body>
    <%
    out.print("<div class=\"titleWrap\">");
        out.print("<div class=\"title\"> 제목 : ");
            out.print(title);
        out.print("</div>");
        out.print("<div class=\"writer\"> 글쓴이 : ");
            out.print(Writer);
        out.print("</div>");
        out.print("<div class=\"writtenAt\"> 작석일자 : ");
            out.print(WrittenAt);
        out.print("</div>");
        out.print("<div class=\"hit\"> 조회수 : ");
            out.print(Hit);
        out.print("</div>");
    out.print("</div>");
    out.print("<div class=\"content\"> 내용 : ");
        out.print(Content);
    out.print("</div>");

    out.print("<p>댓글</p>");


   
    if(commentResponseVo != null) {
        ArrayList<CommentVo> commentList = commentResponseVo.getCommentVoArrayList();
            out.print("<table>");
            out.print("<thead>");
                out.print("<tr>");
                    out.print("<th>닉네임</th>");
                    out.print("<th>내용</th>");
                    out.print("<th>작성일</th>");
                out.print("</tr>");
            out.print("<tbody>");
            for(CommentVo comment : commentList) {
                out.print("<tr>");
                    out.print("<td>");
                        out.print(comment.getUserNickname());
                    out.print("</td>");
                    out.print("<td>");
                        out.print(comment.getText());
                    out.print("</td>");
                    out.print("<td>");
                        out.print(comment.getWrittenAt());
                    out.print("</td>");
                out.print("</tr>");
            }
            out.print("</tbody>");
            out.print("</table>");
        session.setAttribute("CommentResponseVo", null);
    } else {
        out.print("<p>Empty</p>");
    }
    %>

    <form method="post" id="comment-form">
        <input type="text" name="comment" required>
        <input type="submit">
    </form>
    <%
    if(search.equals("false") || what == null || keyword == null) {
        out.print("<a href=\"/board1?id="+requestId+"&page="+searchPage+"\">목록으로</a>");
    } else {
        out.print("<a href=\"/search?id="+requestId+"&page="+searchPage+"&searchmode="+search+"&what="+what+"&keyword="+keyword+"\">목록으로</a>");
    }
    %>
</body>
</html>