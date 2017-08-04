<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="m" uri="/auth-tags" %><!--引入我们的标签-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script src="../jquery-easyui/jquery-1.9.1.js" type="text/javascript" ></script>
<script src="../js/url.js" type="text/javascript" ></script>
<style type="text/css">
	p.normal {font-style:normal}
	p.italic {font-style:italic}
	p.oblique {font-style:italic;color:#9D9D9D}
</style>

</head>
<body>
<input type="button" style='font-size: 15px' value="发送消息" onclick="sendMessage()"></br>
-------------------------:</br>
           <m:auth privilege="del"> <!--我们自定义标签-->
                <li >
                  del
                </li>
           </m:auth>
            <m:auth privilege="query">
                <li >
                    query
                </li>
            </m:auth>
            <m:auth privilege="add">
                <li >
                   add
                </li>
            </m:auth>
            <m:auth privilege="004"> <!--我们自定义标签-->
                <li >
                  del 004
                </li>
           </m:auth>
            <m:auth privilege="001">
                <li >
                    query 001
                </li>
            </m:auth>
            <m:auth privilege="003">
                <li >
                   add 003
                </li>
            </m:auth>
        </ul> 
    </div>

</body>

</body>


</html>