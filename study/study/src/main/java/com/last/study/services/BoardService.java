package com.last.study.services;

import com.last.study.daos.BoardDao;
import com.last.study.vos.BoardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class BoardService {
    private final BoardDao boardDao;
    private final DataSource dataSource;

    @Autowired
    public BoardService(BoardDao boardDao, DataSource dataSource) {
        this.boardDao = boardDao;
        this.dataSource = dataSource;
    }

    public ArrayList<BoardVo> viewBoardinPage(int page) throws SQLException {
        try(Connection connection = dataSource.getConnection()) {
            return this.boardDao.selectBoardinPage(connection, page);
        }
    }
}
