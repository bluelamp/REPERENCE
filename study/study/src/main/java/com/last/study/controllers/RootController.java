package com.last.study.controllers;

import com.last.study.enums.*;
import com.last.study.services.Board1Service;
import com.last.study.services.BoardService;
import com.last.study.services.UserService;
import com.last.study.vos.*;
import jdk.jshell.spi.ExecutionControl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.events.Comment;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Controller
public class RootController {
    private final UserService userService;
    private final BoardService boardService;
    private final Board1Service board1Service;
    @Autowired
    public RootController(UserService userService, BoardService boardService, Board1Service board1Service) {
        this.userService = userService;
        this.boardService = boardService;
        this.board1Service = board1Service;
    }




//****************************************************************    로그인
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String loginGet(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public void loginPost(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(name="email", defaultValue = "") String email,
                          @RequestParam(name="password", defaultValue = "") String password) throws IOException, SQLException {
        PrintWriter out = response.getWriter();
        System.out.println("로그인 요청됨 (이메일 : "+ email+ " / 비밀번호 : "+ password+")");

        UserLoginVo userLoginVo = new UserLoginVo(email, password, request.getSession());
        UserLoginResult userLoginResult = this.userService.login(userLoginVo);
        out.print(userLoginResult.name());

        request.getSession().setAttribute("UserLoginVo", userLoginVo);
        request.getSession().setAttribute("UserLoginResult", userLoginResult);

        response.sendRedirect("/login");
    }

    //****************************************************************    로그아웃
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public void logOutGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();

        if(session.getAttribute("UserVo") != null) {
            session.setAttribute("UserVo", null);
        }
        response.sendRedirect("/login");
    }


    
//****************************************************************    회원가입
    @RequestMapping(value="/register", method= RequestMethod.GET)
    public String registerGet(HttpServletRequest request, HttpServletResponse response) {
        return "register";
    }

    @RequestMapping(value="/register", method= RequestMethod.POST)
    public void registerPost(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(name="email", defaultValue = "") String email,
                             @RequestParam(name="password", defaultValue = "") String password,
                             @RequestParam(name="name", defaultValue = "") String name,
                             @RequestParam(name="nickname", defaultValue = "") String nickname,
                             @RequestParam(name="contact", defaultValue = "") String contact
    ) throws IOException, SQLException {
        response.setContentType("text/html; charset=UTF-8;");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        UserVo registerVo = new UserVo(email, password, name, nickname, contact);
        UserRegisterResult userRegisterResult = this.userService.register(registerVo);

        session.setAttribute("UserRegisterResult", userRegisterResult);
        session.setAttribute("UserRegisterVo", registerVo);

        response.sendRedirect("/register");
    }
    
    
//****************************************************************    비밀번호 분실
    @RequestMapping(value="/reset", method= RequestMethod.GET)
    public String resetGet(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(name="step", defaultValue = "1") String step) {
        switch(step) {
            case "2":
                return "reset-code";
            case "3":
                return "reset-modify";
            default:
                return "reset";
        }
    }

    @RequestMapping(value="/reset", method= RequestMethod.POST)
    public void resetPost(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(name="step", defaultValue = "1") String step,
                           @RequestParam(name="email", defaultValue = "") String email,
                           @RequestParam(name="contact", defaultValue = "") String contact,
                          @RequestParam(name="code", defaultValue = "") String code) throws IOException, SQLException {
        response.setContentType("text/html; charset=UTF-8;");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        switch(step) {
            case "1":
                UserResetVo userResetVo = new UserResetVo(email, contact);
                UserResetResult userResetResult = this.userService.reset(request.getSession(), userResetVo);
                if(userResetResult == UserResetResult.CODE_SENT) {
                    response.sendRedirect("/reset?step=2");
                } else {
                    response.sendRedirect("/reset?result=no_matching_user");
                }
                break;
            case "2":
                UserResetResult codeCheckResult = this.userService.reset(request.getSession(), code);
                if( codeCheckResult == UserResetResult.CODE_GOOD) {
                    response.sendRedirect("/reset?step=3");
                } else {
                    response.sendRedirect("/reset?step=2&result=code_nono");
                }
                break;
            case "3":
                String password = request.getParameter("password");
                UserResetResult Result = this.userService.modifyPassword(request.getSession(), password);
                if(Result == UserResetResult.MODIFY_SUCCESS) {
                    session.setAttribute("userResetIndex", null);
                    response.sendRedirect("/login");
                } else if(Result == UserResetResult.MODIFY_FAILURE) {
                    response.sendRedirect("/reset?step=3&result=failure");
                }
                break;

        }
    }




//****************************************************************    내 게시판
    @RequestMapping(value="/board", method= RequestMethod.GET)
    public String boardGet(HttpServletRequest request, HttpServletResponse response) {
        return "board";
    }

