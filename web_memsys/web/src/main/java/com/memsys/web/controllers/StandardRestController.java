package com.memsys.web.controllers;

import com.memsys.web.enums.ApiResponse;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@RestController
public class StandardRestController extends StandardController {
    @Override
    protected void handleException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        // TODO : API 전용 오류 페이지 구축
        try {
            response.setContentType("text/html; charset=UTF-8;");
            PrintWriter out = response.getWriter();
            out.print(ApiResponse.INTERNAL_SERVER_ERROR.name());
            exception.printStackTrace();
        } catch (Exception ignored) {
            // 사용하지 않을 예외 객체의 변수 이름은 'ignored'로 한다.
            // 요청을 처리하기를 포기한다.
        }
    }
}
