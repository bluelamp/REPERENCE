package com.memsys.web.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller

public class TestController {
    @RequestMapping(value="/test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // JSONObject : {키 : 값, 키 : 값, 키 : 값}
        // JSONObject.getValue("키") = 값
        // JSONArray : {값, 값, 값, 값, 값}
        // JSONArray.get(index) = 값
        /*
        * StartPage: 1
        * EndPage: 4
        * RequestPage : 1
        * Users : [
        * ["user_index", "user_name", "user_nickname",.....]
        * ["user_index", "user_name", "user_nickname",.....]
        * ["user_index", "user_name", "user_nickname",.....]
        * ["user_index", "user_name", "user_nickname",.....]
        * ]
        */



        response.setContentType("text/html; charset=UTF-8;");
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("관리자1");
        jsonArray.put("관리자2");
        jsonArray.put("관리자3");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("startPage", "1");
        jsonObject.put("EndPage", "4");
        jsonObject.put("requestPage", "1");
        jsonObject.put("users", jsonArray);

        response.getWriter().print(jsonObject.toString(4));

    }
}
