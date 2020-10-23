package com.memsys.web.services;

import com.memsys.web.daos.SecurityDao;
import com.memsys.web.vos.LoginAttemptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class SecurityService {
    private final SecurityDao securityDao;
    private final DataSource dataSource;

    @Autowired
    public SecurityService(SecurityDao securityDao, DataSource dataSource) {
        this.securityDao = securityDao;
        this.dataSource = dataSource;
    }

    public void putLoginAttempt(LoginAttemptVo loginAttemptVo) throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            this.securityDao.insertLoginAttempt(connection, loginAttemptVo);
        }
    }

    public boolean isLoginAttemptLimitExceeded(String ip) throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            return (this.securityDao.selectLoginAttemptCount(connection, ip, 5) > 5);
        }
    }
    public boolean isLoginFailureLimitExceeded(String ip) throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            return (this.securityDao.selectLoginFailureCount(connection, ip, 60) >= 3);
        }
    }

    public boolean isIpBlocked(String ip) throws SQLException{
        try(Connection connection = this.dataSource.getConnection()) {
            return (this.securityDao.selectBlockedIpCount(connection, ip) > 0);
        }
    }
    public void putBlockedIp(String ip) throws  SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            this.securityDao.insertBlockedIp(connection, ip, 1);
        }
    }
}
