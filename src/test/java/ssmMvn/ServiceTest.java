package ssmMvn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ddup.sys.action.PrivilegeAction;
import com.ddup.sys.dao.PrivilegeMapper;
import com.ddup.sys.service.PrivilegeService;

public class ServiceTest {

    ApplicationContext context=null;
    PrivilegeMapper privilegeDao=null;
    PrivilegeService privilegeService=null;
    PrivilegeAction privilegeAction=null;
    
    @Before
    public void before(){
        context=new ClassPathXmlApplicationContext("applicationContext.xml","spring-servlet.xml");
        privilegeService=context.getBean(PrivilegeService.class);
        privilegeAction=context.getBean(PrivilegeAction.class);
        privilegeDao=context.getBean(PrivilegeMapper.class);
    }
    
    @Test
    public void serviceTest(){
        Map<String,Object> map=new HashMap<String,Object>();
        RowBounds r=new RowBounds(1,5);
        map.put("rowBounds", r);
        List<Map<String, Object>> list=privilegeService.listForCRUD(map);
        System.err.println(list);
    }
    
    @Test
    public void actionTest(){
        privilegeAction.listJson(null, 1, 5);
    }
    
    @Test
    public void mapperTest(){
    }
    
}
