package com.memsys.web.daos;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CounterDao {
    public int getTotalMemberCount(Connection connection) throws SQLException {
        int count = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(`user_index`) AS `count` FROM `memsys`.`users`")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public int getTodayRegisterCount(Connection connection) throws SQLException  {
        int count = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(`user_index`) AS `count` FROM `memsys`.`users` WHERE `user_status`= 'OKY' AND DATE(`user_created_at`) = CURDATE()")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public int getTodayWithdrawCount(Connection connection) throws SQLException  {
        int count = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(`user_index`) AS `count` FROM `memsys`.`users` WHERE `user_status`= 'DEL' AND DATE(`user_status_changed_at`) = CURDATE()")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public int getThirtyDaysMemberCount(Connection connection) throws SQLException  {
        int OkyCount = 0;
        int DelCount = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT `user_status` AS `status`, COUNT(`user_index`) AS `count` FROM `memsys`.`users` WHERE DATE(`user_created_at`)  > DATE_SUB(CURDATE(), INTERVAL 30 DAY) GROUP BY `user_status`")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    switch (resultSet.getString("status")) {
                        case "ADM":
                            break;
                        case "OKY":
                            OkyCount = resultSet.getInt("count");
                            break;
                        case "DEL":
                            DelCount = resultSet.getInt("count");
                            break;
                        case "SUS":
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return (OkyCount-DelCount);
    }



    public int[] selectMemberCount(Connection connection) throws SQLException{
        int[] countArray = new int[4];
        int index = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT \n" +
                "(SELECT COUNT(`user_index`) AS `count` FROM `memsys`.`users`) AS `totalCount`,\n" +
                "(SELECT COUNT(`user_index`) AS `count` FROM `memsys`.`users` WHERE `user_status`= 'OKY' AND DATE(`user_created_at`) = CURDATE()) AS `registerCount`,\n" +
                "(SELECT COUNT(`user_index`) AS `count` FROM `memsys`.`users` WHERE `user_status`= 'DEL' AND DATE(`user_status_changed_at`) = CURDATE()) AS `withdrawCount`,\n" +
                "(SELECT (SELECT COUNT(`user_index`) FROM `memsys`.`users` WHERE DATE(`user_created_at`)  > DATE_SUB(CURDATE(), INTERVAL 30 DAY)) -\n" +
                "(SELECT COUNT(`user_index`) FROM `memsys`.`users` WHERE DATE(`user_status_changed_at`)  > DATE_SUB(CURDATE(), INTERVAL 30 DAY) AND `user_status`='DEL')) AS `thirtyDaysCount`")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    countArray[0] = resultSet.getInt("TotalCount");
                    countArray[1] = resultSet.getInt("registerCount");
                    countArray[2] = resultSet.getInt("withdrawCount");
                    countArray[3] = resultSet.getInt("thirtyDaysCount");
                }
            }
        }
        return countArray;
    }


}
