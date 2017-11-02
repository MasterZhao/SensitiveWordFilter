<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
 <%@include file="common.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>黑名单</title>
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
	width:90%;
	font-size: 18px
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
		if(val<1||val>parseInt("${blacklistpage.getTotalPageNumber()}")){
			alert("输入的页码超出范围");
			$(this).val("");
			return;
		}
		//实现跳转
		var href="queryServlet?method=getBlackListPage&pageNo="+pageNo;
		window.location.href=href;
	})
	$(".pass").click(function(){
		var flag=confirm("确定将文档标记为审核通过吗？");
		if(!flag){
			return false;
		}
	})
})

</script>
</head>
<body>
<center>
<span id="title">黑名单</span>

<c:if test="${!empty blacklistpage.list }">
<h2>共${blacklistpage.totalItemNumber }条记录</h2>
<table class="table table-bordered table-hover mytable">
	<tr id="info">
	<td>文件名</td>
	<td>本地路径</td>
	<td style="width:15%">操作</td>
	</tr>
	<c:forEach items="${blacklistpage.list }" var="docItem">
		<tr>
		<td style="display:none" name="${docItem.id }">${docItem.id }</td>
		<td>${docItem.title }</td>
		<td>${docItem.docPath }</td>
		<td>
		<%-- <form action="docServlet?method=removeFromBlackList" method="post">
		<input type="hidden" name="path" value="${docItem.docPath }">
		<input type="submit" class="btn btn-default" value="通过审核">
		</form> --%>
		<a href="docServlet?method=removeFromBlackList&path=${docItem.docPath }&pageNo=${blacklistpage.pageNo}">
		<button class="btn btn-default pass" >通过审核</button>
		</a>
		<!-- <a href="docServlet?method=deleteDoc&pageNo=${blacklistpage.pageNo}&pageSymbol=blacklist"><button class="btn btn-default">删除</button></a> -->
		</td>
		</tr>
	</c:forEach>
</table>

<ul class="pagination">
	<li><a href="queryServlet?method=getBlackListPage&pageNo=1">首页</a></li>
</ul>

<ul class="pagination">
	<c:if test="${blacklistpage.hasPrev()==true }">
		<li><a href="queryServlet?method=getBlackListPage&pageNo=${blacklistpage.getPrev() }">&laquo;</a></li>
	</c:if>
	<li class="active"><a href="#">${blacklistpage.pageNo }</a></li>
	<c:if test="${blacklistpage.hasNext()==true }">
		<li><a href="queryServlet?method=getBlackListPage&pageNo=${blacklistpage.getNext() }">&raquo;</a></li>
	</c:if>
</ul>

<ul class="pagination">
	<li><a href="queryServlet?method=getBlackListPage&pageNo=${blacklistpage.totalPageNumber}">尾页：${blacklistpage.totalPageNumber }</a></li>
</ul>
<div class="input-group">
			<span class="input-group-addon">跳转到</span>
			<input type="text" class="form-control" id="pageNo">
			<span class="input-group-addon">页</span>
</div>
</c:if>

<c:if test="${empty blacklistpage.list }">
<h2>当前黑名单中无记录</h2>
</c:if>
<br><br>
</center>
</body>
</html>