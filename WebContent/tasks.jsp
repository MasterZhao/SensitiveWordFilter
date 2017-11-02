<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
 <%@include file="common.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务记录</title>
<style type="text/css">
	#info{
		background-color:#CCCCCC;
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
	width:60%;
	font-size: 20px
	}
	.input-group{
	width:12%;
	}
</style>
<script type="text/javascript">
$(function(){
	$("#pageNo").change(function(){
		var val=$(this).val();
		val=$.trim(val);
		
		//1.校验val是否为数字
		var reg=/^\d+$/g;
		if(!reg.test(val)){
			alert("输入的不是合法的页码");
			$(this).val("");
			return;
		}
		//校验val是否在1到totalpagenumber的范围之内
		var pageNo=parseInt(val);
		if(val<1||val>parseInt("${taskpage.getTotalPageNumber()}")){
			alert("输入的页码超出范围");
			$(this).val("");
			return;
		}
		//实现跳转
		var href="queryServlet?method=getAllTaskPage&pageNo="+pageNo;
		window.location.href=href;
	})
})

</script>
</head>
<body>
<center>
<span id="title">任务记录</span>
<c:if test="${!empty taskpage }">
<table class="table table-bordered table-hover mytable">
	<tr id="info">
	<td>&nbsp;&nbsp;任务号</td>
	<td>&nbsp;&nbsp;任务执行时间</td>
	<td>&nbsp;&nbsp;</td>
	</tr>
	<c:forEach items="${taskpage.list }" var="taskItem">
	<tr>
	<td>&nbsp;&nbsp;${taskItem.id }</td>
	<td>&nbsp;&nbsp;${taskItem.exeTime }</td>
	<td>
	&nbsp;&nbsp;<a href="queryServlet?method=getTaskDetailsById&pageNo=1&taskId=${taskItem.id }" target="_blank">查看任务详情</a>
	</td>
	</tr>
</c:forEach>
</table>

<ul class="pagination">
	<li><a href="queryServlet?method=getAllTaskPage&pageNo=1">首页</a></li>
</ul>

<ul class="pagination">
	<c:if test="${taskpage.hasPrev()==true }">
		<li><a href="queryServlet?method=getAllTaskPage&pageNo=${taskpage.getPrev() }">&laquo;</a></li>
	</c:if>
	<li class="active"><a href="#">${taskpage.pageNo }</a></li>
	<c:if test="${taskpage.hasNext()==true }">
		<li><a href="queryServlet?method=getAllTaskPage&pageNo=${taskpage.getNext() }">&raquo;</a></li>
	</c:if>
</ul>

<ul class="pagination">
	<li><a href="queryServlet?method=getAllTaskPage&pageNo=${taskpage.totalPageNumber}">尾页：${taskpage.totalPageNumber }</a></li>
</ul>
<div class="input-group">
			<span class="input-group-addon">跳转到</span>
			<input type="text" class="form-control" id="pageNo">
			<span class="input-group-addon">页</span>
</div>
</c:if>

<c:if test="${empty taskpage }">
<h2>尚无任务详情</h2>
</c:if>
<br><br>
</center>
</body>
</html>