package com.last.study.daos;

import com.last.study.vos.BoardVo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class BoardDao {
    private final int ROWS_PER_PAGE = 10;
    public ArrayList<BoardVo> selectBoardinPage(Connection connection, int page) throws SQLException {
        ArrayList<BoardVo> boardVoArray = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT `board_index` AS `boardIndex`, \n" +
                "`kategorie_name` AS `kategorieName`, \n" +
                "`board_title` AS `boardTitle`,\n" +
                "`user_nickname` AS `userNickname`,\n" +
                "`board_made_at` AS `boardMadeAt`,\n" +
                "`board_views` AS `boardViews`\n" +
                "\t FROM `laststudy`.`boards` \n" +
                "     INNER JOIN `laststudy`.`users` ON `boards`.`user_index` = `users`.`user_index` \n" +
                "      INNER JOIN `laststudy`.`board_kategorie` ON `boards`.`kategorie_index` = `board_kategorie`.`kategorie_index` LIMIT ?, ?")) {
            preparedStatement.setInt(1, (page - 1) * ROWS_PER_PAGE);
            preparedStatement.setInt(2, ROWS_PER_PAGE);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    BoardVo boardVo = new BoardVo(
                            resultSet.getInt("boardIndex"),
                            resultSet.getString("kategorieName"),
                            resultSet.getString("boardTitle"),
                            resultSet.getString("userNickname"),
                            resultSet.getDate("boardMadeAt"),
                            resultSet.getInt("boardViews")
                    );
                    boardVoArray.add(boardVo);
                }
            }
        }
        return boardVoArray;
    }
}
