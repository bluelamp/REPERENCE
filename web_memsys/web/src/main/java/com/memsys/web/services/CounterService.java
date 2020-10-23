package com.memsys.web.services;

import com.memsys.web.daos.CounterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class CounterService {
    private DataSource dataSource;
    private CounterDao counterDao;

    @Autowired
    public CounterService(DataSource dataSource, CounterDao counterDao) {
        this.dataSource = dataSource;
        this.counterDao = counterDao;
    }

    public int getTotalMemberCount() throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            return counterDao.getTotalMemberCount(connection);
        }
    }
    public int getTodayRegisterCount() throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            return counterDao.getTodayRegisterCount(connection);
        }
    }
    public int getTodayWithdrawCount() throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            return counterDao.getTodayWithdrawCount(connection);
        }
    }

    public int getThirtyDaysMemberCount() throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            return counterDao.getThirtyDaysMemberCount(connection);
        }
    }

    public int[] getMemberCount() throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            return counterDao.selectMemberCount(connection);
        }
    }
}
