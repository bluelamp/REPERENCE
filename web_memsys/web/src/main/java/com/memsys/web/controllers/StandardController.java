package com.memsys.web.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class StandardController {
    @ExceptionHandler
    protected abstract void handleException(HttpServletRequest request, HttpServletResponse response, Exception exception);
}
