package com.memsys.web.daos;

import com.memsys.web.enums.UserModifyResult;
import com.memsys.web.enums.UserRegisterResult;
import com.memsys.web.vos.*;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class UserDao {
    public UserVo selectUser(Connection connection, LoginVo loginVo) throws SQLException {
        UserVo userVo = null;
        String query = "SELECT \t`users`.`user_index` AS `userIndex`,\n" +
                "\t\t`users`.`user_name` AS `userName`,\n" +
                "\t\t`users`.`user_nickname` AS `userNickname`,\n" +
                "        `users`.`user_contact` AS `userContact`,\n" +
                "        `users`.`user_address` AS `userAddress`,\n" +
                "        `users`.`user_birth` AS `userBirth`,\n" +
                "        `users`.`user_admin_flag` AS `userAdminFlag`,\n" +
                "        `users`.`user_status` AS `userStatus`,\n" +
                "\t\t`users`.`user_created_at` AS `userCreatedAt`,\n" +
                "        `users`.`user_signed_at` AS `userSignedAt`,\n" +
                "        `users`.`user_status_changed_at` AS `userStatusChangedAt`,\n" +
                "        `users`.`user_password_modified_at` AS `userPasswordModifiedAt` \n" +
                "FROM `memsys`.`users` AS `users`\n" +
                "WHERE `users`.`user_email` = ? AND `users`.`user_password` = ? LIMIT 1";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, loginVo.getEmail());
            preparedStatement.setString(2, loginVo.getHashedpassword());

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    userVo = new UserVo(
                        resultSet.getInt("userIndex"),
                        loginVo.getEmail(),
                        resultSet.getString("userName"),
                        resultSet.getString("userNickname"),
                        resultSet.getString("userContact"),
                        resultSet.getString("userAddress"),
                        resultSet.getString("userBirth"),
                        resultSet.getBoolean("userAdminFlag"),
                        resultSet.getString("userStatus"),
                        resultSet.getDate("userCreatedAt"),
                        resultSet.getDate("userSignedAt"),
                        resultSet.getDate("userStatusChangedAt"),
                        resultSet.getDate("userPasswordModifiedAt")
                    );
                }
            }
        }

        return userVo;
    }

    private final int ROWS_PER_PAGE = 10;

    public GetUserVo selectUserListInfo(Connection connection, int pageNumber) throws SQLException {
        int startPage = pageNumber < 6 ? 1: pageNumber - 5;
        int endPage = pageNumber < 6 ? 11 : pageNumber + 5;
        int requestPage = pageNumber;
        ArrayList<UserVo> users = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(`user_index`) AS `count` FROM `memsys`.`users`")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    int totalUserCount = resultSet.getInt("count");
                    int maxPageNumber = (int)Math.ceil((double)totalUserCount / ROWS_PER_PAGE);
                    if(endPage > maxPageNumber) {
                        endPage = maxPageNumber;
                    }
                }
            }
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT \t`users`.`user_index` AS `userIndex`,\n" +
                "\t\t`users`.`user_name` AS `userName`,\n" +
                "        `users`.`user_email` AS `userEmail`,\n" +
                "\t\t`users`.`user_nickname` AS `userNickname`,\n" +
                "        `users`.`user_contact` AS `userContact`,\n" +
                "        `users`.`user_address` AS `userAddress`,\n" +
                "        `users`.`user_birth` AS `userBirth`,\n" +
                "        `users`.`user_admin_flag` AS `userAdminFlag`,\n" +
                "        `users`.`user_status` AS `userStatus`,\n" +
                "\t\t`users`.`user_created_at` AS `userCreatedAt`,\n" +
                "        `users`.`user_signed_at` AS `userSignedAt`,\n" +
                "        `users`.`user_status_changed_at` AS `userStatusChangedAt`,\n" +
                "        `users`.`user_password_modified_at` AS `userPasswordModifiedAt` \n" +
                "FROM `memsys`.`users` AS `users`\n" +
                "LIMIT ?, ?")) {
            preparedStatement.setInt(1, (pageNumber-1) * ROWS_PER_PAGE);
            preparedStatement.setInt(2, ROWS_PER_PAGE);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    UserVo userVo = new UserVo(
                        resultSet.getInt("userIndex"),
                        resultSet.getString("userEmail"),
                        resultSet.getString("userName"),
                        resultSet.getString("userNickname"),
                        resultSet.getString("userContact"),
                        resultSet.getString("userAddress"),
                        resultSet.getString("userBirth"),
                        resultSet.getBoolean("userAdminFlag"),
                        resultSet.getString("userStatus"),
                        resultSet.getDate("userCreatedAt"),
                        resultSet.getDate("userSignedAt"),
                        resultSet.getDate("userStatusChangedAt"),
                        resultSet.getDate("userPasswordModifiedAt"));
                    users.add(userVo);
                }
            }
        }
        return new GetUserVo(startPage,endPage, requestPage, users);
    }

    public boolean selectdeleteUser(Connection connection, int[] index) {
        String query = "UPDATE `memsys`.`users` SET `user_status` = 'DEL', `user_status_changed_at` = NOW() WHERE ";
        for(int i =0; i < index.length; i++) {
            query += "`user_index` = "+index[i];
            if(i != index.length-1) {
                query += " OR ";
            }
        }
        System.out.print(query);
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            return true;
        } catch(SQLException ignored) {
            return false;
        }
    }

    public UserRegisterResult selectUsertoCheckDuplicate(Connection connection, RegisterVo registerVo) throws SQLException {
        UserRegisterResult result = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT (\n" +
                "SELECT COUNT(`users`.`user_email`)\n" +
                "FROM `memsys`.`users` AS `users`\n" +
                "WHERE `users`.`user_email` = ?\n" +
                ") AS `Emailcount`,\n" +
                "(\n" +
                "SELECT COUNT(`users`.`user_nickname`)\n" +
                "FROM `memsys`.`users` AS `users`\n" +
                "WHERE `users`.`user_nickname` = ?\n" +
                ") AS `Nicknamecount` ,\n" +
                "(\n" +
                "SELECT COUNT(`users`.`user_contact`)\n" +
                "FROM `memsys`.`users` AS `users`\n" +
                "WHERE `users`.`user_contact` = ?\n" +
                ") AS `Contactcount`")) {
            preparedStatement.setString(1, registerVo.getEmail());
            preparedStatement.setString(2, registerVo.getNickname());
            preparedStatement.setString(3, registerVo.getContact());
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    if(resultSet.getInt("Emailcount") == 0 &&
                        resultSet.getInt("Nicknamecount") == 0 &&
                        resultSet.getInt("Contactcount") == 0) {
                        // 중복되는 사항이 없음.
                        result = UserRegisterResult.USER_REGISTER_SUCCESS;
                    } else {
                        if (resultSet.getInt("Emailcount") > 0) {
                            // 이메일 중복
                            result = UserRegisterResult.USER_EMAIL_DUPLICATE;
                        } else if (resultSet.getInt("Nicknamecount") > 0) {
                            // 별명 중복
                            result = UserRegisterResult.USER_NICKNAME_DUPLICATE;
                        } else if (resultSet.getInt("Contactcount") > 0) {
                            // 연락처 중복
                            result = UserRegisterResult.USER_CONTACT_DUPLICATE;
                        } else {
                            //예외 처리
                        }
                    }
                }
            }
        }
        return result;
    }
    public UserRegisterResult insertUser(Connection connection, RegisterVo registerVo) {
        UserRegisterResult result = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `memsys`.`users` (\n" +
                "\t`user_email` \t\t\t\t,\n" +
                "    `user_password` \t\t\t,\n" +
                "    `user_name` \t\t\t\t,\n" +
                "    `user_nickname` \t\t\t,\n" +
                "    `user_contact` \t\t\t\t,\n" +
                "    `user_address` \t\t\t\t,\n" +
                "    `user_birth` \t\t\t\t,\n" +
                "    `user_admin_flag`\t\t\t,\n" +
                "    `user_status` \t\t\t\t) VALUES (\n" +
                "\t?,\n" +
                "\t?,\n" +
                "    ?,\n" +
                "    ?,\n" +
                "    ?,\n" +
                "    ?,\n" +
                "    ?,\n" +
                "    ?,\n" +
                "    ?\n" +
                ")")) {
            preparedStatement.setString(1, registerVo.getEmail());
            preparedStatement.setString(2, registerVo.getHashedPassword());
            preparedStatement.setString(3, registerVo.getName());
            preparedStatement.setString(4, registerVo.getNickname());
            preparedStatement.setString(5, registerVo.getContact());
            preparedStatement.setString(6, registerVo.getAddress());
            preparedStatement.setString(7, registerVo.getBirth());
            preparedStatement.setBoolean(8, registerVo.isIsadmin());
            preparedStatement.setString(9, registerVo.getStatus());
            preparedStatement.executeUpdate();
            return UserRegisterResult.USER_REGISTER_SUCCESS;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return UserRegisterResult.USER_REGISTER_FAILURE;
        }
    }
    public UserModifyResult modifyUser(Connection connection, ModifyVo modifyVo) {
        UserRegisterResult result = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `memsys`.`users` \n" +
                "SET `user_email` = ?,\n" +
                "`user_password` = ?,\n" +
                "`user_name` = ?,\n" +
                "`user_nickname` = ?,\n" +
                "`user_contact` = ?,\n" +
                "`user_address` = ?,\n" +
                "`user_birth` = ?,\n" +
                "`user_admin_flag` = ?,\n" +
                "`user_status` = ?,\n" +
                "`user_status_changed_at` = NOW(),\n" +
                "`user_password_modified_at` = NOW()\n" +
                "WHERE `user_index` = ?")) {

            preparedStatement.setString(1, modifyVo.getEmail());
            preparedStatement.setString(2, modifyVo.getHashedpassword());
            preparedStatement.setString(3, modifyVo.getName());
            preparedStatement.setString(4, modifyVo.getNickname());
            preparedStatement.setString(5, modifyVo.getContact());
            preparedStatement.setString(6, modifyVo.getAddress());
            preparedStatement.setString(7, modifyVo.getBirth());
            preparedStatement.setBoolean(8, modifyVo.isIsadmin());
            preparedStatement.setString(9, modifyVo.getUserstatus());
            preparedStatement.setInt(10, modifyVo.getIndex());
            preparedStatement.executeUpdate();
            return UserModifyResult.USER_MODIFY_SUCCESS;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return UserModifyResult.USER_MODIFY_FAILURE;
        }
    }
}
