<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
 <%@include file="common.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务详情</title>
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
	.table-div{
		margin-left: 7%;
		float: left;
		width:40%;
	}
	.input-group{
	width:12%;
	}
	.table{
		font-size: 15px;
	}
	.panel{
			width:60%;	
			
		}
</style>
<script type="text/javascript">
</script>
</head>
<body>
<center>
<span id="title">${taskId }号任务</span>
<c:if test="${empty wordcountlist }">
	<h2>该任务未发现敏感词</h2>
</c:if>
<c:if test="${!empty wordcountlist }">
<%-- <div class="row">
	<div class="table-div">
		<table class="table">
			<!--通过添加 .table-hover class，当指针悬停在行上时会出现浅灰色背景-->
	<caption><h3>敏感词统计</h3></caption>
   <thead>
      <tr>
         <th>敏感词</th>
         <th>出现次数</th>
      </tr>
   </thead>
   <tbody>
   <c:forEach items="${wordcountlist }" var="wordcount">
   	<tr>
         <td>${wordcount.word }</td>
         <td>${wordcount.count }</td>
     </tr>
   </c:forEach>
   </tbody>
</table>
</div><!-- end of class:table-div -->
		
<div class="table-div">
	<table class="table">
	<caption><h3>敏感词分级统计</h3></caption>
	<thead>
		<tr>
			<th>级别</th>
			<th>出现次数</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${levelcountlist }" var="levelcount">
		<tr>
			<td>
			<c:if test="${levelcount.wordLevel==0 }">已删除的敏感词</c:if>
			<c:if test="${levelcount.wordLevel!=0 }">${levelcount.wordLevel }</c:if>
			</td>
			<td>${levelcount.levelCount }</td>
		</tr>
	</c:forEach>
	</tbody>
	</table>
	</div><!-- end of class:table-div -->	
</div><!-- end of class:row --> --%>

<!-- 使用面板显示 -->
<div class="panel panel-default">
	<div class="panel-heading">
		<caption><h4>敏感词统计</h4></caption>
	</div>
	<table class="table">
		<th>敏感词</th><th>出现次数 </th>
		<c:forEach items="${wordcountlist }" var="wordcount">
			<tr>
			<td>${wordcount.word }</td><td>${wordcount.count }次</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div class="panel panel-default">
	<div class="panel-heading">
		<caption><h4>敏感词分级统计</h4></caption>
	</div>
	<table class="table">
		<th>敏感词级别</th><th>出现次数 </th>
		<c:forEach items="${levelcountlist }" var="levelcount">
			<tr>
			<td>
			<c:if test="${levelcount.wordLevel==0 }">已删除的敏感词</c:if>
			<c:if test="${levelcount.wordLevel!=0 }">${levelcount.wordLevel }</c:if>
			</td>
			<td>${levelcount.levelCount }次</td>
			</tr>
		</c:forEach>
	</table>
</div>

</c:if>
<br><br>
</center>
</body>
</html>