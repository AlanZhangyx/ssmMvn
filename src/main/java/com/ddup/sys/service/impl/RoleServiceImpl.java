package com.ddup.sys.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddup.sys.dao.RoleMapper;
import com.ddup.sys.model.Privilege;
import com.ddup.sys.model.Role;
import com.ddup.sys.service.PrivilegeService;
import com.ddup.sys.service.RoleService;
import com.ddup.utils.ProcessUtil;

/**
 * @Description: TODO
 * @author zyx
 * @date 2015年8月26日 下午3:34:16
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PrivilegeService privilegeService;
    
    /*** 定义需要动态查询的列的key和value ****/
    /** key **/
    //列名原始：是放在第一个select部分的
    private static final String COLUMNS_KEY1="columns_select";
    
    
    /** value **/
    private static final String COLUMNS1="id,name";
    //private static final String COLUMNS2="id,name,description,create_time as createTime,update_time as updateTime";
    
    /*********************** PROCESS ***********************/
    
    /**
     * 增加
     */
    @Transactional
    @Override
    public int insert(Role record, Integer... pIds) {
        int effectCount=roleMapper.insertSelective(record);
        if (pIds.length>0) {
            Integer id=record.getId();
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("roleId", id);
            map.put("pIds", pIds);
            roleMapper.insertRolePrivilege(map);
        }
        return effectCount;
    }

    /**
     * 删除
     */
    @Override
    public void deleteByPrimaryKeys(Integer... ids) {
        if (ids.length>0) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("ids", ids);
            roleMapper.deleteByPrimaryKeys(map);
        }
    }

    /**
     * 修改
     */
    @Transactional
    @Override
    public int updateByPrimaryKeySelective(Role record, Integer... pIds) {
        int effectCount=roleMapper.updateByPrimaryKeySelective(record);
        if (pIds.length>0) {
            //先清空原记录
            roleMapper.deleteRolePrivilege(record.getId());
            //再插入现在关系
            Integer id=record.getId();
            
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("roleId", id);
            map.put("pIds", pIds);
            roleMapper.insertRolePrivilege(map);
        }
        return effectCount;
    }

    /**
     * 单个查询
     */
    @Override
    public Role selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    /**
     * 唯一性查询
     */
    @Override
    public boolean checkUnique(Role record) {
        return roleMapper.countSelectedProperty(record)<1;
    }
    
    /**************************各种列表********************/

    /**
     * 专为CRUD列表时服务
     */
    @Override
    public List<Role> listForCRUD(Map<String, Object> map) {
        return roleMapper.listModels(map);
    }
    /**
     * 计数
     */
    @Override
    public Integer listForCRUDCount(Map<String, Object> map) {
        return roleMapper.listModelsCount(map);
    }

    /**
     * 角色列表，给用户管理时ZTREE用，字段少
     */
    @Override
    public List<Map<String, Object>> listForZtree() {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(COLUMNS_KEY1, COLUMNS1);
        return roleMapper.listMaps(map);
    }

    /**
     * 用户的角色列表
     */
    @Override
    public Map<String, Object> listByUserId(Integer userId) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(COLUMNS_KEY1, COLUMNS1);
        List<Map<String,Object>> listAll=roleMapper.listMaps(map);//获取所有
        map.put("userId", userId);
        List<Map<String,Object>> listSelected=roleMapper.listMaps(map);//获取选中的
        
        //需要得到选中role的ids和names
        String rIds="";
        String rNames="";
        
        //将选中的标识到全部中
        for (int i = 0; i < listSelected.size(); i++) {
            Map<String,Object> checked=listSelected.get(i);
            rIds+=checked.get("id")+",";
            rNames+=checked.get("name")+",";
            for (int j = 0; j < listAll.size(); j++) {
                Map<String ,Object> item=listAll.get(j);
                if((Integer)checked.get("id")==(Integer)item.get("id")){
                    item.put("checked", true);
                    listAll.set(j, item);
                    break;
                }
            }
        }
        if (listSelected.size()>0) {
            rIds=rIds.substring(0, rIds.length()-1);
            rNames=rNames.substring(0, rNames.length()-1);
        }
        
        //构造返回结果
        map.clear();
        map.put("listAllWithChkSign", listAll);
        map.put("rIds", rIds);
        map.put("rNames", rNames);
        return map;
    }
    
    /*********************工具方法******************************/
    
    /**
     * @Title: formatRoleList2ArrayList 
     * @Description: 将list(Page<E>)转为他的父类ArrayList
     * @param list
     * @throws
     */
    public static List<Map<String, Object>> formatRoleList2ArrayList(List<Role> list) {
        //返回的结果
        List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
        DateFormat sd= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化时间
        
        Map<String,Object> tempMap=null;
        for (int i = 0; i < list.size(); i++) {
            Role item=list.get(i);//源Model
            tempMap=new HashMap<String,Object>(0);//目标Map
            ProcessUtil.beanToMap(item, tempMap);//转Map
            
            //对可能有的时间进行格式化
            if(tempMap.containsKey("createTime")){
                tempMap.put("createTime",sd.format(tempMap.get("createTime")));
            }
            if(tempMap.containsKey("updateTime")){
                tempMap.put("updateTime",sd.format(tempMap.get("updateTime")));
            }
            
            //对权限进行字符串化处理
            String pIds="";
            String pNames="";
            next:
            if(tempMap.containsKey("privilegeList")){
                @SuppressWarnings("unchecked")
                List<Privilege> pList=(List<Privilege>)tempMap.get("privilegeList");
                if(pList.size()<1){
                    tempMap.remove("privilegeList");
                    break next;
                }
                
                for (int j = 0; j < pList.size(); j++) {
                    pIds+=pList.get(j).getId()+",";
                    pNames+=pList.get(j).getName()+",";
                }
                pIds = pIds.substring(0, pIds.length()-1);
                pNames = pNames.substring(0, pNames.length()-1);
                
                //清除privilegeList列
                tempMap.remove("privilegeList");
            }
            //设置上面生成的字符串描述
            tempMap.put("pIds",pIds);
            tempMap.put("pNames",pNames);
            
            //返回
            resultList.add(tempMap);
        }
        return resultList;
    }

}
