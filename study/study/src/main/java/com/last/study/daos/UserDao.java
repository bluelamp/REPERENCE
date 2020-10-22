package com.last.study.daos;

import com.last.study.vos.UserLoginVo;
import com.last.study.vos.UserResetVo;
import com.last.study.vos.UserVo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao {

    public UserVo selectUser(Connection connection, UserLoginVo userLoginVo) throws SQLException {
        UserVo userVo = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT \n" +
                "`user_index` AS `userIndex`,\n" +
                "`user_email` AS `userEmail`,\n" +
                "`user_password` AS `userPassword`,\n" +
                "`user_name` AS `userName`,\n" +
                "`user_nickname` AS `userNickname`,\n" +
                "`user_contact` AS `userContact`,\n" +
                "`user_level` AS `userLevel` \n" +
                " FROM `laststudy`.`users` " +
                "WHERE `user_email` = ? " +
                "AND" +
                " `user_password` = ? " +
                "LIMIT 1")) {
            preparedStatement.setString(1, userLoginVo.getEmail());
            preparedStatement.setString(2, userLoginVo.getPassword());
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    userVo = new UserVo(
                            resultSet.getInt("userIndex"),
                            resultSet.getString("userEmail"),
                            resultSet.getString("userPassword"),
                            resultSet.getString("userName"),
                            resultSet.getString("userNickname"),
                            resultSet.getString("userContact"),
                            resultSet.getInt("userLevel")
                    );

                }
            }
        }
        return userVo;
    }
    public UserVo selectUser(Connection connection, String email, String nickname, String contact) throws SQLException {
        UserVo userVo = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT \n" +
                "`user_index` AS `userIndex`,\n" +
                "`user_email` AS `userEmail`,\n" +
                "`user_password` AS `userPassword`,\n" +
                "`user_name` AS `userName`,\n" +
                "`user_nickname` AS `userNickname`,\n" +
                "`user_contact` AS `userContact`,\n" +
                "`user_level` AS `userLevel`" +
                " FROM `laststudy`.`users` " +
                "WHERE `user_email` = ? " +
                "OR" +
                " `user_nickname` = ? " +
                "OR" +
                " `user_contact` = ? " +
                "LIMIT 1")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, nickname);
            preparedStatement.setString(3, contact);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    userVo = new UserVo(
                            resultSet.getInt("userIndex"),
                            resultSet.getString("userEmail"),
                            resultSet.getString("userPassword"),
                            resultSet.getString("userName"),
                            resultSet.getString("userNickname"),
                            resultSet.getString("userContact"),
                            resultSet.getInt("userLevel")
                    );

                }
            }
        }
        return userVo;
    }

    public UserVo selectUser(Connection connection, UserResetVo userResetVo) throws SQLException {
        UserVo userVo = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT \n" +
                "`user_index` AS `userIndex`,\n" +
                "`user_email` AS `userEmail`,\n" +
                "`user_password` AS `userPassword`,\n" +
                "`user_name` AS `userName`,\n" +
                "`user_nickname` AS `userNickname`,\n" +
                "`user_contact` AS `userContact`,\n" +
                "`user_level` AS `userLevel`" +
                " FROM `laststudy`.`users` " +
                "WHERE `user_email` = ? " +
                "AND" +
                " `user_contact` = ? " +
                "LIMIT 1")) {
            preparedStatement.setString(1, userResetVo.getEmail());
            preparedStatement.setString(2, userResetVo.getContact());
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    userVo = new UserVo(
                            resultSet.getInt("userIndex"),
                            resultSet.getString("userEmail"),
                            resultSet.getString("userPassword"),
                            resultSet.getString("userName"),
                            resultSet.getString("userNickname"),
                            resultSet.getString("userContact"),
                            resultSet.getInt("userLevel")
                    );
                }
            }
        }
        return userVo;
    }

    public boolean register(Connection connection, UserVo userVo) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO `laststudy`.`users` (\n" +
                "`user_email`,\n" +
                "`user_password`,\n" +
                "`user_name`,\n" +
                "`user_nickname`,\n" +
                "`user_contact`\n" +
                ") VALUES (\n" +
                "\t?,\n" +
                "    ?,\n" +
                "    ?,\n" +
                "    ?,\n" +
                "    ?\n" +
                ")" +
                "")) {
            preparedStatement.setString(1, userVo.getEmail());
            preparedStatement.setString(2, userVo.getPassword());
            preparedStatement.setString(3, userVo.getName());
            preparedStatement.setString(4, userVo.getNickname());
            preparedStatement.setString(5, userVo.getContact());
            preparedStatement.executeUpdate();
            return true;
        } catch(Exception ignored) {
            return false;
        }
    }

    public void insertResetCode(Connection connection, int userIndex, String code) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO `laststudy`.`user_reset_codes` (\n" +
                "                `user_index`, `code`, `code_expires_at`) VALUE \n" +
                "                (?, ?, DATE_ADD(NOW(), INTERVAL 3 MINUTE))")) {
            preparedStatement.setInt(1, userIndex);
            preparedStatement.setString(2, code);
            preparedStatement.execute();
        }
    }

    public int selectResetCodeCount(Connection connection, int userIndex, String code) throws SQLException{
        int count;
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT COUNT(`code_index`) AS `count` FROM `laststudy`.`user_reset_codes` WHERE `user_index` = ? AND `code` = ? AND `code_expires_at` > NOW()")) {
            preparedStatement.setInt(1, userIndex);
            preparedStatement.setString(2, code);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt("count");
            }

        }
        return count;
    }

    public boolean modifyPassword(Connection connection, int userIndex, String password) throws SQLException{
        boolean ok = false;
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE `laststudy`.`users` SET `user_password`=? WHERE `user_index` = ?")) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, userIndex);
            preparedStatement.executeUpdate();

            ok = true;
        }
        return ok;
    }
}
