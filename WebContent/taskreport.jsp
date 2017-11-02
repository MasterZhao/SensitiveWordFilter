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
	font-size: 20px;
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
		font-size: 18px;
	}
	.panel{
			width:55%;	
			font-size: 20px;
		}
		.panel-heading{
			height: 50px;
		}
</style>
<script type="text/javascript">
</script>
</head>
<body>
<center>
<span id="title">任务报告</span>
<br>
<!-- request.setAttribute("alltasknum", allTaskNum);
		request.setAttribute("taskdiscoverednum",taskDiscoveredNum);
		request.setAttribute("alldocnunm", docNum);
		request.setAttribute("blacklistnum", blackListNum);
		request.setAttribute("allwordnum", wordNum);
		request.setAttribute("worddiscoverednum", wordDiscoveredNum);
		request.setAttribute("levelcountlist", levelCountList);
 -->
 <br>
<div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">任务统计报告</div>
  <div class="panel-body">
    <p>已执行任务：${alltasknum }次，&nbsp;其中${taskdiscoverednum }次任务发现敏感词</p>
  </div>

  <!-- List group -->
  <ul class="list-group">
    <li class="list-group-item">共审核${alldocnum}篇文档，已将${ blacklistnum }篇文档加入黑名单</li>
    <li class="list-group-item">当前词库中敏感词数量为${allwordnum }个，累计共出现敏感词${worddiscoverednum }个</li>
    <li class="list-group-item">
    		统计敏感词分级<br>
    <c:forEach items="${levelcountlist }" var="levelCount">
    	<c:if test="${levelCount.wordLevel==0 }">
    	已经被删除的敏感词${levelCount.levelCount }次
    	</c:if>
    	&nbsp;    	
    	<c:if test="${levelCount.wordLevel!=0 }">
    		${levelCount.wordLevel }级敏感词${levelCount.levelCount }次
    	</c:if>
    </c:forEach>
    </li>
  </ul>
</div>

</center>
</body>
</html>