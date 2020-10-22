package com.last.study.services;

import com.last.study.daos.UserDao;
import com.last.study.enums.UserLoginResult;
import com.last.study.enums.UserRegisterResult;
import com.last.study.enums.UserResetResult;
import com.last.study.vos.UserLoginVo;
import com.last.study.vos.UserResetVo;
import com.last.study.vos.UserVo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

@Service
public class UserService {
    private final UserDao userDao;
    private final DataSource dataSource;

    @Autowired
    public UserService(UserDao userDao, DataSource dataSource) {
        this.userDao = userDao;
        this.dataSource = dataSource;
    }

    public UserLoginResult login(UserLoginVo userLoginvo) throws SQLException {
        // TODO : 정규화
        UserLoginResult userLoginResult;
        if (userLoginvo.getEmail().equals("") || userLoginvo.getPassword().equals("")) {
            userLoginResult = UserLoginResult.USER_LOGIN_RESULT_FAILURE;
        } else {
            try(Connection connection = this.dataSource.getConnection()) {
                UserVo userVo = this.userDao.selectUser(connection, userLoginvo);
                if(userVo == null) {
                    userLoginResult = UserLoginResult.USER_LOGIN_RESULT_FAILURE;
                } else {
                    userLoginResult = UserLoginResult.USER_LOGIN_RESULT_SUCCESS;
                    userLoginvo.getSession().setAttribute("UserVo", userVo);
                }
            }
        }
        return userLoginResult;
    }

    public UserRegisterResult register(UserVo uservo) throws SQLException {
        // TODO : 회원가입 정규화
        UserVo CheckUser = null;
        UserRegisterResult userRegisterResult;
//        1. 정규화를 지키지않은 이메일 비밀번호 양식인 경우.
//        2. 이메일이나 별명이 이미 있는 경우.
        try(Connection connection = this.dataSource.getConnection()) {
            CheckUser = this.userDao.selectUser(connection, uservo.getEmail(), uservo.getNickname(), uservo.getContact());

            if(CheckUser != null) {
                if(CheckUser.getEmail().equals(uservo.getEmail())) {
                    return UserRegisterResult.USER_REGISTER_RESULT_FAILURE_EMAIL_DUPLICATED;
                } else if(CheckUser.getNickname().equals(uservo.getNickname())) {
                    return UserRegisterResult.USER_REGISTER_RESULT_FAILURE_NICKNAME_DUPLICATED;
                } else if(CheckUser.getContact().equals(uservo.getContact())) {
                    return UserRegisterResult.USER_REGISTER_RESULT_FAILURE_CONTACT_DUPLICATED;
                } else
                    return UserRegisterResult.USER_REGISTER_RESULT_FAILURE;
            } else {
                if(this.userDao.register(connection, uservo)) {
                    return UserRegisterResult.USER_REGISTER_RESULT_SUCCESS;
                } else {
                    return UserRegisterResult.USER_REGISTER_RESULT_FAILURE;
                }
            }
        }
    }


    public UserResetResult reset(HttpSession session, UserResetVo userResetVo)  throws SQLException{
        UserResetResult userResetResult;
        try(Connection connection = this.dataSource.getConnection()) {
            UserVo userVo = this.userDao.selectUser(connection, userResetVo);
            if(userVo == null) {
                //전달된 이메일, 연락처와 일치하는 회원이 없다.
                userResetResult = UserResetResult.NO_MATCHING_USER;
            } else {
                //있다.
                Random random = new Random();
                String code = String.format("%06d", random.nextInt(999999));
                this.userDao.insertResetCode(connection, userVo.getIndex(), code);
                session.setAttribute("userResetIndex", userVo.getIndex());
                userResetResult = UserResetResult.CODE_SENT;
            }
        }
        return userResetResult;
    }

    public UserResetResult reset(HttpSession session, String code) throws SQLException, NumberFormatException {
        UserResetResult userResetResult;
        try(Connection connection = this.dataSource.getConnection()) {
            Object userIndexObject = session.getAttribute("userResetIndex");
            int userIndex = Integer.parseInt(String.valueOf(userIndexObject));
            int count = this.userDao.selectResetCodeCount(connection, userIndex, code);
            if( count == 1) {
                userResetResult = UserResetResult.CODE_GOOD;
            } else {
                userResetResult = UserResetResult.CODE_NONO;
            }
        }
        return userResetResult;
    }
    public UserResetResult modifyPassword(HttpSession session, String password) throws SQLException, NumberFormatException {
        UserResetResult userResetResult;
        try(Connection connection = this.dataSource.getConnection()) {
            Object userIndexObject = session.getAttribute("userResetIndex");
            int userIndex = Integer.parseInt(String.valueOf(userIndexObject));
            if(this.userDao.modifyPassword(connection, userIndex, password)) {
                userResetResult = UserResetResult.MODIFY_SUCCESS;
            } else {
                userResetResult = UserResetResult.MODIFY_FAILURE;
            }
        }
        return userResetResult;
    }
}
