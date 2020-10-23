package com.memsys.web.daos;

import com.memsys.web.enums.UserLoginResult;
import com.memsys.web.vos.LoginAttemptVo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class SecurityDao {
    public void insertLoginAttempt(Connection connection, LoginAttemptVo loginAttemptVo) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `memsys`.`login_attempts` (`attempt_ip`, `attempt_email`,`attempt_password`, `attempt_result`) VALUES(?,?,?,?) ")) {
            preparedStatement.setString(1, loginAttemptVo.getIp());
            preparedStatement.setString(2, loginAttemptVo.getEmail());
            preparedStatement.setString(3, loginAttemptVo.getPassword());
            preparedStatement.setString(4, loginAttemptVo.getLoginResult());
            preparedStatement.execute();
        }
    }

    public int selectLoginAttemptCount(Connection connection, String ip,  int lookBackSeconds) throws SQLException {
        int count = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(`attempt_index`) AS `count` FROM `memsys`.`login_attempts` WHERE `attempt_ip` = ? AND `attempt_created_at` > DATE_SUB(NOW(), INTERVAL ? SECOND)")) {
            preparedStatement.setString(1, ip);
            preparedStatement.setInt(2, lookBackSeconds);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    count= resultSet.getInt("count");
                }
            }
        }
        return count;
    }
    public int selectLoginFailureCount(Connection connection, String ip, int lookBackSeconds) throws  SQLException {
        int count = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(`attempt_index`) AS `count` FROM `memsys`.`login_attempts` WHERE `attempt_ip`=? AND `attempt_created_at` > DATE_SUB(NOW(), INTERVAL ? SECOND) AND `attempt_result`=?")) {
            preparedStatement.setString(1, ip);
            preparedStatement.setInt(2, lookBackSeconds);
            preparedStatement.setString(3, UserLoginResult.LOGIN_FAILURE.name());
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public int selectBlockedIpCount(Connection connection, String ip) throws  SQLException {
        int count = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(`ip_index`) AS `count` FROM `memsys`.`blocked_ips` WHERE `ip`=? AND `ip_expired_at` > NOW()")) {
            preparedStatement.setString(1, ip);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public void insertBlockedIp(Connection connection, String ip, int minutes) throws  SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `memsys`.`blocked_ips` (`ip`, `ip_expires_at`) VALUES(?, DATE_ADD(NOW(), INTERVAL ? MINUTE))")) {
            preparedStatement.setString(1, ip);
            preparedStatement.setInt(2, minutes);
            preparedStatement.execute();
        }
    }
}
