<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
 <%@include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>敏感词管理</title>
<style type="text/css">
table{
	font-family: "楷体";
	font-size:20px
}
#hasstoped{
	color:red;
}
body{
	background-color:#E8E8E8;
	}
#info{
	font-size:25px;
	background-color:#CDC0B0;
}
#new{
	width: 700px;
	height:50px;
	font-size: 18px;
	position:relative;
	margin-bottom:20px;
	
} 
.input-group{
	width:13%;
}
button{
	width:50px;
	height:25px
}
option{
	font-size: 16px;
}
#newword{
	width:150px;
	height:50px;
	background-color:#F5F5DC;
	font-size: 25px;
	font-family: "楷体";
	text-shadow:3px 2px 2px #F5F5F5
} 
.selected{
	font-size: 25px;
}
.btn{
	font-size: 18px;
	width:60px;
	height:35px;
}
select{
	width:150px;
	height:35px;
	font-size:20px;
}
button{
 width:90px;
 height:35px;
 background-color: #EEEED1;
 font-size: 30px;
}
.weihu{
	width:200px;
}
.mytable{
width:800px
}
.span6{
		width:15%;
		float: left;
		margin-left: 8%;
		margin-right: 8%;
		font-size: 18px;
	}
	.image{
		width:30%;
	}
	.row-fluid{
		width:50%;
	}
</style>
<script type="text/javascript" src="script/jquery-1.7.2.js"></script>
<script type="text/javascript">
$(function(){
	
	var wordSymbol=$("#wordsymbol").val();
	
	//点击"新增敏感词"，弹出form
 	$("#newword").mouseover(function(){
		$("#newword").css("background-color","#EEE9BF");
	})
	$("#newword").mouseleave(function(){
		$("#newword").css("background-color","#F5F5DC");
	})
/* 	$("#newword").click(function(){
		$("#new").css("display","block");
	})  */
	
	//判断格式是否符合要求
	$("#submit").click(function(){
		 var t = $(":text");
	     if (t.val() == "") {
	    	 alert("不能为空！");
	         return false;
	     }
	     if(t.val().length>20){
	    	 alert("输入字数过长，请修改！");
	    	 return false;
	     }
		var val=$('input:option[name="level"]:checked').val();
		if(val==null){
			alert("请选择等级");
			return false;
		}
		else{
			return true;
		}
	});
	//删除单个敏感词		
	$(".delete").click(function(){
		var $tr=$(this).parent().parent();
		var word=$.trim($tr.find("td:first").text());
		var flag=confirm("确定删除["+word+"]吗？")
		if(!flag){
			return false;
		}
		
	});
	//启用单个敏感词
	$(".start").click(function(){
		var $tr=$(this).parent().parent();
		var word=$.trim($tr.find("td:first").text());
		var flag=confirm("确定启动敏感词  ["+word+" ]吗？");
		if(!flag){
			return false;
		}	
	});
	//停用单个敏感词
	$(".stop").click(function(){
		var $tr=$(this).parent().parent();
		var word=$.trim($tr.find("td:first").text());
		var flag=confirm("确定停用敏感词  ["+word+" ]吗？")
		if(!flag){
			return false;
		}
	});
	//清空敏感词库
	$("#deleteall").click(function(){
		var flag=confirm("清空后无法还原，确定清空敏感词库吗？");
		if(!flag){
			return false;
		}
	})
	$(".selectlevel").click(function(){
		 $(this).children('option').first().hide();
	});
	
	$(".selectlevel").change(function(){
		var oldLevel=$(this).children('option').first().text();
		var wordId=$(this).children('option').first().attr('name');
		//alert(wordId);
		var newLevel=$(this).find("option:selected").text(); 

		if(oldLevel==newLevel){
			return false;
		}
		newLevel=$(this).children("option:selected").val();
		//alert(newLevel);
		var url="wordServlet";
		var args={"method":"changeLevel","id":wordId,"level":newLevel};
		
		$.post(url,args,function(data){
			var success=data.success;
			if(success){
				alert("成功修改！");
			}else{
				alert("未能成功修改！");
			}
		},"JSON");//语法：$.post(url,data,callback,type);
	});
	
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
		if(val<1||val>parseInt("${wordpage.getTotalPageNumber()}")){
			alert("输入的页码超出范围");
			$(this).val("");
			return;
		}
		//实现跳转
		var href="queryServlet?method=getWordPage&pageNo="+pageNo+"&wordSymbol="+wordSymbol;
		window.location.href=href;
	})
});
</script>
</head>
<body>
<center>
<br>

<span class="title">
<c:if test="${wordSymbol==-1 }">全部敏感词</c:if>
<c:if test="${wordSymbol==0 }">已停用敏感词</c:if>
<c:if test="${wordSymbol==1 }">已启用敏感词</c:if>
</span>

<br>
<br>

<button type="button" id="newword" class="btn btn-default" data-toggle="collapse" data-target="#new">新增敏感词</button>
<br><br>
<div id="new" class="collapse">
	<form action="wordServlet?method=insertWord&pageNo=${wordpage.pageNo}" method="post" class="form-inline" role="form">
	新增词汇：<input type="text" name="newword" id="text" placeholder="输入要新增的敏感词" class="form-control">
	<input type="radio" name="level" value="1">1级&nbsp;
	<input type="radio" name="level" value="2">2级&nbsp;
	<input type="radio" name="level" value="3">3级&nbsp;
	<input type="radio" name="level" value="4">4级&nbsp; 
	<input type="radio" name="level" value="5">5级&nbsp; 
	<!-- <select class="form-control">
			<option value="1" name="level">1级敏感词</option>
			<option value="2" name="level">2级敏感词</option>
			<option value="3" name="level">3级敏感词</option>
			<option value="4" name="level">4级敏感词</option>
			<option value="5" name="level">5级敏感词</option>
	</select> -->
	<input type="submit" value="提交" id="submit" class="btn btn-primary">
	
	</form>	
