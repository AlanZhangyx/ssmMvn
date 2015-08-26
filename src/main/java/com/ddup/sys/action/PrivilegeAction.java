package com.ddup.sys.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ddup.base.BaseAction;
import com.ddup.base.ToJSPException;
import com.ddup.sys.model.Privilege;
import com.ddup.sys.service.PrivilegeService;
import com.ddup.utils.ProcessUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 权限相关操作
 * @author ZhangYaxu
 * @date 2015年8月18日
 */
@RequestMapping(value="/privilege",method = { RequestMethod.GET, RequestMethod.POST })
@Controller
public class PrivilegeAction extends BaseAction {
    
    private static final String JSP_PREFIX="/WEB-INF/jsp/sys/privilege";//Privilege前缀
    private static final Logger LOGGER=Logger.getLogger(PrivilegeAction.class);//日志
    
    @Resource
    private PrivilegeService privilegeService;
    
    /**
     * 结果JSON，使用时先new
     */
    private JSONObject resultJson=null;
  
    
    /*********************Business Process***************************/
    /**
     * 1 代表此权限
     * 跳转到list页面
     */
    @RequestMapping("/query")
    public String list() throws ToJSPException{
        try {
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("跳转到权限页面");
            LOGGER.error(errorMsg, e);
            throw new ToJSPException(errorMsg);
        }
        return JSP_PREFIX+"/list";
    }
    
    
    /**
     * 2 列表页面数据填充
     * 返回用于CRUD的json数据
     */
    @RequestMapping(value="/query/jsonlist")
    @ResponseBody
    public JSONObject jsonList(String fuzzyWord,Integer page,Integer rows
            ){
        resultJson=new JSONObject();
        try {
            //查询条件
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("fuzzyWord", fuzzyWord);
            List<Map<String,Object>> list=null;//结果list
            //查询数据库
            if (null==page||null==rows) {//全量查询
                list=privilegeService.listForCRUD(map);//分页list是Page<E>类型
            }else{//分页查询
                PageHelper.startPage(page, rows);
                list=privilegeService.listForCRUD(map);//分页list是Page<E>类型
                PageInfo<Map<String,Object>> p = new PageInfo<Map<String,Object>>(list);//取出分页统计信息statistic
                resultJson.put("total", p.getTotal());
            }
            //结果处理
            ProcessUtil.formatPage2ArrayList(list);//将list(Page<E>)转为他的父类ArrayList(不是Page也无所谓),并格式化时间
            resultJson.put("rows", list);//结果返回
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("查询权限列表");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    
    /**
     * 3.1 跳转到add页面
     */
    @RequestMapping("/query/addUI")
    public ModelAndView addUI(HttpServletRequest request) throws ToJSPException{
        ModelAndView mav=new ModelAndView(JSP_PREFIX+"/add");
        try {
            JSONArray jsonArray=new JSONArray();
            jsonArray.addAll(privilegeService.listForZtree());
            mav.addObject("list",jsonArray);
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("跳转到权限增加页面");
            LOGGER.error(errorMsg, e);
            throw new ToJSPException(errorMsg);
        }
        return mav;
    } 
    
    
    /**
     * 3.2 用于增加一条记录
     */
    @RequestMapping(value="/add")
    @ResponseBody
    public JSONObject add(Privilege record){
        resultJson=new JSONObject();
        try {
            privilegeService.insertSelective(record);
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("增加一个权限");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    /**
     * 4 delete
     * 如果有子权限，会一并删除其子权限cascade
     * 
     * 测试：service方法加了事务，那么它调用mapper应该会exception，那么就会Rollback
     * 先在界面选择3条数据，然后手动从数据库删除其中1条，
     * 然后界面发送删除请求，看看是否回滚
     */
    @RequestMapping(value="/delete")
    @ResponseBody
    public JSONObject delete(Integer... ids){
        resultJson=new JSONObject();
        try {
            if (ids.length>0) {
                privilegeService.deleteByPrimaryKeys(ids);
            }
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("删除权限");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    /**
     * 5.1 跳转到update页面
     */
    @RequestMapping("/query/updateUI")
    public ModelAndView updateUI(Integer id) throws ToJSPException{
        ModelAndView mav=new ModelAndView(JSP_PREFIX+"/update");
        try {
            //权限
            Privilege record=privilegeService.selectByPrimaryKey(id);
            //查询出权限列表
            List<Map<String, Object>> pList=privilegeService.listForZtree();
            //如果有父权限的话，在权限列表中标记中并给其加上选中标记
            Integer pId=record.getParentId();//父权限ID
            if (pId!=0) {
                //check标记
                for (int i = 0; i < pList.size(); i++) {
                    Map<String,Object> item=pList.get(i);
                    if(pId==(Integer)item.get("id")){
                        item.put("checked", true);
                        pList.set(i, item);
                        break;
                    } 
                }
                //设置父权限Name
                mav.addObject("parentName", privilegeService.selectByPrimaryKey(pId).getName());
            }
            //将pList放入放回结果
            JSONArray jsonArray=new JSONArray();
            jsonArray.addAll(pList);
            mav.addObject("pList", jsonArray);
            mav.addObject("privilege", record);//结果返回
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("跳转到权限修改页面");
            LOGGER.error(errorMsg, e);
            throw new ToJSPException(errorMsg);
        }
        return mav;
    } 

    /**
     * 5.2 update
     */
    @RequestMapping(value="/update")
    @ResponseBody
    public JSONObject update(Privilege record){
        resultJson=new JSONObject();
        try {
            privilegeService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            String errorMsg=ProcessUtil.formatErrMsg("修改一个权限");
            LOGGER.error(errorMsg, e);
            return ProcessUtil.returnError(500, errorMsg);
        }
        return ProcessUtil.returnCorrect(resultJson);
    }
    
    /**
     * 6 唯一性检查
     */
    @RequestMapping(value="/query/check")
    @ResponseBody
    public boolean check(@RequestParam String name,Integer id){
        if (null!=id && 0!=id) {//修改的情况
            String dbName=privilegeService.selectByPrimaryKey(id).getName();
            if(name.equals(dbName)){
                return true;
            }
        }
        Privilege record=new Privilege();
        record.setName(name);
        return privilegeService.checkUnique(record);
    }
    
}