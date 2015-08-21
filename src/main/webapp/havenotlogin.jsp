<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网站没有此页面</title>
<script type="text/javascript">
<%-- 到登陆页，然后再刷新父页面 --%>
<%-- 这样就解决了iframe和非iframe的跳转问题了 --%>
parent.location.reload();
location.href="<%=request.getContextPath()%>/login";
</script>
</head>
<body>
</body>
</html>