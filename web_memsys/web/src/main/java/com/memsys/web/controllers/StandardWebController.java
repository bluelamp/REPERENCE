package com.memsys.web.controllers;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class StandardWebController extends StandardController {
    @Override
    protected void handleException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        // TODO : 웹 전용 오류 페이지 구축
    }
}
