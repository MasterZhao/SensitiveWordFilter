<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
 <%@include file="common.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入敏感词</title>
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
		width:60%;	
		}
	.panel-heading{
			height: 50px;
		}
	.import{
		font-size: 20px;
	}	
</style>
<script type="text/javascript">
</script>
</head>
<body>
<center>
<span id="title">导入敏感词</span>
<br>
<div class="import">
（支持xls和xlsx格式）
<br><br>
<form action="uploadServlet" method="post" enctype="multipart/form-data" >
   选择需要上传的本地文件<input type="file" name="upfile" class="btn btn-default">
  <input type="submit" value="上传" class="btn btn-default">
</form>
</div>

<br>

</center>
</body>
</html>