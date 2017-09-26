<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>敏感词过滤系统首页</title>
<style type="text/css">
	body{
	background-color:#E8E8E8;
	}
	.input-path{
		
		width:552px;
		height:308px;
		float:top;
		margin-top:30px;
	}

	.startsearch{
		width:200px;
		height:50px;
	}
	.text{
		height:25px;
		width:300px;
		line-height: 25px;
		margin-top:30px;
		font-size: 15px;
	}
	.index{
		font-size: 20px；
	}
	span{
	font-size: 30px;
	text-shadow:3px 2px 3px #FAEBD7;
	color:#668B8B;
}
	#name{
		width:300px;
	}
	
	.startnow{
		margin-left: -25px;
	}
</style>
<script type="text/javascript" src="script/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$(".index").click(function(){
			var t=$(":text");
			if(t.val()==""){
				alert("输入不能为空");
				return false;
			}
		})
		
	})
</script>

</head>
<body>

<center>
<br><br>
<div class="input-path">
<span>输入文档目录路径，解析目录下所有文档</span>
<br>
实例：D:\SolrApplication\solrhome\collection2\doc
<form action="indexServlet?method=createIndex" method="post" target="_blank" class="form-inline" role="form">
	<div class="form-group">
	<input type="text" name="path" class="form-control" id="name" 
			   placeholder="在此输入路径">
	<input type="submit" value="开始解析文档" class="btn btn-default index">
	</div>
	</form>
<br>
<div class="startsearch">
<a href="queryServlet?method=startSearch" target="_blank"><h3><button class="btn btn-default startnow">直接从现有索引库中搜索敏感词</button></h3></a>
<br>
</div>
</div>
<br>
</center>
</body>
</html>