<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%> 
<%@include file="common.jsp" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.title{
	font-family: "楷体";
	color:#NaNNaNNaN;
}
.search-form{
	background-color: #fff;
	padding:5px;
}

.search-text{
	height:25px;
	width: 350px;
	border: 0;
	outline: none;
	background-color:#FAEBD7
}

.search-submit{
	background-image:url(image/search-button.jpg);
	width:29px;
	height:29px;
	border:0
}

.search-box{
	top:150px;
		}
</style>
</head>
<body>
<center>
<c:if test="${!empty size }">
<h2 class="title">操作完成，索引创建成功</h2>
<h3 class="title">共导入了${size }篇文章</h3>
</c:if>
<c:if test="${empty size }">
<h3>当前未新增索引</h3>
</c:if>
<a href="queryServlet?method=startSearch"><button><h3>开始执行任务</h3></button></a>
<br><br>
<h3 class="title">人工指定敏感词进行搜索（不会进行任务记录）</h3>
</center>

<center>
<div class="box">
<div class="search-box">
	<form action="queryServlet?method=startSearch" method="post" target="_blank" class="search-form">
		<input type="text" name="word" class="search-text">
		<input type="submit" value="" class="search-submit">
	</form>
</div>
</div>
</center>
</body>
</html>