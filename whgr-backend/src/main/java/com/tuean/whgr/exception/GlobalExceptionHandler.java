package com.tuean.whgr.exception;

import com.tuean.whgr.entity.MineResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

import static com.tuean.whgr.Const.NEED_LOGIN_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<? extends MineResp<? extends Object>> handleAllExceptions(Exception ex, WebRequest request) {
        MineResp<? extends Object> error = MineResp.error();
        String msg = ex.getMessage();
        error.setMessage(msg);
        logger.error("", ex);
        return new ResponseEntity<MineResp<? extends Object>>(error, HttpStatus.OK);
    }


    @ExceptionHandler(NeedLoginException.class)
    public final ResponseEntity<MineResp> needLoginException(Exception ex, WebRequest request) {
        logger.error("", ex);
        return new ResponseEntity<>(MineResp.error(NEED_LOGIN_ERROR, null), HttpStatus.OK);
    }

    @ExceptionHandler(SQLException.class)
    public final ResponseEntity<MineResp> sqlException(Exception ex, WebRequest request) {
        logger.error("", ex);
        return new ResponseEntity<>(MineResp.error(), HttpStatus.OK);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<MineResp> sql2Exception(Exception ex, WebRequest request) {
        logger.error("", ex);
        return new ResponseEntity<>(MineResp.error(), HttpStatus.OK);
    }

}
