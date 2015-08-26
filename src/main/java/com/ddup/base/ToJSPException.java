package com.ddup.base;

/**
 * @Description: 这个错误用来被拦截跳转到JSP页面
 * @author zyx
 * @date 2015年8月20日 下午2:49:47
 */
public class ToJSPException extends Exception {

    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = -6537093243470500194L;
    
    @SuppressWarnings("unused")
    private Exception exception;
    
    @SuppressWarnings("unused")
    private String message;
    
    @SuppressWarnings("unused")
    private int code;
    
    public ToJSPException(Exception exception){
        super(exception);
        this.exception=exception;
    }
    
    public ToJSPException(String message){
        super(message);
        this.message=message;
    }
    
    public ToJSPException(int code){
        this.code=code;
    }
    
    public ToJSPException(int code,String message){
        super(message);
        this.code=code;
    }
    
    public ToJSPException(String message,Throwable cause){
        super(message,cause);
        this.message=message;
    }

}
