<%@ page pageEncoding="UTF-8"%>
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
                    type:"post"
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
                remote:"此用户名已存在,请重新输入!"
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
    
    //选择角色
    $("#pickRole").click(function(){
    	treeObj=$.fn.zTree.init($("#roleTree"), setting, treeNode);
    });
    
});
</script>
<!-- 新增页面 -->
<form id="form" method="post" action="${pageContext.request.contextPath}/user/add">
    <table>
        <tr>
            <td>用户名:</td>
            <td>
                <input type="text" name="userName" id="userName" />
            </td>
        </tr>
        <tr>
            <td>真实姓名:</td>
            <td>
                <input type="text" name="realName" id="realName" />
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
                <textarea name="rNames" id="rNames" readonly="readonly"></textarea>
                <input type="hidden" name="rIds" id="rIds" />
                <input type="button" id="pickRole" value="选择" />
                <ul id="roleTree" class="ztree"></ul>
            </td>
        </tr>
        <tr>
            <td>电话号码:</td>
            <td>
                <input type="text" name="mobileNo" id="mobileNo" />
            </td>
        </tr>
        <tr>
            <td>电子邮箱:</td>
            <td>
                <input type="email" name="email" id="email" />
            </td>
        </tr>
    </table>
    <div style="text-align:center;padding:5px">
        <input type="submit" value="确定"/>
        <input type="reset" value="重置"/>
	</div>
</form>