    @RequestMapping(value="/board", method= RequestMethod.POST)
    public void boardPost(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(name="page", defaultValue = "1") String page) throws IOException, SQLException {
        response.setContentType("text/html; charset=UTF-8;");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        ArrayList<BoardVo> listBoard = null;
        listBoard = this.boardService.viewBoardinPage(Integer.parseInt(page));

        if( !listBoard.isEmpty()) {
            JSONArray jsonArrayBoard = new JSONArray();
            Iterator iterator = listBoard.iterator();
            while(iterator.hasNext()) {
                BoardVo boardVo = (BoardVo)iterator.next();
                JSONObject jsonObjectBoard = new JSONObject();
                jsonObjectBoard.put("index", boardVo.getIndex());
                jsonObjectBoard.put("kategorie", boardVo.getBoardKategorie());
                jsonObjectBoard.put("title", boardVo.getTitle());
                jsonObjectBoard.put("nickName", boardVo.getUserNickname());
                jsonObjectBoard.put("createAt", (new SimpleDateFormat("yyyy-MM-dd").format(boardVo.getDatetime())).toString());
                jsonObjectBoard.put("views", boardVo.getViews());
                jsonArrayBoard.put(jsonObjectBoard);
            }
            session.setAttribute("jsonBoardVo", jsonArrayBoard);
        }
        response.sendRedirect("/board");
    }





    //****************************************************************    게시글 보기
    @RequestMapping(value="/article", method= RequestMethod.GET)
    public String ArticleGet(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(name="id", defaultValue = "ntc") String id,
                                  @RequestParam(name="page", defaultValue = "1") String page,
                                 @RequestParam(name="article_id", defaultValue = "1") String articleId,
                                 @RequestParam(name="searchmode", defaultValue = "false") String search,
                                  @RequestParam(name="what", defaultValue = "1") String what,
                                  @RequestParam(name="keyword", defaultValue = "1") String keyword,
                                  @RequestParam(name="index", defaultValue = "1") String index) throws IOException, SQLException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        ArticleVo articleVo= null;
        CommentResponseVo commentResponseVo = null;
        ArrayList<CommentVo> commentVoArrayList = null;
        try {
            articleVo = this.board1Service.selectArticle(request.getSession(), Integer.parseInt(index));
            commentResponseVo = new CommentResponseVo(CommentResult.RECEIVED_SUCCESS, this.board1Service.selectComments(request.getSession(),articleId));
        } catch(NumberFormatException ignored) {}
        session.setAttribute("ArticleVo", articleVo);
        session.setAttribute("CommentResponseVo", commentResponseVo);
        return "article";
    }

//****************************************************************    댓글 달기
    @RequestMapping(value="/article", method= RequestMethod.POST)
    public String ArticlePost(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(name="article_id", defaultValue = "") String articleId,
                             @RequestParam(name="id", defaultValue = "ntc") String id,
                             @RequestParam(name="page", defaultValue = "1") String page,
                              @RequestParam(name="searchmode", defaultValue = "false") String search,
                              @RequestParam(name="what", defaultValue = "") String what,
                             @RequestParam(name="keyword", defaultValue = "") String keyword,
                             @RequestParam(name="commenttext", defaultValue = "1") String text
                             ) throws IOException, SQLException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        CommentVo commentVo = new CommentVo(articleId, text, "nan", "nan");
        CommentResult commentResult;
        commentResult = this.board1Service.insertComment(request.getSession(), commentVo);

        if(what.equals("") || keyword.equals("")) {
            response.sendRedirect(String.format("/article?article_id=%s&id=%s&page=%s&result=%s",
                    articleId,
                    id,
                    page,
                    commentResult.name()));
        } else {
            response.sendRedirect(String.format("/search?article_id=%s&id=%s&page=%s&searchmod=%s&what=%s&&keyword=%s&result=%s",
                    articleId,
                    id,
                    page,
                    search,
                    what,
                    keyword,
                    commentResult.name()));
        }

        return "article";
    }


