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

span{
	font-size: 45px;
	text-shadow:3px 2px 3px #FAEBD7;
	color:#8B5F65
	
}
.docdiv{
	
	width:600px;
	height:400px;
	border:2px solid #000;
	background:#FFFFE0;
	float:left;
	font-family: "楷体";
	font-size: 20px;
	padding:15px;
	margin-left:10px;
	margin-right:10px
}
#description{
	width:300px;
	height:30px;
	overflow:auto;
}
.submit{
color: #8B2500;
wdith:90px;
height:50px
}
.blackdoc{
color:#CD0000;
margin-top:40px;
}
.pass{
color:#32CD32;
margin-top:40px;
}
h4{
	text-shadow:3px 2px 3px #FAEBD7;
	color:#8B5F65
}
.passbutton{
	width:100px;
	height:22px;
	color:#32CD32;
	font-size:18px;
	font-family: "楷体";
}
.blackdocbutton{
	width:100px;
	height:22px;
	color:#CD0000;
	font-size:18px;
	font-family: "楷体";
	margin-top: -20px;
}
#content{
	width:550px;
	height:100px;
	font-size:20px;
	overflow:scroll;
	background-color: #C1FFC1
}
</style>
<script type="text/javascript" src="script/jquery-1.7.2.js"></script>
<script type="text/javascript">
$(function(){
	var fontsize=$("font").size();
	alert("共发现"+fontsize+"个疑似敏感词");
})
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
			${doc.content }
		</div>
		</div>
	</c:forEach>
	</div>
	<br>
	
	<c:if test="${empty docs }">
		<h3>本次搜索没有发现非法文档</h3>
	</c:if>
</center>	
</body>
</html>