<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
 <%@include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
	width:45%;
	float:left;	
	margin-left:2%;
	margin-right:2%
	}
</style>
<script type="text/javascript" src="script/jquery-1.7.2.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
<center>
	<br>
	<span>发现${docs.size() }篇文档可能不符合要求</span>
	<h4>***根据相关度进行排序，需要人工审核***</h4>
	<br><br>
	
	<div id="docs">
	<c:forEach items="${docs }" var="doc">
	<div class="docdiv">
		<h4>文件名：${doc.title }</h4>
		<h4>文件路径：${doc.docPath }</h4>
		
		<!-- 若docsymbol=1，说明还没有加入黑名单，显示“加入黑名单”选项和“通过审核”选项 -->
		<c:if test="${doc.docSymbol==1 }">
		 <form action="docServlet?method=insertBlackList" method="post">
			<input type="hidden" name="docpath" value="${doc.docPath }">
			<input type="submit" value="加入黑名单" class="blackdocbutton">
		 </form>
		 <br>
		  <form action="docServlet?method=pass" method="post">
			<input type="hidden" name="docpath" value="${doc.docPath }">
			<input type="submit" value="通过审核" class="passbutton">
		  </form>
		</c:if>
		
		<c:if test="${doc.docSymbol==0 }">
			<h2 class="blackdoc">已加入黑名单</h2>
		</c:if>
		
		<c:if test="${doc.docSymbol==2 }">
			<h2 class="pass">该文档已经过人工审核</h2>
		</c:if>
		<h4>详情：</h4>
		<div id="content">
			${doc.description }
		</div>
		</div>
	</c:forEach>
	</div>
	<br>
	<ul class="pagination">
	<li><a href="queryServlet?method=getWordPage&pageNo=1&wordSymbol=${wordSymbol }">首页</a></li>
</ul>

<ul class="pagination">
	<c:if test="${wordpage.hasPrev()==true }">
		<li><a href="queryServlet?method=getWordPage&pageNo=${wordpage.getPrev() }&wordSymbol=${wordSymbol}">&laquo;</a></li>
	</c:if>
	<li class="active"><a href="#">${wordpage.pageNo }</a></li>
	<c:if test="${wordpage.hasNext()==true }">
		<li><a href="queryServlet?method=getWordPage&pageNo=${wordpage.getNext() }&wordSymbol=${wordSymbol}">&raquo;</a></li>
	</c:if>
</ul>

<ul class="pagination">
	<li><a href="queryServlet?method=getWordPage&pageNo=${wordpage.totalPageNumber}&wordSymbol=${wordSymbol}">尾页：${wordpage.totalPageNumber }</a></li>
</ul>
<div class="input-group">
			<span class="input-group-addon">跳转到</span>
			<input type="text" class="form-control" id="pageNo">
			<span class="input-group-addon">页</span>
</div>
	<c:if test="${empty docs }">
		<h3>本次搜索没有发现非法文档</h3>
	</c:if>
</center>	
</body>
</html>