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
	margin:0 auto;
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
	width:99%;
	font-size:20px;
	overflow-y:scroll;
	/* background-color: #C1FFC1 */
}
	.panel{
	width:45%;
	float:left;	
	margin-left:2%;
	margin-right:2%;
	}
.panel-heading{
	width:99%;
}
</style>
<script type="text/javascript">
$(function(){
	$("#pageNo").change(function(){
		var word=$("#word").val();
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
		if(val<1||val>parseInt("${searchdocpage.getTotalPageNumber()}")){
			alert("输入的页码超出范围");
			$(this).val("");
			return;
		}
		//实现跳转
		var href="queryServlet?method=startSearch&pageNo="+pageNo+"&word="+word;
		window.location.href=href;
	})
})
</script>
</head>
<body>
<center>
<br><br>
<div><span>共发现${searchdocpage.totalItemNumber }篇文章包含该词</span>
<br>
</div>
<br>
<c:if test="${!empty searchdocpage }">
<%-- <table class="table table-bordered table-hover mytable">
	<tr id="info">
	<td>文件名</td>
	<td>本地路径</td>
	<td>待审核内容</td>
	<td>操作</td>
	</tr>
	<c:forEach items="${searchdocpage.list }" var="docItem">
	<tr>
	<td style="display:none" name="${docItem.id }">${docItem.id }</td>
	<td>${docItem.title }</td>
	<td>${docItem.docPath }</td>
	<td>${docItem.description }</td>
	<td>
	<form action="docServlet?method=removeFromBlackList&pageNo=${searchdocpage.pageNo }" method="post">
		<input type="hidden" name="path" value="${docItem.docPath }">
		<input type="submit" class="btn btn-default" value="加入黑名单">
	</form>
	<form action="docServlet?method=removeFromBlackList&pageNo=${searchdocpage.pageNo }" method="post">
		<input type="hidden" name="path" value="${docItem.docPath }">
		<input type="submit" class="btn btn-default" value="通过审核">
	</form>
	</td>
	</tr>
</c:forEach>
</table> --%>

<table class="table table-bordered table-hover mytable">
	<tr>
	<c:forEach items="${searchdocpage.list }" var="doc">
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
	<c:if test="${doc.docSymbol==1 or doc.docSymbol==2 }">
	已通过审核&nbsp; <!-- <a href="docServlet?method=insertBlackList&path=${doc.docPath }&pageNo=${searchdocpage.pageNo}"><button type="submit" class="btn btn-default">加入黑名单</button> </a> -->
	</c:if>
	<c:if test="${doc.docSymbol==0 }">
	已加入黑名单 &nbsp;<!-- <a href="docServlet?method=pass&path=${doc.docPath }&pageNo=${searchdocpage.pageNo}"><button type="submit" class="btn btn-default">通过审核</button> </a> -->
	</c:if> 
	<c:if test="${doc.docSymbol==-1 }">
	  <%-- <a href="docServlet?method=insertBlackList&path=${doc.docPath }&pageNo=${searchdocpage.pageNo}"><button type="submit" class="btn btn-default">加入黑名单</button> </a>
	  <a href="docServlet?method=pass&path=${doc.docPath }&pageNo=${searchdocpage.pageNo}"><button type="submit" class="btn btn-default">通过审核</button> </a> --%>
	待审核
	</c:if>
	</div>
</div>
	</c:forEach>
</tr>
</table>

<div>
	<ul class="pagination">
		<li><a href="queryServlet?method=startSearch&pageNo=1&word=${word }">首页</a></li>
	</ul>

	<ul class="pagination">
	<c:if test="${searchdocpage.hasPrev()==true }">
		<li><a href="queryServlet?method=startSearch&pageNo=${searchdocpage.getPrev() }&word=${word}">&laquo;</a></li>
	</c:if>
	<li class="active"><a href="#">${searchdocpage.pageNo }</a></li>
	<c:if test="${searchdocpage.hasNext()==true }">
		<li><a href="queryServlet?method=startSearch&pageNo=${searchdocpage.getNext() }&word=${word}">&raquo;</a></li>
	</c:if>
	</ul>

	<ul class="pagination">
		<li><a href="queryServlet?method=startSearch&pageNo=${searchdocpage.totalPageNumber}&word=${word}">尾页：${searchdocpage.totalPageNumber }</a></li>
	</ul>
	<div class="input-group">
			<span class="input-group-addon">跳转到</span>
			<input type="text" class="form-control" id="pageNo">
			<span class="input-group-addon">页</span>
	</div>
	
	<input type="hidden" value="${word }" id="word">
</div>
</c:if>

<c:if test="${empty searchdocpage }">
<h2>暂无待审核资源</h2>
</c:if>
<br><br>
</center>
</body>
</html>