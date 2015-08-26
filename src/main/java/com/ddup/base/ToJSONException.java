package com.ddup.base;

/**
 * @Description: 用来被拦截，从而知道要给客户端返回一个JSON类型的错误信息
 * @author zyx
 * @date 2015年8月20日 下午2:51:09
 */
public class ToJSONException extends Exception {

    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = 1875446794980617135L;
    
    @SuppressWarnings("unused")
    private Exception exception;
    
    @SuppressWarnings("unused")
    private String message;
    
    @SuppressWarnings("unused")
    private int code;
    
    public ToJSONException(Exception exception){
        super(exception);
        this.exception=exception;
    }
    
    public ToJSONException(String message){
        super(message);
        this.message=message;
    }
    
    public ToJSONException(int code){
        this.code=code;
    }
    
    public ToJSONException(int code,String message){
        super(message);
        this.code=code;
    }
    
    public ToJSONException(String message,Throwable cause){
        super(message,cause);
        this.message=message;
    }

}