</div>

<form action="queryServlet?method=getOneWord" class="form-inline" role="form" method="post">
	<div class="form-group">
		<input type="text" class="form-control" id="name" name="word" placeholder="输入要查询的敏感词">
	</div>
	<input type="submit" class="btn btn-default" value="查询">
</form>
<br>

<c:if test="${!empty wordpage }">
<table class="table table-bordered table-hover mytable">
<tr id="info">
<!--<td>编号</td>  -->
<td>敏感词</td>
<td>状态</td>
<td class="weihu">级别</td>
<td class="weihu">维护</td>
</tr>
	<c:forEach items="${wordpage.list }" var="wordItem">
		<tr>
		<!-- <td>${wordItem.id }</td> -->
		<td>${wordItem.word }</td>
		<td>
		<c:if test="${wordItem.wordSymbol==0 }"><label id="hasstoped">已停用</label></c:if>
		<c:if test="${wordItem.wordSymbol!=0 }"><label id="hasstarted">已启用</label></c:if>
		</td>
		<td>

		<select class="form-control selectlevel">
			<option value="${wordItem.wordLevel }" name="${wordItem.id }">${wordItem.wordLevel }级敏感词</option>
			<option value="1">1级敏感词</option>
			<option value="2">2级敏感词</option>
			<option value="3">3级敏感词</option>
			<option value="4">4级敏感词</option>
			<option value="5">5级敏感词</option>
			
		</select>
		</td>
		<!-- <td>${wordItem.wordLevel }级敏感词</td> -->
		<td>
			<a href="wordServlet?method=deleteWord&id=${wordItem.id }&pageNo=${wordpage.pageNo}&wordSymbol=${wordSymbol}" class="delete"><button class="btn btn-default">删除</button></a>&nbsp;
			<c:if test="${wordItem.wordSymbol==0 }">
				<a href="wordServlet?method=changeSymbol&id=${wordItem.id }&symbol=${wordItem.wordSymbol}&pageNo=${wordpage.pageNo}&wordSymbol=${wordSymbol}" class="start">
				<button class="btn btn-default" id="start">启用</button>
				</a>
			</c:if>
			<c:if test="${wordItem.wordSymbol!=0 }">
				<a href="wordServlet?method=changeSymbol&id=${wordItem.id }&symbol=${wordItem.wordSymbol}&pageNo=${wordpage.pageNo}&wordSymbol=${wordSymbol}" class="stop">
				<button class="btn btn-default" id="stop">停用</button>
				</a>
			</c:if>
		</td>
		</tr>
	</c:forEach>
</table>

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
<br>
<!-- 该隐藏域用于js获取wordSymbol，供跳转使用 -->
<input type="hidden" id="wordsymbol" value="${wordSymbol }"/>
</c:if>
<br>

<c:if test="${empty wordpage }">
	<c:if test="${!empty senword }">
		<table class="table table-bordered table-hover mytable">
	<tr id="info">
	<!--<td>编号</td>  -->
	<td>敏感词</td>
	<td>状态</td>
	<td class="weihu">级别</td>
	<td class="weihu">维护</td>
	</tr>
		<tr>
		<!-- <td>${wordItem.id }</td> -->
		<td>${senword.word }</td>
		<td>
		<c:if test="${senword.wordSymbol==0 }"><label id="hasstoped">已停用</label></c:if>
		<c:if test="${senword.wordSymbol!=0 }"><label id="hasstarted">已启用</label></c:if>
		</td>
		<td>

		<select class="form-control">
			<option value="${senword.wordLevel }" name="${senword.id }">${senword.wordLevel }级敏感词</option>
			<option value="1">1级敏感词</option>
			<option value="2">2级敏感词</option>
			<option value="3">3级敏感词</option>
			<option value="4">4级敏感词</option>
			<option value="5">5级敏感词</option>
		</select>
		</td>
		<!-- <td>${wordItem.wordLevel }级敏感词</td> -->
		<td>
			<a href="wordServlet?method=deleteWord&id=${senword.id }&wordSymbol=${wordSymbol}" class="delete"><button class="btn btn-default">删除</button></a>&nbsp;
			<c:if test="${senword.wordSymbol==0 }">
				<a href="wordServlet?method=changeSymbol&id=${senword.id }&symbol=${senword.wordSymbol}&pageSymbol=getOneWord" class="start">
				<button class="btn btn-default" id="start">启用</button>
				</a>
			</c:if>
			<c:if test="${senword.wordSymbol!=0 }">
				<a href="wordServlet?method=changeSymbol&id=${senword.id }&symbol=${senword.wordSymbol}&pageSymbol=getOneWord" class="stop">
				<button class="btn btn-default" id="stop">停用</button>
				</a>
			</c:if>
		</td>
		</tr>
</table>
	</c:if>

</c:if>
<br>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span6">
			<a href="upload.jsp">导入敏感词库
			<img src="image/import.png" class="image"/>
			</a>
		</div>
		<div class="span6">
			<a href="downloadServlet?method=downloadWords">导出敏感词库
			<img src="image/export.png" class="image"/>
			</a>
		</div>
		<div class="span6">
			<a href="wordServlet?method=deleteAllWords" id="deleteall">清空敏感词库
			<img src="image/deleteall.jpg" class="image"/>
			</a>
		</div>
	</div>
</div>
<br><br>
</center>
	
</body>
</html>