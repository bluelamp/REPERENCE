package com.last.study.services;

import com.last.study.daos.Board1Dao;
import com.last.study.daos.UserDao;
import com.last.study.enums.BoardResponseResult;
import com.last.study.enums.BoardWriteResult;
import com.last.study.enums.CommentResult;
import com.last.study.utility.Variable;
import com.last.study.vos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class Board1Service {
    private final Board1Dao board1Dao;
    private final UserDao userDao;
    private final DataSource dataSource;

    @Autowired
    public Board1Service(Board1Dao board1Dao, UserDao userDao, DataSource dataSource) {
        this.board1Dao = board1Dao;
        this.userDao = userDao;
        this.dataSource = dataSource;
    }

    public BoardResponseVo getArticles(HttpSession session, Board1Vo board1Vo) throws SQLException {
        BoardResponseVo boardResponseVo;
        try(Connection connection = this.dataSource.getConnection()) {
            // 넘어온 id를 가지는 게시판이 있는가?
            // 회원일 경우 level, 아니면 (10)의 레벨로 해당 게시판을 읽을 수 있는가?
            // 게시글 가져오기

            BoardLevelVo boardLevel = this.board1Dao.selectBoardLevel(connection, board1Vo);
            if(boardLevel == null) {
                // 해당 ID를 가지는 게시판이 없을때,
                boardResponseVo = new BoardResponseVo(BoardResponseResult.NO_MATCHING_ID, null);
            } else {
                if (boardLevel.getReadLevel() < this.getUserLevel(session)) {
                    //권한 없음.
                    boardResponseVo = new BoardResponseVo(BoardResponseResult.NOT_AUTHORIZED, null);
                } else {
                    //읽기 가능.
//                    boardResponseVo = new BoardResponseVo(BoardResponseResult.OKAY, this.board1Dao.);
                    boardResponseVo = new BoardResponseVo(BoardResponseResult.OKAY, this.board1Dao.selectArticles(connection, board1Vo), board1Vo.getPage(), this.board1Dao.countArticles(connection, board1Vo));
                }
            }
        }
        return boardResponseVo;
    }

    private int getUserLevel(HttpSession session) {
        Object userVoObject = session.getAttribute("UserVo");
        UserVo userVo = null;
        if(userVoObject instanceof UserVo) {
            userVo = (UserVo) userVoObject;
        }
        int userLevel;
        if(userVo == null) {
            userLevel = 10;
        } else {
            userLevel = userVo.getLevel();
        }
        return userLevel;
    }

    public BoardResponseVo search(HttpSession session, SearchVo searchVo) throws SQLException {
        BoardResponseVo boardResponseVo;
        try(Connection connection = this.dataSource.getConnection()) {
            Board1Vo board1Vo = new Board1Vo(searchVo.getId(), String.valueOf(searchVo.getPage()));
            BoardLevelVo boardLevel = this.board1Dao.selectBoardLevel(connection, board1Vo);
            if(boardLevel == null) {
                // 해당 ID를 가지는 게시판이 없을때,
                boardResponseVo = new BoardResponseVo(BoardResponseResult.NO_MATCHING_ID, null);
            } else {
                if (boardLevel.getReadLevel() < this.getUserLevel(session)) {
                    //권한 없음.
                    boardResponseVo = new BoardResponseVo(BoardResponseResult.NOT_AUTHORIZED, null);
                } else {
                    //읽기 가능.
//                    boardResponseVo = new BoardResponseVo(BoardResponseResult.OKAY, this.board1Dao.);
                    boardResponseVo = new BoardResponseVo(BoardResponseResult.OKAY, this.board1Dao.selectArticles(connection, searchVo), searchVo.getPage(), this.board1Dao.countArticles(connection, searchVo));
                }
            }
        }
        return boardResponseVo;
    }

    public BoardWriteResult insertContent(HttpSession session, BoardWriteVo boardWriteVo) throws SQLException{
        BoardWriteResult boardWriteResult;
        try(Connection connection = this.dataSource.getConnection()) {
            BoardLevelVo boardLevel = this.board1Dao.selectBoardLevel(connection, boardWriteVo);

            if(boardLevel == null) {
                // 해당 ID를 가지는 게시판이 없을때,
                boardWriteResult =BoardWriteResult.NO_MATCHING_ID;
            } else {
                if (boardLevel.getWriteLevel() < this.getUserLevel(session)) {
                    //쓰기 권한 없음.
                    boardWriteResult = BoardWriteResult.NOT_ALLOWED;
                } else {

                    //쓰기 가능.
                    if(this.board1Dao.insertArticle(connection, boardWriteVo))
                        boardWriteResult = BoardWriteResult.SUCCESS;
                    else
                        boardWriteResult = BoardWriteResult.FAILURE;
                }
            }
        }

        return boardWriteResult;
    }
    public ArticleVo selectArticle(HttpSession session, int Articleindex) throws SQLException{
        ArticleVo articleVo;
        try(Connection connection = this.dataSource.getConnection()) {
            articleVo = this.board1Dao.selectArticle(connection, Articleindex);
        }
        return articleVo;
    }

    /******************************              댓글 달기 */
    public CommentResult insertComment(HttpSession session, CommentVo commentVo) throws SQLException{
        CommentResult commentResult;
        UserVo userVo = session.getAttribute("UserVo") instanceof UserVo ? (UserVo) session.getAttribute("UserVo"): null;

        if(userVo instanceof  UserVo) {
            userVo = (UserVo)session.getAttribute("UserVo");
        } else {
            userVo = Variable.getAnonymousUserVo();
        }
        try(Connection connection = this.dataSource.getConnection()) {
            if(this.board1Dao.insertComment(connection, commentVo, userVo.getEmail())) {
                commentResult = CommentResult.INSERT_SUCCESS;
            } else {
                commentResult = CommentResult.INSERT_FAILURE;
            }
        }
        return commentResult;
    }
    
    /******************************              댓글 가져오기 */
    public ArrayList<CommentVo> selectComments(HttpSession session, String ArticleId) throws SQLException{
        ArrayList<CommentVo> CommentVoList;
        try(Connection connection = this.dataSource.getConnection()) {
            CommentVoList = this.board1Dao.selectComments(connection, ArticleId);
        }
        return CommentVoList;
    }

}
