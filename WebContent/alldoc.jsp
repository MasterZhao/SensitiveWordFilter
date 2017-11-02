<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
 <%@include file="common.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>全部文档</title>
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
		if(val<1||val>parseInt("${alldocpage.getTotalPageNumber()}")){
			alert("输入的页码超出范围");
			$(this).val("");
			return;
		}
		//实现跳转
		var href="queryServlet?method=getAllDocPage&pageNo="+pageNo;
		window.location.href=href;
	})
	
	$(".pass").click(function(){
		var flag=confirm("确定将文档标记为通过审核吗？");
		if(!flag){
			return false;
		}
	})
	$(".insertblack").click(function(){
		var flag=confirm("确定将文档放入黑名单吗？");
		if(!flag){
			return false;
		}
	})
})

</script>
</head>
<body>
<center>
<span id="title">全部资源</span>
<c:if test="${!empty alldocpage }">
<table class="table table-bordered table-hover mytable">
	<tr id="info">
	<td>文件名</td>
	<td>本地路径</td>
	<td>状态</td>
	<td style="width:10%;">操作</td>
	</tr>
	<c:forEach items="${alldocpage.list }" var="docItem">
	<tr>
	<td style="display:none" name="${docItem.id }">${docItem.id }</td>
	<td>${docItem.title }</td>
	<td>${docItem.docPath }</td>
	
	<!-- 当资源已经在黑名单中时，可以选择通过审核 -->
	<c:if test="${docItem.docSymbol==0 }">
	<td>已加入黑名单</td>
	<td>
	<form action="docServlet?method=removeFromBlackList&pageNo=${alldocpage.pageNo }&pageSymbol=alldoc" method="post">
		<input type="hidden" name="path" value="${docItem.docPath }">
		<input type="submit" class="btn btn-default pass" value="通过审核">
	</form>
	</td>
	</c:if>
	
	<!-- 当资源属于已通过审核状态时，可以选择加入黑名单 -->
	<c:if test="${docItem.docSymbol==1 or docItem.docSymbol==2 }">
	<td>已通过审核</td>
	<td>
	<form action="docServlet?method=insertBlackList&pageNo=${alldocpage.pageNo }&pageSymbol=alldoc" method="post">
		<input type="hidden" name="path" value="${docItem.docPath }">
		<input type="submit" class="btn btn-default insertblack" value="加入黑名单">
	</form>
	</td>
	</c:if>
	<!-- 若资源状态为待审核，则显示加入黑名单和通过审核两种操作 -->
	<c:if test="${docItem.docSymbol==-1 }">
	<td>待审核（可进入待审核资源页面查看详情）</td>
	<td>
	<form action="docServlet?method=insertBlackList&pageNo=${alldocpage.pageNo }&pageSymbol=alldoc" method="post">
		<input type="hidden" name="path" value="${docItem.docPath }">
		<input type="submit" class="btn btn-default insertblack" value="加入黑名单">
	</form>
	<form action="docServlet?method=removeFromBlackList&pageNo=${alldocpage.pageNo }&pageSymbol=alldoc" method="post">
		<input type="hidden" name="path" value="${docItem.docPath }">
		<input type="submit" class="btn btn-default pass" value="通过审核">
	</form>
	</td>
	</c:if>
	</tr>
</c:forEach>
</table>

<ul class="pagination">
	<li><a href="queryServlet?method=getAllDocPage&pageNo=1">首页</a></li>
</ul>

<ul class="pagination">
	<c:if test="${alldocpage.hasPrev()==true }">
		<li><a href="queryServlet?method=getAllDocPage&pageNo=${alldocpage.getPrev() }">&laquo;</a></li>
	</c:if>
	<li class="active"><a href="#">${alldocpage.pageNo }</a></li>
	<c:if test="${alldocpage.hasNext()==true }">
		<li><a href="queryServlet?method=getAllDocPage&pageNo=${alldocpage.getNext() }">&raquo;</a></li>
	</c:if>
</ul>

<ul class="pagination">
	<li><a href="queryServlet?method=getAllDocPage&pageNo=${alldocpage.totalPageNumber}">尾页：${alldocpage.totalPageNumber }</a></li>
</ul>
<div class="input-group">
			<span class="input-group-addon">跳转到</span>
			<input type="text" class="form-control" id="pageNo">
			<span class="input-group-addon">页</span>
</div>
</c:if>

<br><br>
</center>
</body>
</html>