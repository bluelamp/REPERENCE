package com.memsys.web.controllers;

import com.memsys.web.enums.ApiResponse;
import com.memsys.web.enums.ServerResult;
import com.memsys.web.enums.UserLoginResult;
import com.memsys.web.enums.UserRegisterResult;
import com.memsys.web.services.SecurityService;
import com.memsys.web.services.UserService;
import com.memsys.web.vos.*;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping(value="/admin/apis")
public class AdminApiController extends StandardRestController {
    private final UserService userService;
    private final SecurityService securityService;

    @Autowired
    public AdminApiController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @RequestMapping(value="login")
    public void login(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam(name="email", defaultValue ="") String email,
                      @RequestParam(name="password", defaultValue = "") String password) throws Exception {
        response.setContentType("text/html; charset=UTF-8;");
        PrintWriter out = response.getWriter();
        LoginVo loginVo = new LoginVo(email, password);
        UserLoginResult result = null;
        try {
            if (this.securityService.isIpBlocked(request.getRemoteAddr())) {
                result = UserLoginResult.IP_BLOCKED;
            } else if (this.securityService.isLoginAttemptLimitExceeded(request.getRemoteAddr())) {
                result = UserLoginResult.LIMIT_EXCEEDED;
//            out.print(UserLoginResult.LIMIT_EXCEEDED.name());
            } else {
                if (loginVo.isNormalization()) {
                    result = this.userService.login(loginVo, request.getSession());
                    if (result == UserLoginResult.LOGIN_FAILURE) {
//                    out.print("LOGIN_FAILURE");
                    } else if (result == UserLoginResult.LOGIN_TIMEOUT) {
//                    out.print("LOGIN_TIMEOUT");
                    } else if (result == UserLoginResult.LOGIN_SUCCESS) {
//                    out.print("LOGIN_SUCCESS");
                    } else {
                        System.out.print("서버 못 불러옴.");
                    }

                } else {
                    out.print("NORMALIZATION_FAILURE.");
                }


                LoginAttemptVo loginAttempvo = new LoginAttemptVo(
                        request.getRemoteAddr(),
                        loginVo.getEmail(),
                        loginVo.getPassword(),
                        result.name()
                );

                this.securityService.putLoginAttempt(loginAttempvo);

                if (result == UserLoginResult.IP_BLOCKED.LOGIN_FAILURE && this.securityService.isLoginFailureLimitExceeded(request.getRemoteAddr())) {
                    this.securityService.putBlockedIp(request.getRemoteAddr());
                }
            }
            out.print(result.name());
        } catch (SQLException sqlException) {
            out.print("NOT_REQUEST_FROM_SERVER");
        }
        out.close();
    }

    @RequestMapping(value="logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session = request.getSession();
        Object Obj = session.getAttribute("UserVo");
        UserVo userVo = Obj instanceof UserVo ? (UserVo) Obj : null;

        System.out.print("로그아웃");
        if(userVo != null) {
            session.setAttribute("UserVo", null);
        }
    }

