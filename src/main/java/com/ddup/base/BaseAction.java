package com.ddup.base;

/**
 * @Description: 父Action，用来设置一些Action的通用信息
 * @author dznzyx
 * @date 2015年8月18日 下午8:03:58
 */
public class BaseAction {
    
    /**
     * 1 通用的jsp前缀
     */
    protected static final String JSP_PREFIX="/WEB-INF/jsp/";
    
    protected String page;//当前第几页
    protected String rows;//总共需要几条记录

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }
}
