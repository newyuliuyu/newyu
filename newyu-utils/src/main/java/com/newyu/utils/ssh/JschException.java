/**
 * Project Name:easydata
 * File Name:EasyException.java
 * Package Name:com.ez.commons.exception
 * Date:2017年3月2日下午4:01:34
 * Copyright (c) 2017, easytnt All Rights Reserved.
 */
package com.newyu.utils.ssh;

/**
 * ClassName: EasyException <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 2017年3月2日 下午4:01:34 <br/>
 *
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public class JschException extends RuntimeException {
    private String code;
    private String message;

    public JschException() {
        this("", null);
    }

    public JschException(String message) {
        this(message, null);
    }

    public JschException(Throwable cause) {
        this("", cause);
    }

    public JschException(String message, Throwable cause) {
        super(message, cause);
        init(message);
    }

    public JschException format(String format, Object... values) {
        String message = String.format(format, values);
        init(message);
        return this;
    }

    private void init(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
