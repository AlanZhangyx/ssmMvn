<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>欢迎</title>
    <%@ include file="/include.script.jsp"%>
    <script type="text/javascript">
        var domainUrl="<%=request.getContextPath()%>";
        <%-- 动态添加tab页 --%>
        function addTab(event, treeId, treeNode){
        	if(treeNode.level!=0){<%-- 非根节点就打开一个tab --%>
        		var title=treeNode.name;
                var url=treeNode.actionUrl;
                if ($('#tabs').tabs('exists', title)){
                    $('#tabs').tabs('select', title);
                } else {
                    var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
                    $('#tabs').tabs('add',{
                        title:title,
                        content:content,
                        closable:true
                    });
                }
        	}
        }
        <%-- Ztree --%>
        var setting = {
       		data: {
       	        simpleData: {
       	            enable: true,
       	            idKey: "id",
       	            pIdKey: "parentId",
       	            rootPId: 0
       	        }
       	    }, callback: {
                onClick: addTab
            }
        };
        
        var treeNode=eval('(${menu})');

        $(document).ready(function(){
            $.fn.zTree.init($("#navigateTree"), setting, treeNode);
        });
    </script>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',border:false" style="height:80px;background:#B3DFDA;padding:10px">天天向上</div>
    <div data-options="region:'west',split:true,title:'导航菜单'" style="width:150px;">
        <ul id="navigateTree" class="ztree"></ul>
    </div>
    <div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">
版权所有
    </div>
    <div data-options="region:'center',title:'Center'">
        <div class="easyui-tabs" id="tabs" data-options="fit:true,border:false,plain:true">
        </div>
    </div>
</body>
</html>