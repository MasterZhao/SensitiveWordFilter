<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
 <%@include file="common.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>待审查文档</title>
<style type="text/css">
	#info{
		background-color:#CCCCCC;
	}
	body{
	background-color:#E8E8E8;
	}
	.title{
	color:#C5C1AA;
	text-shadow:3px 2px 3px #969696;
	}
	.mytable{
	width:90%;
	font-size: 18px;
	}
	.input-group{
	width:12%;
	}
	span{
	font-size: 40px;
	text-shadow:3px 2px 3px #FAEBD7;

}
#content{
	height:100px;
	font-size:20px;
	overflow-y:scroll;
	/* background-color: #C1FFC1 */
}
	.panel{
	width:46%;
	float:left;	
	margin-left:2%;
	margin-right:1%
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
		if(val<1||val>parseInt("${checkdocpage.getTotalPageNumber()}")){
			alert("输入的页码超出范围");
			$(this).val("");
			return;
		}
		//实现跳转
		var href="queryServlet?method=getDocWithoutCheckPage&pageNo="+pageNo;
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
<br><br>
<div><span>${checkdocpage.totalItemNumber }篇待审核资源</span>
<br>
<c:if test="${!empty size }">
	<span>审核了${size }篇文档</span>
</c:if>
</div>
<br>
<c:if test="${!empty checkdocpage }">
<%-- <table class="table table-bordered table-hover mytable">
	<tr id="info">
	<td>文件名</td>
	<td>本地路径</td>
	<td>待审核内容</td>
	<td>操作</td>
	</tr>
	<c:forEach items="${checkdocpage.list }" var="docItem">
	<tr>
	<td style="display:none" name="${docItem.id }">${docItem.id }</td>
	<td>${docItem.title }</td>
	<td>${docItem.docPath }</td>
	<td>${docItem.description }</td>
	<td>
	<form action="docServlet?method=removeFromBlackList&pageNo=${checkdocpage.pageNo }" method="post">
		<input type="hidden" name="path" value="${docItem.docPath }">
		<input type="submit" class="btn btn-default" value="加入黑名单">
	</form>
	<form action="docServlet?method=removeFromBlackList&pageNo=${checkdocpage.pageNo }" method="post">
		<input type="hidden" name="path" value="${docItem.docPath }">
		<input type="submit" class="btn btn-default" value="通过审核">
	</form>
	</td>
	</tr>
</c:forEach>
</table> --%>

<table class="table table-bordered table-hover mytable">
	<tr>
	<c:forEach items="${checkdocpage.list }" var="doc">
<div class="panel panel-default">
	<div class="panel-heading">
		<h4>文件名：${doc.title }</h4>
		<p>文件路径：${doc.docPath }</p>
	</div>
	<div class="panel-body" >
		<div id="content">
		${doc.description }
		</div>	
	</div>
	<div class="panel-footer" style="height:60px;margin:0 auto;">
	  <a href="docServlet?method=insertBlackList&path=${doc.docPath }&pageNo=${checkdocpage.pageNo}"><button type="submit" class="btn btn-default insertblack">加入黑名单</button> </a>
	  <a href="docServlet?method=pass&path=${doc.docPath }&pageNo=${checkdocpage.pageNo}"><button type="submit" class="btn btn-default pass">通过审核</button> </a>
	</div>
</div>
	</c:forEach>
</tr>
</table>
<div>
	<ul class="pagination">
		<li><a href="queryServlet?method=getDocWithoutCheckPage&pageNo=1">首页</a></li>
	</ul>

	<ul class="pagination">
	<c:if test="${checkdocpage.hasPrev()==true }">
		<li><a href="queryServlet?method=getDocWithoutCheckPage&pageNo=${checkdocpage.getPrev() }">&laquo;</a></li>
	</c:if>
	<li class="active"><a href="#">${checkdocpage.pageNo }</a></li>
	<c:if test="${checkdocpage.hasNext()==true }">
		<li><a href="queryServlet?method=getDocWithoutCheckPage&pageNo=${checkdocpage.getNext() }">&raquo;</a></li>
	</c:if>
	</ul>

	<ul class="pagination">
		<li><a href="queryServlet?method=getDocWithoutCheckPage&pageNo=${checkdocpage.totalPageNumber}">尾页：${checkdocpage.totalPageNumber }</a></li>
	</ul>
	<div class="input-group">
			<span class="input-group-addon">跳转到</span>
			<input type="text" class="form-control" id="pageNo">
			<span class="input-group-addon">页</span>
	</div>
</div>
</c:if>

<c:if test="${empty checkdocpage }">
<h2>暂无待审核资源</h2>
</c:if>
<br><br>
</center>
</body>
</html>