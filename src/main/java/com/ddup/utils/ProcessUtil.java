package com.ddup.utils;

import net.sf.json.JSONObject;

/**
 * @Description: TODO
 * @author dznzyx
 * @date 2015年8月19日 下午5:33:05
 */
public class ProcessUtil {
    
    /**
     * @Title: returnCorrect 
     * @Description: 正确返回
     * @param json
     * @return
     * @throws
     */
    public static JSONObject returnCorrect(JSONObject json){
        json.put("state", 200);
        return json;
    }
    
    /**
     * @Title: returnError 
     * @Description: 错误返回
     * @param errorMsg
     * @return
     * @throws
     */
    public static JSONObject returnError(String errorMsg){
        JSONObject json=new JSONObject();
        json.put("state", 500);
        json.put("errorMsg", errorMsg);
        return json;
    }
    
    /**
     * @Title: returnError 
     * @Description: 错误返回
     * @param errorMsg
     * @return
     * @throws
     */
    public static JSONObject returnError(int errorCode,String errorMsg){
        JSONObject json=new JSONObject();
        json.put("state", errorCode);
        json.put("errorMsg", errorMsg);
        return json;
    }
    
    /**
     * @Title: returnError 
     * @Description: 错误返回
     * @param errorMsg
     * @return
     * @throws
     */
    public static JSONObject returnError(JSONObject json,String errorMsg){
        json.put("state", 500);
        json.put("errorMsg", errorMsg);
        return json;
    }
    
    /**
     * @Title: formatErrMsg 
     * @Description: TODO
     * @param errorMsg 
     * @return
     * @throws
     */
    public static String formatErrMsg(String errorMsg){
        return errorMsg+"时系统出现位置异常！";
    }
}
