<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/additional-methods.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/additional-methods-ddup.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/messages_zh.js"></script>
<script type="text/javascript">
<%-- Ztree --%>
var treeObj=null;
var treeNode=eval('(${list})');
var setting = {
	check:{
		enable:true,
		chkStyle:"checkbox",
		radioType:"level"
	},
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            rootPId: null
        }
    }, callback: {
    	onCheck: function(event,treeId,treeNode){
    		var nodes = treeObj.getCheckedNodes(true);
    		var rIds="";
    		var rNames="";
    		for (var i = 0; i < nodes.length; i++) {
                rIds+=nodes[i].id+",";
                rNames+=nodes[i].name+",";
            }
    		$("#rIds").val(rIds.substring(0,rIds.length-1));
            $("#rNames").val(rNames.substring(0,rNames.length-1));
        }
    }
};

$(function(){
    $("#form").validate({
        rules:{
            userName:{
                required:true,
                remote:{
                    url:"${pageContext.request.contextPath}/user/query/check",
                    type:"post",
                    data:{id:$("#id").val()}
                }
            },
            realName:{
                required:true,
                maxlength:50
            },
            password:{
                required:true,
                maxlength:50,
                equalTo:password2
            },
            password2:{
                required:true,
                maxlength:50,
                equalTo:password
            },
            mobileNo:{
                mobilePhone:true
            },
            email:{
                myEmail:true
            }
        }, messages: {
        	userName:{
                remote:"此角色名已存在,请重新输入!"
            }
        }, submitHandler:function(form){
        	$(form).ajaxSubmit({
                success:function(data){
                	if(data.state==200){
                		$('#mainGrid').datagrid("reload");
                		$.messager.alert("消息提示", "成功！");
                        $('#div_cu').dialog('close');
                	}else{
                		$.messager.alert("消息提示", "失败！"+data.errorMsg);
                	}
                }
            });
        }
    });
    
    //选择父权限
    $("#pickRole").click(function(){
    	treeObj=$.fn.zTree.init($("#roleTree"), setting, treeNode);
    });
    
});
</script>
<!-- 新增页面 -->
<sp:form id="form" method="post" commandName="user" action="${pageContext.request.contextPath}/user/update">
    <table>
        <tr>
            <td>用户名:</td>
            <td>
                <sp:input type="hidden" path="id" id="id" />
                <sp:input type="text" path="userName" id="userName" />
            </td>
        </tr>
        <tr>
            <td>真实姓名:</td>
            <td>
                <sp:input type="text" path="realName" id="realName" />
            </td>
        </tr>
        <tr>
            <td>密码:</td>
            <td>
                <input type="password" name="password" id="password" />
            </td>
        </tr>
        <tr>
            <td>重复密码:</td>
            <td>
                <input type="password" id="password2" />
            </td>
        </tr>
        <tr>
            <td>分配角色：</td>
            <td>
                <textarea id="rNames" readonly="readonly">${rNames}</textarea>
                <input type="hidden" name="rIds" id="rIds" value="${rIds}" />
                <input type="button" id="pickRole" value="选择" />
                <ul id="roleTree" class="ztree"></ul>
            </td>
        </tr>
        <tr>
            <td>电话号码:</td>
            <td>
                <sp:input type="text" path="mobileNo" id="mobileNo" />
            </td>
        </tr>
        <tr>
            <td>电子邮箱:</td>
            <td>
                <sp:input type="text" path="email" id="email" />
            </td>
        </tr>
    </table>
    <div style="text-align:center;padding:5px">
        <input type="submit" value="确定"/>
	</div>
</sp:form>