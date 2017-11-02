<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>手动搜索敏感词</title>
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
	#word{
		width: 70%;
	}
	.startnow{
		margin-left: -25px;
	}
	.form-inline{
		margin-bottom: 40px;
	}
	.form-group{
		width: 100%;
	}
</style>
<script type="text/javascript" src="script/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		
		$(".search").click(function(){
			//获取当前input的text对象（name为word）
			var t=$(" input[ name='word' ] ");
			var length=t.val().length;
			if(length==0){
				alert("输入不能为空");
				return false;
			}
			if(t.val().length>20){
				alert("输入过长，请重新输入");
				return false;
			}
		})
		
	})
</script>
</head>
<body>
<center>
<br><br><br><br>
<div class="input-path">
<span>手动查询敏感词</span>
<h4>（从已抓取的文档索引中搜索，本次查询不会作为任务记录）</h4>
<br><br>
	<form action="queryServlet?method=startSearch" method="post" target="_blank" class="form-inline" role="form">
	<div class="form-group">
	<input type="text" name="word" class="form-control" id="word" 
			   placeholder="在此输入要查询的词汇，为保证准确性请不要超过20字">
	<input type="submit" value="查询" class="btn btn-default search">
	</div>
	</form>
</div>
<br>

</center>
</body>
</html>