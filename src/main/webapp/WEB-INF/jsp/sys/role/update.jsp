<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/additional-methods.min.js"></script>
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
            pIdKey: "parentId",
            rootPId: null
        }
    }, callback: {
    	onCheck: function(event,treeId,treeNode){
    		var nodes = treeObj.getCheckedNodes(true);
    		var pIds="";
    		var pNames="";
    		for (var i = 0; i < nodes.length; i++) {
                pIds+=nodes[i].id+",";
                pNames+=nodes[i].name+",";
            }
    		$("#pIds").val(pIds.substring(0,pIds.length-1));
            $("#pNames").val(pNames.substring(0,pNames.length-1));
        }
    }
};

$(function(){
    $("#form").validate({
        rules:{
        	name:{
                required:true,
                remote:{
                    url:"${pageContext.request.contextPath}/role/query/check",
                    type:"post",
                    data:{id:$("#id").val()}
                }
            },
            description:{
            	maxlength:100
            }
        }, messages: {
            name:{
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
    $("#pickPrivilege").click(function(){
    	treeObj=$.fn.zTree.init($("#privilegeTree"), setting, treeNode);
    });
    
});
</script>
<!-- 新增页面 -->
<sp:form id="form" method="post" commandName="role" action="${pageContext.request.contextPath}/role/update">
    <table>
        <tr>
            <td>角色名:</td>
            <td>
                <sp:input type="hidden" path="id" id="id" />
                <sp:input type="text" path="name" id="name" />
            </td>
        </tr>
        <tr>
            <td>分配权限：</td>
            <td>
                <textarea id="pNames" readonly="readonly">${pNames}</textarea>
                <input type="hidden" name="pIds" id="pIds" value="${pIds}" />
                <input type="button" id="pickPrivilege" value="选择" />
                <ul id="privilegeTree" class="ztree"></ul>
            </td>
        </tr>
        <tr>
           <td>角色描述：</td>
           <td>
               <sp:textarea path="description" id="description" cols="50" rows="5"></sp:textarea>
           </td>
        </tr>
    </table>
    <div style="text-align:center;padding:5px">
        <input type="submit" value="确定"/>
	</div>
</sp:form>