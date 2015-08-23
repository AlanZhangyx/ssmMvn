<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/include.script.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<style type="text/css">
    body{margin:0}
</style>
<script type="text/javascript">
function fillGird(){
    $('#mainGrid').datagrid({
        url:baseUrl+"/privilege/get/jsonlist",
        queryParams: {},
        width:'98%',
        height:'100%',
        title:"",
        fit:true,
        pageSize:20,
        pageList:[10,20],
        striped:true,
        loadMsg:"数据加载中请稍后……",
        fitColumns:true,
        pagination:true,
        autoRowHeight:true,
        rownumbers:true,
        nowrap:false,
        columns:[[
            {field:'id',checkbox:true},
            {field:'name',title:'权限名',width:100},
            {field:'actionUrl',title:'权限url',width:100},
            {field:'parentName',title:'父权限',width:100},
            {field:'isMenu',title:'是否菜单',width:50,
                formatter: function(value,row,index){
                    if(value==1){return "是"}else{return "否"}
                }
            },
            {field:'icon',title:'图标url',width:200},
            {field:'updateTime',title:'更新时间',width:100}
        ]],
        toolbar:[{
            text:'新增',
            iconCls:'icon-add',
            handler:function(){
                $('#div_cu').dialog({
                	title:"新增",
                	height:"50%",
                	width:"50%",
                	close:false,
                	cache:false,
                	href:baseUrl+"/privilege/get/add",
                	modal:true
                });
            }
        },{
            text:'修改',
            iconCls:'icon-edit',
            handler:function(){
                var rows = $("#mainGrid").datagrid("getSelections");
                if(rows.length<1){
                    $.messager.alert("消息提示", "请选择一条记录！","warning");
                    return false;
                }else if(rows.length>1){
                    $.messager.alert("消息提示", "只能选择一条记录！","warning");
                    return false;
                }
                var id= rows[0].id;
                if(id){
                    $.ajax({
                        type : "POST",
                        url : baseUrl+"/privilege/get/update",
                        data:{id:id},
                        success : function(data) {
                            $("#id").val(data.id);
                            $("#type").combobox("setValue",data.type);
                            $("#title").val(data.title);
                            $("#content").val(data.content);
                            $('#dlg_save').dialog('open');
                        }
                    });
                }
            }
        },{
            text:'删除',
            iconCls:'icon-remove',
            handler:function(){
                var rows = $("#mainGrid").datagrid("getSelections");
                if(rows.length<1){
                    $.messager.alert("消息提示", "请选择一条记录！","warning");
                    return false;
                }
                var affirm=$.messager.confirm("消息提示", "您确认删除吗？此操作不可回退！",function(r){
                    if(r){
                        var ids="";
                        for (var int = 0; int < rows.length; int++) {
                            ids+=rows[int].id+",";
                        }
                        //casual find a if sentence to limit ajax request
                        if(ids.length>0){
                            $.ajax({
                                type : "POST",
                                url : baseUrl+"/privilege/delete",
                                data:{ids:ids},
                                success : function(data) {
                                    if(data.state==200){
                                        $.messager.alert("消息提示", "成功！");
                                        $('#mainGrid').datagrid("reload");
                                    }else{
                                        $.messager.alert("消息提示", "抱歉，数据异常请重试！","warning");
                                    }
                                }
                            })
                        }
                    }
                });
            }
        }]
    });
}

/**
 * 保存，新增或修改的
 */
function dlg_saveOrUpdate(){
    var idT=$("#id").val();
    if(""!=idT&&undefined!=idT){//修改
        dlg_update();
    }else{//新增save
        var type=$("#type").combobox("getValue");
        var title=$("#title").val();
        var content=$("#content").val();
        if(type==""){
            $.messager.alert("消息提示", "类型不能为空！","warning");
            return false;
        }else if (title=="") {
            $.messager.alert("消息提示", "标题不能为空！","warning");
            return false;
        }else if (content=="") {
            $.messager.alert("消息提示", "内容不能为空！","warning");
            return false;
        }else{
            $.ajax({
                type : "POST",
                url : baseUrl+"/title_addOne",
                data:{
                    type:type,
                    title:title,
                    content:content
                },
                success : function(data) {
                    if(data.state==200){
                        $.messager.alert("消息提示", "成功！");
                        $('#dlg_save').dialog('close');
                        $('#mainGrid').datagrid("reload");
                    }else{
                        $.messager.alert("消息提示", "抱歉，数据异常请重试！","warning");
                    }
                }
            });
        }
    }
}

/**
 * 更新
 */
function dlg_update(){
    var id=$("#id").val();
    var type=$("#type").combobox("getValue");
    var title=$("#title").val();
    var content=$("#content").val();
    if(type==""){
        $.messager.alert("消息提示", "类型不能为空！","warning");
        return false;
    }else if (title=="") {
        $.messager.alert("消息提示", "标题不能为空！","warning");
        return false;
    }else if (content=="") {
        $.messager.alert("消息提示", "内容不能为空！","warning");
        return false;
    }else{
        $.ajax({
            type : "POST",
            url : baseUrl+"/title_udpateOne",
            data:{
                id:id,
                type:type,
                title:title,
                content:content
            },
            success : function(data) {
                if(data.state==1){
                    $.messager.alert("消息提示", "成功！");
                    $('#dlg_save').dialog('close');
                    $('#mainGrid').datagrid("reload");
                }else{
                    $.messager.alert("消息提示", "抱歉，数据异常请重试！","warning");
                }
            }
        });
    }
}

/**
 * 重置
 */
function clearForm(){
    $('#ff').form('clear');
}


$(function(){
    fillGird();
});
</script>
</head>
<body>
    <div class="easyui-datagrid" id="mainGrid"></div>
    <div id="div_cu"></div>
</body>
</html>