    @RequestMapping(value="get-user")
    public void getUser(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(name="page", defaultValue = "1") String page) throws Exception{
        response.setContentType("text/html; charset=UTF-8;");
        int pageNumber;
        try {
            pageNumber = Integer.parseInt(page);
        } catch (NumberFormatException ignored) {
            pageNumber = 1;
        }


        GetUserVo getUserVo = this.userService.getUserList(pageNumber);
        JSONArray jsonUsers = new JSONArray();
        for(UserVo userVo : getUserVo.getUserVos()) {
            JSONObject jsonUser = new JSONObject();
            jsonUser.put("index", userVo.getIndex());
            jsonUser.put("email", userVo.getEmail());
            jsonUser.put("name", userVo.getName());
            jsonUser.put("nickname", userVo.getNickname());
            jsonUser.put("contact", userVo.getContact());
            jsonUser.put("address", userVo.getAddress());
            jsonUser.put("birth", userVo.getBirth());
            jsonUser.put("isadmin", userVo.isAdmin() ? "true" : "false");
            jsonUser.put("status", userVo.getUserstatus());
            jsonUser.put("createAt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userVo.getUserCreatedAt()));
            jsonUser.put("signedAt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userVo.getUserSingedAt()));
            jsonUser.put("statusChangedAt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userVo.getUserStatusChangedAt()));
            jsonUser.put("passwordModifiedAt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userVo.getUserPasswordModifiedAt()));
            jsonUsers.put(jsonUser);
        }
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("startPage", getUserVo.getStartPage());
        jsonResponse.put("endPage", getUserVo.getEndPage());
        jsonResponse.put("requestPage", getUserVo.getRequestPage());
        jsonResponse.put("users", jsonUsers);

        response.getWriter().print(jsonResponse.toString(4));
    }

    @RequestMapping(value="delete-user")
    public void deleteUser(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(name="index", defaultValue = "") String index) throws Exception {
        response.setContentType("text/html; charset=UTF-8;");
        PrintWriter out = response.getWriter();
        String[] splitIndex = index.split(",");
        int[] intIndex = new int[splitIndex.length];
        int count =0;
        for(String strIndex: splitIndex) {
            try{
                intIndex[count] = Integer.parseInt(strIndex);
            }catch(NumberFormatException ignored) {
                out.print("잘못된 값입니다.");
            }
            count++;
        }

        ServerResult result = this.userService.deleteUser(intIndex);
        out.print(result.name());
    }

    @RequestMapping(value="register")
    public void register(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(name="email", defaultValue = "") String email,
                        @RequestParam(name="password", defaultValue = "") String password,
                        @RequestParam(name="name", defaultValue = "") String name,
                        @RequestParam(name="nickname", defaultValue = "") String nickname,
                        @RequestParam(name="contact", defaultValue = "") String contact,
                        @RequestParam(name="address", defaultValue = "") String address,
                        @RequestParam(name="birth", defaultValue = "") String birth,
                        @RequestParam(name="is_admin", defaultValue = "") String isAdmin,
                         @RequestParam(name="status", defaultValue = "") String status
                        ) throws Exception {
        response.setContentType("text/html; charset=UTF-8;");
        PrintWriter out = response.getWriter();

        String[] splitBirth = birth.split("-");
        String BirthDay = "";
        for(String temp : splitBirth) {
            BirthDay += temp;
        }
        BirthDay = BirthDay.substring(2,8);

        RegisterVo registerVo = new RegisterVo(
                email,
                password,
                name,
                nickname,
                contact,
                address,
                BirthDay,
                (isAdmin.equals("TRUE") ? true: false),
                status);
        UserRegisterResult userRegisterResult = this.userService.selectUsertoCheckDuplicate(registerVo);
        if(userRegisterResult != UserRegisterResult.USER_REGISTER_SUCCESS) {
            // 중복 등의 이유로 실패 했을때,
            out.print(userRegisterResult.name());
        } else {
            // DB 중복 검사 이후, 중복없음. 유저를 추가함.
            out.print(this.userService.insertUser(registerVo).name());
        }
    }
    @RequestMapping(value="modify-user")
    public void modifyUser(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(name="index", defaultValue = "") String index,
                           @RequestParam(name="email", defaultValue = "") String email,
                           @RequestParam(name="password", defaultValue = "") String password,
                           @RequestParam(name="name", defaultValue = "") String name,
                           @RequestParam(name="nickname", defaultValue = "") String nickname,
                           @RequestParam(name="contact", defaultValue = "") String contact,
                           @RequestParam(name="address", defaultValue = "") String address,
                           @RequestParam(name="birth", defaultValue = "") String birth,
                           @RequestParam(name="is_admin", defaultValue = "") String isAdmin,
                           @RequestParam(name="status", defaultValue = "") String status
    ) throws Exception {
        response.setContentType("text/html; charset=UTF-8;");
        PrintWriter out = response.getWriter();
        String[] splitBirth = birth.split("-");
        String BirthDay = "";
        for(String temp : splitBirth) {
            BirthDay += temp;
        }
        BirthDay = BirthDay.substring(2,8);

        ModifyVo modifyVo = new ModifyVo(Integer.parseInt(index), email, password, name, nickname, contact, address, BirthDay, isAdmin.equals("TRUE")?true:false, status);

        out.print(this.userService.modifyUser(modifyVo).name());

    }
}
