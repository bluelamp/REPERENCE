<%@ page import="com.last.study.vos.BoardResponseVo" %>
<%@ page import="com.last.study.enums.BoardResponseResult" %>
<%@ page import="com.last.study.vos.ArticleVo" %>
<%@ page import="com.last.study.vos.UserVo" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
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

    String search = request.getParameter("searchmode");
    String requestId = request.getParameter("id") == null ? "ntc": (String)request.getParameter("id");
    String keyword = request.getParameter("keyword") == null ? "": (String)request.getParameter("keyword");
    String what = request.getParameter("what") == null ? "title": (String)request.getParameter("what");
    String searchPage = request.getParameter("page") == null ? "1" : (String)request.getParameter("page");


    Object boardResponseVoObject = session.getAttribute("BoardResponseVo");
    BoardResponseVo boardResponseVo = null;
    if(boardResponseVoObject instanceof BoardResponseVo) {
        boardResponseVo = (BoardResponseVo) boardResponseVoObject;
    }

%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판</title>
    <script defer src="/scripts/board1.js"></script>
    <style>
        table {
            border-collapse: collapse;
        }
    </style>
</head>
<body>
    <%
    //assert boardResponseVo != null;  // boardResponseVo 는 Null 이 아니다! 라는 프로그램적 설정
    if(boardResponseVo.getBoardResponseResult() == BoardResponseResult.NO_MATCHING_ID) {
        out.print("<script>alert(\"존재하지 않는 게시판입니다.\"); window.location.href=\"/board1?id=ntc\"</script>");
    } else if (boardResponseVo.getBoardResponseResult() == BoardResponseResult.NOT_AUTHORIZED) {
        out.print("<script>alert(\"해당 게시판을 읽을 권한이 없습니다.\"); window.location.href=\"/board1?id=fre\"</script>");
        
    } else if(boardResponseVo.getBoardResponseResult() == BoardResponseResult.OKAY) { 
        out.println("<table id=\"board\">");
        out.println("<thead>");
        out.println("<tr><th>번호</th><th>제목</th><th>작성자</th><th>작성 일자</th><th>조회수</th></tr>");
        out.println("</thead>");
        out.println("<tbody>");
        for(ArticleVo article : boardResponseVo.getArticles()) {
            out.println(String.format("<tr><td>%d</td><td>%s</td><td>%s</td><td>%s</td><td>%d</td></tr>",
            article.getArticleId(),
            article.getTitle(),
            article.getWriter(),
            article.getWrittenAt(),
            article.getHit()
            ));
        }
        out.println("</tbody>");
        out.println("</table>");

        String id = request.getParameter("id") != null ? request.getParameter("id") : "ntc";

        out.println("<div>");
        out.println("<span><a href=\"board1?id="+id+"&page=1"+"&searchmode="+search+"\">처음</a></span>");
        out.println("<span><a href=\"board1?id="+id+"&page="+boardResponseVo.getMaxPage()+"&searchmode="+search+"\">끝</a></span>");

        int startPage = boardResponseVo.getStartPage();
        int endPage = boardResponseVo.getEndPage();
        for(int i = startPage; i <= endPage; i++) {
            if(i == boardResponseVo.getRequestPage()) {
                out.println("<span><strong>"+i+"</strong></span>");
            } else {
                out.println("<span><a href=\"board1?id="+id+"&page="+i+"&searchmode="+search+"&what="+what+"&keyword="+keyword+"\">"+i+"</a></span>");
            }
        }
        
        out.println("</div>");

        if(id.equals("fre")) {
            out.println("<span><a href=\"board1?id=ntc&page=1\">공지사항</a></span>");
        }
        if(id.equals("ntc")) {
            out.println("<span><a href=\"board1?id=fre&page=1\">자유게시판</a></span>");
        }
        
        out.println("<span><a href=\"write?id="+requestId+"&page="+searchPage+"\">글작성</a></span>");
    }
    
    %>

    <form id="search-form" method="get" action="search">
        <input type="hidden" name="searchmode" value="true">
        <select name="id">
            <% 
            if(requestId.equals("ntc")) {
                out.println("<option value=\"ntc\" selected>공지사항</option>");
                out.println("<option value=\"fre\">자유게시판</option>");
            } else {
                out.println("<option value=\"ntc\">공지사항</option>");
                out.println("<option value=\"fre\" selected>자유게시판</option>");
            }
            %>
        </select>
        <select name="what">
            <% 
            if(what.equals("title")) {
                out.println("<option value=\"title\" selected>제목</option>");
                out.println("<option value=\"title_content\">제목+내용</option>");
                out.println("<option value=\"nickname\">닉네임</option>");
            } else if(what.equals("title_content")){
                out.println("<option value=\"title\">제목</option>");
                out.println("<option value=\"title_content\" selected>제목+내용</option>");
                out.println("<option value=\"nickname\">닉네임</option>");
            } else {
                out.println("<option value=\"title\">제목</option>");
                out.println("<option value=\"title_content\">제목+내용</option>");
                out.println("<option value=\"nickname\" selected>닉네임</option>");
            }
            %>
        </select>
        <input type="text" name="keyword" value="<%= keyword%>">
        <input type="submit" value="검색">
    </form>

    <a href="logout">로그아웃</a>
    
    <script>
        let board = document.body.querySelector("#board");
        let articles = board.querySelectorAll("tbody > tr");
        console.log(articles);
        articles.forEach(function(el, index) {
            el.style = "cursor: pointer";
            el.addEventListener('mouseenter', function(bool) {
                el.style.background = "#aaa";        
            })
            el.addEventListener('mouseleave', function(bool) {
                el.style.background = "none";        
            })
            el.addEventListener('click', function() {
                let index = el.querySelector("td");
                window.location.href = "/article?article_id="+index.innerText+"&id=<%=requestId%>&page=<%=searchPage%>&searchmode=<%=search%>&what=<%=what%>&keyword=<%=keyword%>";
            })
        })
</script>
</body>
</html>