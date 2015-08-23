<%@ page pageEncoding="UTF-8"%>
<!-- 新增页面 -->
<form id="form" class="easyui-form" method="post">
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
</form>
<div style="text-align:center;padding:5px">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="dlg_saveOrUpdate()">提交</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
</div>