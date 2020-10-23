package com.memsys.web.controllers;


import com.memsys.web.services.CounterService;
import com.memsys.web.vos.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@RestController
@RequestMapping(value="/admin/apis/counter", method = RequestMethod.POST)
public class CounterApiController extends StandardRestController {
    private final CounterService counterService;

    @Autowired
    public CounterApiController(CounterService counterService) {
        this.counterService = counterService;
    }

    @RequestMapping(value="get_total_member_count")
    public void getTotalMemberCount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, Exception {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Object objUserVo = session.getAttribute("UserVo");
        if(objUserVo instanceof UserVo && (((UserVo) objUserVo).isAdmin())) {
            out.print(counterService.getTotalMemberCount());
        } else {
            out.print("NOT_PERMISSION");
            throw new Exception("PERMISSION_DENIED");
        }
    }

    @RequestMapping(value="get_today_register_count")
    public void getTodayRegisterCount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, Exception {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Object objUserVo = session.getAttribute("UserVo");
        if(objUserVo instanceof UserVo && (((UserVo)objUserVo).isAdmin())) {
            out.print(counterService.getTodayRegisterCount());
        } else {
            out.print("NOT_PERMISSION");
            throw new Exception("PERMISSION_DENIED");
        }
    }

    @RequestMapping(value="get_today_withdraw_count")
    public void getTodayWithdrawCount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, Exception {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Object objUserVo = session.getAttribute("UserVo");
        if(objUserVo instanceof UserVo && (((UserVo)objUserVo).isAdmin())) {
            out.print(counterService.getTodayWithdrawCount());
        } else {
            out.print("NOT_PERMISSION");
            throw new Exception("PERMISSION_DENIED");
        }
    }

    @RequestMapping(value="get_total_thirtydays_member_count")
    public void getThirtyDaysMemberCount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, Exception {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Object objUserVo = session.getAttribute("UserVo");
        if(objUserVo instanceof UserVo && (((UserVo)objUserVo).isAdmin())) {
            out.print(counterService.getThirtyDaysMemberCount());
        } else {
            out.print("NOT_PERMISSION");
            throw new Exception("PERMISSION_DENIED");
        }
    }

    @RequestMapping(value="get_member_count")
    public void getMemberCount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, Exception {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        int[] memberCount = {0,0,0,0};
        Object objUserVo = session.getAttribute("UserVo");
        if(objUserVo instanceof UserVo && (((UserVo)objUserVo).isAdmin())) {
            memberCount = counterService.getMemberCount();
            out.print(memberCount[0]+","+memberCount[1]+","+memberCount[2]+","+memberCount[3]);
        } else {
            out.print("NOT_PERMISSION");
            throw new Exception("PERMISSION_DENIED");
        }
    }
}
