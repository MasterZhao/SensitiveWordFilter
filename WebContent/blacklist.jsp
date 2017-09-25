<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
 <%@include file="common.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#info{
		background-color:#CCCCCC;
		color:#8B2252
	}
	body{
	background-color:#E8E8E8;
	}
	#title{
	font-size: 60px;
	color:#C5C1AA;
	text-shadow:3px 2px 3px #969696;
	}
	.mytable{
	width:90%;
	font-size: 20px
	}
</style>
</head>
<body>
<center>
<span id="title">黑名单</span>
<c:if test="${!empty blacklist }">
<table class="table table-bordered table-hover mytable">
	<tr id="info">
	<td>文件名</td>
	<td>本地路径</td>
	<td>备注</td>
	<td>操作</td>
	</tr>
	<c:forEach items="${blacklist }" var="docItem">
	<tr>
	<td style="display:none" name="${docItem.id }">${docItem.id }</td>
	<td>${docItem.title }</td>
	<td>${docItem.docPath }</td>
	<c:if test="${!empty docItem.description }">
		<td>${docItem.description }</td>
	</c:if>
	<c:if test="${empty docItem.description }">
		<td>无</td>
	</c:if>
	<td><form action="docServlet?method=removeFromBlackList" method="post">
		<input type="hidden" name="path" value="${docItem.docPath }">
		<input type="submit" value="还原">
	</form></td>
	</tr>
</c:forEach>
</table>
</c:if>

<c:if test="${empty blacklist }">
<h2>当前黑名单为空</h2>
</c:if>
<br><br>

<a href="index.jsp">返回</a>
</center>
</body>
</html>