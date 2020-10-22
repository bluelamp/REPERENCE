package com.last.study.daos;

import com.last.study.interfaces.IBoardIdImpl;
import com.last.study.vos.*;
import com.mysql.cj.protocol.Resultset;
import org.springframework.stereotype.Repository;

import javax.xml.transform.Result;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Repository
public class Board1Dao {
    public BoardLevelVo selectBoardLevel(Connection connection, IBoardIdImpl boardIdimpl) throws SQLException {
        System.out.println("여기까지 들어옴 --- 3-2-1 id = " + boardIdimpl.getId());
        BoardLevelVo boardLevelVo = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT `board_read_level` AS `boardReadLevel`, `board_write_level` AS `boardWriteLevel`\n" +
                "FROM `laststudy`.`boards1`\n" +
                "WHERE `board_id` =?")) {
            preparedStatement.setString(1, boardIdimpl.getId());
            preparedStatement.executeQuery();
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while (resultSet.next()) {
                    boardLevelVo = new BoardLevelVo(
                            resultSet.getInt("boardReadLevel"),
                            resultSet.getInt("boardWriteLevel")
                    );
                }
            }

        }
        return boardLevelVo;
    }

    public int selectBoardCount(Connection connection, Board1Vo board1Vo) throws SQLException {
        int count;
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT COUNT(`board_index`) AS `count` FROM `laststudy`.`boards1` WHERE `board_id` = ?")) {
            preparedStatement.setString(1, board1Vo.getId());
            preparedStatement.executeQuery();

            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                resultSet.next();
                count = resultSet.getInt("count");
            }
        }
        return count;
    }

    public ArrayList<ArticleVo> selectArticles(Connection connection, Board1Vo board1Vo) throws SQLException {
        ArrayList<ArticleVo> Articles = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT \n" +
                "`articles`.`article_index` AS `articleIndex`,\n" +
                "`users`.`user_nickname` AS `userNickname`,\n" +
                "`boards`.`board_name` AS `boardName`,\n" +
                "`articles`.`article_title` AS `articleTitle`,\n" +
                "`articles`.`article_content` AS `articleContent`,\n" +
                "`articles`.`article_written_at` AS `articleWrittenAt`,\n" +
                "`articles`.`article_hit` AS `articleHit`\n" +
                "FROM `laststudy`.`articles` AS `articles`\n" +
                "INNER JOIN `laststudy`.`boards1` AS `boards` ON `articles`.`board_id` = `boards`.`board_id`\n" +
                "INNER JOIn `laststudy`.`users` AS `users` ON `articles`.`user_email` = `users`.`user_email`\n" +
                "WHERE `boards`.`board_id` = ?\n" +
                "ORDER BY `article_index` DESC LIMIT ?, 10")) {
            preparedStatement.setString(1, board1Vo.getId());
            preparedStatement.setInt(2, (board1Vo.getPage() - 1) * 10);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("articleWrittenAt"));
                    ArticleVo articleVo = new ArticleVo(
                            resultSet.getInt("articleIndex"),
                            resultSet.getString("articleTitle"),
                            resultSet.getString("articleContent"),
                            resultSet.getString("userNickname"),
                            date,
                            resultSet.getInt("articleHit")
                    );
                    Articles.add(articleVo);
                }
            }
        }
        return Articles;
    }

    public int countArticles(Connection connection, Board1Vo board1Vo) throws SQLException {
        int count;
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT COUNT(`articles`.`article_index`) AS `count` FROM `laststudy`.`articles` WHERE `board_id`= ?")) {
            preparedStatement.setString(1, board1Vo.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt("count");
            }
        }
        return count;
    }

    public ArrayList<ArticleVo> selectArticles(Connection connection, SearchVo searchVo) throws SQLException {
        ArrayList<ArticleVo> Articles = new ArrayList<>();
        String query = "" +
                "SELECT \n" +
                "`articles`.`article_index` AS `articleIndex`,\n" +
                "`users`.`user_nickname` AS `userNickname`,\n" +
                "`boards`.`board_name` AS `boardName`,\n" +
                "`articles`.`article_title` AS `articleTitle`,\n" +
                "`articles`.`article_content` AS `articleContent`,\n" +
                "`articles`.`article_written_at` AS `articleWrittenAt`,\n" +
                "`articles`.`article_hit` AS `articleHit`\n" +
                "FROM `laststudy`.`articles` AS `articles`\n" +
                "INNER JOIN `laststudy`.`boards1` AS `boards` ON `articles`.`board_id` = `boards`.`board_id`\n" +
                "INNER JOIn `laststudy`.`users` AS `users` ON `articles`.`user_email` = `users`.`user_email`\n" +
                "WHERE `boards`.`board_id` = ? AND\n";
        if (searchVo.getWhat().equals("title")) {
            query += "TRIM(`articles`.`article_title`) LIKE TRIM('%" + searchVo.getKeyword() + "%')\n";
        } else if (searchVo.getWhat().equals("title_content")) {
            query += "(TRIM(`articles`.`article_title`) LIKE TRIM('%" + searchVo.getKeyword() + "%' OR TRIM(`articles`.`article_content`) LIKE TRIM(%" + searchVo.getKeyword() + "%))\n";
        } else {
            query += "TRIM(`users`.`user_nickname`) = TRIM('" + searchVo.getKeyword() + "')\n";
        }
        query += "ORDER BY `article_index` DESC LIMIT ?, 10";

        System.out.println(query);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, searchVo.getId());
            preparedStatement.setInt(2, (searchVo.getPage() - 1) * 10);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Log
                    System.out.println(resultSet.getInt("articleIndex"));
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("articleWrittenAt"));
                    ArticleVo articleVo = new ArticleVo(
                            resultSet.getInt("articleIndex"),
                            resultSet.getString("articleTitle"),
                            resultSet.getString("articleContent"),
                            resultSet.getString("userNickname"),
                            date,
                            resultSet.getInt("articleHit")
                    );
                    Articles.add(articleVo);
                }
            }
        }
        return Articles;
    }

    public int countArticles(Connection connection, SearchVo searchVo) throws SQLException {
        int count;
        String query = "" +
                "SELECT \n" +
                "COUNT(`articles`.`article_index`) AS `count`\n" +
                "FROM `laststudy`.`articles` AS `articles`\n" +
                "INNER JOIN `laststudy`.`boards1` AS `boards` ON `articles`.`board_id` = `boards`.`board_id`\n" +
                "INNER JOIn `laststudy`.`users` AS `users` ON `articles`.`user_email` = `users`.`user_email`\n" +
                "WHERE `boards`.`board_id` = ? AND ";
        if (searchVo.getWhat().equals("title")) {
            query += "TRIM(`articles`.`article_title`) LIKE TRIM('%" + searchVo.getKeyword() + "%')\n";
        } else if (searchVo.getWhat().equals("title_content")) {
            query += "(TRIM(`articles`.`article_title`) LIKE TRIM('%" + searchVo.getKeyword() + "%' OR TRIM(`articles`.`article_content`) LIKE TRIM(%" + searchVo.getKeyword() + "%))\n";
        } else {
            query += "TRIM(`users`.`user_nickname`) = TRIM('" + searchVo.getKeyword() + "')\n";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, searchVo.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt("count");
            }
        }
        System.out.println("찾은 총 게시물 수 : " + count);
        return count;
    }

    public boolean insertArticle(Connection connection, BoardWriteVo boardWriteVo) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO `laststudy`.`articles` (\n" +
                "`article_title`,\n" +
                "`article_content`,\n" +
                "`user_email`,\n" +
                "`board_id`\n" +
                ") VALUE (\n" +
                "?,\n" +
                "?,\n" +
                "?,\n" +
                "?\n" +
                ")")) {
            preparedStatement.setString(1, boardWriteVo.getTitle());
            preparedStatement.setString(2, boardWriteVo.getContent());
            preparedStatement.setString(3, boardWriteVo.getUserEmail());
            preparedStatement.setString(4, boardWriteVo.getId());
            preparedStatement.execute();
            return true;
        }
    }

    public ArticleVo selectArticle(Connection connection, int Articleindex) throws SQLException {
        ArticleVo articleVo = null;
        this.updateHit(connection, Articleindex);
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT `article`.`article_index` AS `articleIndex`,\n" +
                "`user`.`user_nickname` AS `userNickname`,\n" +
                "`article`.`article_title` AS `articleTitle`,\n" +
                "`article`.`article_content` AS `articleContent`,\n" +
                "`article`.`article_written_at` AS `articleWrittenAt`,\n" +
                "`article`.`article_hit` AS `articleHit`\n" +
                "FROM `laststudy`.`articles` AS `article`\n" +
                "INNER JOIN `laststudy`.`users` AS `user` ON `article`.`user_email` = `user`.`user_email`\n" +
                "WHERE `article_index`= ? LIMIT 1")) {
            preparedStatement.setInt(1, Articleindex);
            try (ResultSet resultset = preparedStatement.executeQuery()) {
                while (resultset.next()) {
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultset.getTimestamp("articleWrittenAt"));
                    articleVo = new ArticleVo(
                            Articleindex,
                            resultset.getString("articleTitle"),
                            resultset.getString("articleContent"),
                            resultset.getString("userNickname"),
                            date,
                            resultset.getInt("articleHit")

                    );
                }
            }
        }
        return articleVo;
    }

    public boolean updateHit(Connection connection, int Articleindex) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE `laststudy`.`articles` SET `article_hit` = `article_hit` + 1 WHERE `article_index`= ?")) {
            preparedStatement.setInt(1, Articleindex);
            preparedStatement.execute();
            return true;
        }
    }

    public boolean insertComment(Connection connection, CommentVo commentVo, String userEmail) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO `laststudy`.`comments` (\n" +
                "`article_index`,\n" +
                "`user_email`,\n" +
                "`comment_text`\n" +
                ") VALUES (\n" +
                "?,\n" +
                "?,\n" +
                "?\n" +
                ")")) {
            preparedStatement.setString(1, commentVo.getArticleId());
            preparedStatement.setString(2, userEmail);
            preparedStatement.setString(3, commentVo.getText());
            preparedStatement.execute();
            return true;
        } catch (SQLException ignored) {
            return false;
        }
    }


    public ArrayList<CommentVo> selectComments(Connection connection, String articleId) throws SQLException {
        ArrayList<CommentVo> commentVoArrayList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT `user`.`user_nickname` AS `userNickname`,\n" +
                "`comment`.`comment_text` AS `commentText`,\n" +
                "`comment`.`comment_written_at` AS `commentWrittenAt`\n" +
                "FROM `laststudy`.`comments` AS `comment`\n" +
                "INNER JOIN `laststudy`.`users` AS `user` ON `user`.`user_email` = `comment`.`user_email`\n" +
                "WHERE `comment`.`article_index` = ?")) {
            preparedStatement.setString(1, articleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CommentVo commentVo = new CommentVo(
                            articleId,
                            resultSet.getString("commentText"),
                            resultSet.getString("commentWrittenAt"),
                            resultSet.getString("userNickname"));
                    commentVoArrayList.add(commentVo);
                }
            }
            return commentVoArrayList;
        }
    }
}
