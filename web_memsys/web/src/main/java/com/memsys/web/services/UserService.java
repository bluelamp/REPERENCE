package com.memsys.web.services;

import com.memsys.web.daos.UserDao;
import com.memsys.web.enums.ServerResult;
import com.memsys.web.enums.UserLoginResult;
import com.memsys.web.enums.UserModifyResult;
import com.memsys.web.enums.UserRegisterResult;
import com.memsys.web.vos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class UserService {
    private final DataSource dataSource;
    private final UserDao userDao;

    @Autowired
    public UserService(DataSource dataSource, UserDao userDao) {
        this.dataSource = dataSource;
        this.userDao = userDao;
    }

    public UserLoginResult login(LoginVo loginVo, HttpSession session) throws SQLException {
        try( Connection connection = this.dataSource.getConnection()) {
            UserVo userVo = userDao.selectUser(connection, loginVo);
            if(userVo == null) {
                return UserLoginResult.LOGIN_FAILURE;
            } else {
                session.setAttribute("UserVo", userVo);
                return UserLoginResult.LOGIN_SUCCESS;
            }
        }
    }
    public GetUserVo getUserList(int pageNumber) throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            return userDao.selectUserListInfo(connection, pageNumber);
        }
    }
    public ServerResult deleteUser(int[] userindex) throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            if(userDao.selectdeleteUser(connection, userindex)) {
                return ServerResult.REQUEST_SUCCESS;
            }else {
                return ServerResult.REQUEST_FAILURE;
            }
        }
    }
    public UserRegisterResult selectUsertoCheckDuplicate(RegisterVo registerVo) throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            return this.userDao.selectUsertoCheckDuplicate(connection, registerVo);
        }
    }
    public UserRegisterResult insertUser(RegisterVo registerVo) throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            return this.userDao.insertUser(connection, registerVo);
        }
    }
    public UserModifyResult modifyUser(ModifyVo modifyVo) throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            return this.userDao.modifyUser(connection, modifyVo);
        }
    }
}