//****************************************************************    게시글 작성
    @RequestMapping(value="/write", method= RequestMethod.GET)
    public String writeArticleGet(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(name="id", defaultValue = "ntc") String id,
                                  @RequestParam(name="page", defaultValue = "1") String page) throws IOException {
        return "write";
    }
    @RequestMapping(value="/write", method= RequestMethod.POST)
    public void writeArticlePost(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(name="idBoard", defaultValue = "ntc") String id,
                                @RequestParam(name="title", defaultValue = "") String title,
                                 @RequestParam(name="content", defaultValue = "") String content,
                                 @RequestParam(name="page", defaultValue = "") String page )
            throws IOException, SQLException, Exception {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        UserVo userVo = session.getAttribute("UserVo") instanceof UserVo ?  (UserVo)session.getAttribute("UserVo") : null;
        if(userVo != null) {
            BoardWriteVo boardWriteVo = new BoardWriteVo(id, title, content, userVo.getEmail());

            BoardWriteResult boardResponseResult = this.board1Service.insertContent(session, boardWriteVo);
            switch(boardResponseResult) {
                case NO_MATCHING_ID:
                    response.sendRedirect(String.format("/board1?id=%s&page=%s&result=no_matching_id", id, page));
                    break;
                case NOT_ALLOWED:
                    response.sendRedirect(String.format("/board1?id=%s&page=%s&result=not_allowed", id, page));
                    break;
                case FAILURE:
                    response.sendRedirect(String.format("/board1?id=%s&page=%s&result=failure", id, page));
                    break;
                case SUCCESS:
                    response.sendRedirect(String.format("/board1?id=%s&page=%s&result=success", id, page));
                    break;
                default:
                    throw new Exception("NotImplementedException");
            }
        } else {
            response.sendRedirect(String.format("/board1?id%s&page=%d&result=failure", id, page));
        }
    }


//****************************************************************    강사님 게시판
    @RequestMapping(value="/board1", method= RequestMethod.GET)
    public String board1Get(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(name="id", defaultValue = "ntc") String id,
                            @RequestParam(name="page", defaultValue = "1") String page,
                            @RequestParam(name="searchmode", defaultValue = "false") String search,
                            @RequestParam(name="what", defaultValue = "") String what,
                            @RequestParam(name="keyword", defaultValue = "") String keyword) throws SQLException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        BoardResponseVo boardResponseVo;
        if(search.equals("false")) {
            Board1Vo board1Vo = new Board1Vo(id, page);
            boardResponseVo = this.board1Service.getArticles(request.getSession(), board1Vo);
            session.setAttribute("BoardResponseVo", boardResponseVo);
        } else {
            String link = "/search?page="+page+"&searchmode="+search+"&id="+id+"&what="+what+"&keyword="+keyword;
            response.sendRedirect(link);
        }

        return "board1";
    }

    //****************************************************************    게시글 검색
    @RequestMapping(value="/search", method= RequestMethod.GET)
    public String searchGet(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(name="page", defaultValue = "1") String page,
                            @RequestParam(name="searchmode", defaultValue = "true") String search,
                             @RequestParam(name="id", defaultValue = "ntc") String id,
                             @RequestParam(name="what", defaultValue = "") String what,
                             @RequestParam(name="keyword", defaultValue = "") String keyword) throws IOException, SQLException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        BoardResponseVo boardResponseVo;

        if(search.equals("true")) {
            SearchVo searchVo = new SearchVo(id, Integer.parseInt(page), what, keyword);
            boardResponseVo = this.board1Service.search(session, searchVo);
            session.setAttribute("BoardResponseVo", boardResponseVo);
        } else {
            String link = "/board1?page="+page+"&id="+id;
            response.sendRedirect(link);
        }
        return "board1";
    }
}
