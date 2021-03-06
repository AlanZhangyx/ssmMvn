package com.ddup.utils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;

/**
 * @Description: TODO
 * @author zyx
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
        return errorMsg+"时系统出现未知异常！";
    }

    /**
     * @Title: formatPage2ArrayList 
     * @Description: 将list(Page<E>)转为他的父类ArrayList
     * @param list
     * @throws
     */
    public static void formatPage2ArrayList(
            List<Map<String, Object>> list) {
        //返回的结果
        List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
        
        DateFormat sd= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化时间
        
        for (int i = 0; i < list.size(); i++) {
            Map<String,Object> item=list.get(i);
            
            //对可能有的时间进行格式化
            if(item.containsKey("createTime")){
                item.put("createTime",sd.format(item.get("createTime")));
            }
            if(item.containsKey("updateTime")){
                item.put("updateTime",sd.format(item.get("updateTime")));
            }
            //返回
            resultList.add(item);
        }
        list = resultList;
    }
    
    //将javabean转为map类型，然后返回一个map类型的值
    public static void beanToMap(Object obj,Map<String,Object> params) { 
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!StringUtils.equals(name, "class")) {
                    Object property=propertyUtilsBean.getNestedProperty(obj, name);
                    if (null!=property) {
                        params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                    }else{
                        params.put(name, "");
                    }
                } 
            } 
        } catch (Exception e) { 
          e.printStackTrace(); 
        } 
    }
    
    /**
     * @Title: responseJson 
     * @Description: 输出传入的JSON信息
     * @param response
     * @param jsonRefuseMsg
     * @throws IOException
     * @throws
     */
    public static void responseJson(HttpServletResponse response,
            String jsonRefuseMsg) throws IOException {
        PrintWriter writer=response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        writer.write(jsonRefuseMsg);
        writer.flush();
        writer.close();
    }

}
