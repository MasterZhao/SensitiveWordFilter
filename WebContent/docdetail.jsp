<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.content{
	border:2px solid #F4A460;
	width:300px;
	height:auto;
}
</style>
</head>
<body>
	<c:if test="${!empty doc }">
		接收到参数
		<div class="content">${doc.content}</div>
		<form action="docServlet?method=addDescription" method="post">
			<c:if test="${!empty doc.description }">已添加备注，可修改<br>
				<input type="text" name="description" value="${doc.description }" id="description">
				<input type="hidden" name="docpath" value="${doc.docPath }">
				<br><br>
			<input type="submit" value="修改" class="submit">
			</c:if>
			<c:if test="${empty doc.description }">未添加备注
			<br>
			<input type="text" name="description" id="description">
				<input type="hidden" name="docpath" value="${doc.docPath }">
			<br><br>
			<input type="submit" value="添加" class="submit">
			</c:if>
			<br>
		</form>
	</c:if>
	<c:if test="${empty doc }">
		未能接收参数
	</c:if>
</body>
</html>