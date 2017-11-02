<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
 <%@include file="common.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已通过审核资源</title>
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
	width:95%;
	font-size: 18px
	}
	.input-group{
	width:12%;
	}
	.pass{
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
		if(val<1||val>parseInt("${passdocpage.getTotalPageNumber()}")){
			alert("输入的页码超出范围");
			$(this).val("");
			return;
		}
		//实现跳转
		var href="queryServlet?method=getPassDocPage&pageNo="+pageNo;
		window.location.href=href;
	})
	
	$(".insertblack").click(function(){
		var flag=confirm("确定将此文档放入黑名单吗？");
		if(!flag){
			return false;
		}
	})
})

</script>
</head>
<body>
<center>

<c:if test="${!empty passdocpage }">
<span id="title">已通过审核的资源</span>
<table class="table table-bordered table-hover mytable">
	<tr id="info">
	<td>文件名</td>
	<td>本地路径</td>
	<td>状态</td>
	<td style="width:10%;">操作</td>
	</tr>
	<c:forEach items="${passdocpage.list }" var="docItem">
	<tr>
	<td style="display:none" name="${docItem.id }">${docItem.id }</td>
	<td>${docItem.title }</td>
	<td>${docItem.docPath }</td>
	
	<!-- 资源为自动通过审核状态 -->
	<c:if test="${docItem.docSymbol==1 }">
	<td>通过机器审核</td>
	<td>
	<form action="docServlet?method=insertBlackList&pageNo=${passdocpage.pageNo }&pageSymbol=passdoc" method="post">
		<input type="hidden" name="path" value="${docItem.docPath }">
		<input type="submit" class="btn btn-default insertblack" value="加入黑名单">
	</form>
	</td>
	</c:if>
	
	<!-- 当资源属于已通过审核状态时，可以选择加入黑名单 -->
	<c:if test="${docItem.docSymbol==2 }">
	<td>通过人工审核</td>
	<td>
	<form action="docServlet?method=insertBlackList&pageNo=${passdocpage.pageNo }&pageSymbol=passdoc" method="post">
		<input type="hidden" name="path" value="${docItem.docPath }">
		<input type="submit" class="btn btn-default insertblack" value="加入黑名单">
	</form>
	</td>
	</c:if>
	</tr>
</c:forEach>
</table>

<ul class="pagination">
	<li><a href="queryServlet?method=getPassDocPage&pageNo=1">首页</a></li>
</ul>

<ul class="pagination">
	<c:if test="${passdocpage.hasPrev()==true }">
		<li><a href="queryServlet?method=getPassDocPage&pageNo=${passdocpage.getPrev() }">&laquo;</a></li>
	</c:if>
	<li class="active"><a href="#">${passdocpage.pageNo }</a></li>
	<c:if test="${passdocpage.hasNext()==true }">
		<li><a href="queryServlet?method=getPassDocPage&pageNo=${passdocpage.getNext() }">&raquo;</a></li>
	</c:if>
</ul>

<ul class="pagination">
	<li><a href="queryServlet?method=getPassDocPage&pageNo=${passdocpage.totalPageNumber}">尾页：${passdocpage.totalPageNumber }</a></li>
</ul>
<div class="input-group">
			<span class="input-group-addon">跳转到</span>
			<input type="text" class="form-control" id="pageNo">
			<span class="input-group-addon">页</span>
</div>
</c:if>
<c:if test="${empty passdocpage }">
<span id="title">已通过审核的资源</span>
</c:if>
<br><br>
</center>
</body>
</html>