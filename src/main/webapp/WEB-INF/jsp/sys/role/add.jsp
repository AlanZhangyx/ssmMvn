<%@ page pageEncoding="UTF-8"%>
<%-- <%@include file="/include.script.jsp" %> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/additional-methods.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/messages_zh.js"></script>
<script type="text/javascript">
<%-- Ztree --%>
var setting = {
	check:{
		enable:true,
		chkStyle:"radio",
		radioType:"all"
	},
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: null
        }
    }, callback: {
        onClick: function(event,treeId,treeNode){
        	
        	$("#parentId").val(treeNode.id);
        	$("#parentName").val(treeNode.name);
        }
    }
};

var treeNode=eval('(${list})');

$(function(){
    $("#form").validate({
        rules:{
        	name:{
                required:true,
                remote:{
                    url:"${pageContext.request.contextPath}/role/query/check",
                    type:"post"
                }
            },
            parentId:{
                maxlength:50
            },
            actionUrl:{
            	maxlength:500
            },
            icon:{
            	maxlength:500
            },
            description:{
            	maxlength:100
            }
        }, messages: {
            name:{
                remote:"此权限名已存在,请重新输入!"
            }
        }, submitHandler:function(form){
        	$(form).ajaxSubmit({
                success:function(data){
                	if(data.state==200){
                		$('#mainGrid').datagrid("reload");
                		$.messager.alert("消息提示", "成功！");
                        $('#div_cu').dialog('close');
                	}else{
                		$.messager.alert("消息提示", "失败！"+data.state.errorMsg);
                	}
                }
            });
        }
    });
    
    //选择父权限
    $("#pickPrivilege").click(function(){
    	$.fn.zTree.init($("#roleTree"), setting, treeNode);
    });
    
});
</script>
<!-- 新增页面 -->
<form id="form" method="post" action="${pageContext.request.contextPath}/role/add">
    <table>
        <tr>
            <td>角色名:</td>
            <td>
                <input type="text" name="name" id="name" />
            </td>
        </tr>
        <tr>
            <td>分配权限：</td>
            <td>
                <input type="text" id="pNames" readonly="readonly" />
                <input type="hidden" name="pIds" id="pIds" />
                <input type="button" id="pickPrivilege" value="选择" />
                <ul id="roleTree" class="ztree"></ul>
            </td>
        </tr>
        <tr>
           <td>角色描述：</td>
           <td>
               <textarea name="description" id="description" cols="50" rows="5"></textarea>
           </td>
        </tr>
    </table>
    <div style="text-align:center;padding:5px">
        <input type="submit" value="确定"/>
        <input type="reset" value="重置"/>
	</div>
</form>