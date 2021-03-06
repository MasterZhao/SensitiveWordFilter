<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.min.css">  
<script src="script/jquery-3.2.1.min.js"></script>
<script src="script/bootstrap.min.js"></script>
<style type="text/css">
	.nav-tabs{
		background-color: #A4D3EE;
		font-size: 20px;
	}
	.onthetop{
		height:65px;
		margin-bottom:0px;
	}
	.title{
			font-size: 35px;
			text-shadow:3px 2px 3px #969696;
		}
	.navbar-text{
		padding-right: 20px;
		font-size: 20px;
	}
	.dropdown-menu{
		width:100%;
		font-size: 18px;
	}

</style>
</head>
<body>
<!-- 首行导航栏 -->
	<nav class="navbar navbar-default onthetop" role="navigation">
		 <div class="container-fluid"> 
   			<div class="navbar-header">
        	<a class="navbar-brand title" href="#">教&nbsp;育&nbsp;资&nbsp;源&nbsp;审&nbsp;核&nbsp;系&nbsp;统</a>
   			</div>
   		 	<div> 
        	<!-- <a href="#"><p class="navbar-text navbar-right">点击此处查看操作说明</p></a> -->
    		</div>
		</div>
	</nav>
	<!-- 第二行导航栏 -->
 <ul class="nav nav-tabs nav-justified">
	<li><a href="index.jsp">首页</a></li>
	<li><a href="searchbyword.jsp">手动查询敏感词</a></li>
	<li class="dropdown">
	 <a class="dropdown-toggle" data-toggle="dropdown" href="">
        敏感词管理<span class="caret"></span>
        <ul class="dropdown-menu">
        <li><a href="queryServlet?method=getWordPage&pageNo=1">全部敏感词</a></li>
        <li><a href="queryServlet?method=getWordPage&pageNo=1&wordSymbol=0">已停用敏感词</a></li>
        <li><a href="queryServlet?method=getWordPage&pageNo=1&wordSymbol=1">已启用敏感词</a></li>
        <li><a href="upload.jsp">导入敏感词</a></li>
        <li><a href="downloadServlet?method=downloadWords">导出敏感词</a></li>
      </ul>
	</li>
	<li class="dropdown">
	 <a class="dropdown-toggle" data-toggle="dropdown" href="">
        资源状态管理<span class="caret"></span>
        <ul class="dropdown-menu">
        <li>
        <a href="queryServlet?method=getBlackListPage&pageNo=1">查看黑名单</a>
        </li>
        <li><a href="queryServlet?method=getPassDocPage&pageNo=1">查看已通过审核的资源</a></li>
        <li><a href="queryServlet?method=getAllDocPage&pageNo=1">浏览全部资源</a></li>
        <li><a href="queryServlet?method=getDocWithoutCheckPage&pageNo=1">待审核资源</a></li>
      </ul>
	</li>
	
	<li class="dropdown">
	 <a class="dropdown-toggle" data-toggle="dropdown" href="">
        任务日志<span class="caret"></span>
        <ul class="dropdown-menu">
        <li>
        <a href="queryServlet?method=getAllTaskPage&pageNo=1">任务记录</a>
        </li>
        <li><a href="queryServlet?method=getTaskReport&pageNo=1">任务报告</a></li>
      </ul>
	</li>
</ul>

</body>
</html>