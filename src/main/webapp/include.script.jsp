<%@ page pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/widget/easyUI1.4.3/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/widget/easyUI1.4.3/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/widget/zTree3.5/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commons/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/widget/easyUI1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/widget/easyUI1.4.3/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/widget/zTree3.5/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/widget/zTree3.5/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript">//全局变量baseUrl：系统路径
    var domainUrl="<%=request.getContextPath()%>";
    var obj=window.location;//地址对象
    var baseUrl=obj.protocol+"//"+obj.host+domainUrl;
</script>
