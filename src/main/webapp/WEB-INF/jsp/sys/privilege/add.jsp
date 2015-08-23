<%@ page pageEncoding="UTF-8"%>
<%-- <%@include file="/include.script.jsp" %> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/additional-methods.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validate/messages_zh.js"></script>
<script type="text/javascript">
$(function(){
    $("#form").validate({
        rules:{
        	name:{
                required:true,
                remote:{
                    url:"${pageContext.request.contextPath}/privilege/check",
                    type:"post"
                }
            },
            parentId:{
                required:true
            },
            actionUrl:{
            	url:true
            },
            icon:{
            	url:true
            },
            description:{
            	maxlength:100
            }
        }, messages: {
            name:{
                remote:"此权限名已存在,请重新输入!"
            }
        } ,submitHandler:function(form){
        	
        }
    });
});
</script>
<!-- 新增页面 -->
<form id="form" class="easyui-form" method="post" action="${pageContext.request.contextPath}/privilege/add">
    <table>
        <tr>
            <td>权限名:</td>
            <td>
                <input type="text" name="name" id="name" />
            </td>
        </tr>
        <tr>
            <td>父权限：</td>
            <td>
                <input type="text" name="parentId" id="parentId" readonly="readonly" />
                <input type="button" id="pickParent" value="选择" />
            </td>
        </tr>
        <tr>
            <td>权限URL:</td>
            <td>
               <input type="text" name="actionUrl" id="actionUrl" />
            </td>
        </tr>
        <tr>
           <td>是否是菜单：</td>
           <td>
               <select name="isMenu" id="isMenu">
                   <option value="0">否</option>
                   <option value="1">是</option>
               </select>
           </td>
        </tr>
        <tr>
            <td>图标URL:</td>
            <td>
               <input type="text" name="icon" id="icon" />
            </td>
        </tr>
        <tr>
           <td>权限描述：</td>
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