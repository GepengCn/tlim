<%@ page import="com.itonglian.utils.XMLProperties" pageEncoding="utf-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.concurrent.ConcurrentHashMap" %>
<%@ page contentType="text/html; charset=utf-8"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="./css/bootstrap.min.css" rel="stylesheet">
    </head>
<script>
</script>
<body>
    <h1>Openfire用户同步及数据库属性配置</h1>
<%
    ConcurrentHashMap<String,Object> concurrentHashMap = XMLProperties.getConcurrentHashMap();

    for(Map.Entry<String, Object> entry:concurrentHashMap.entrySet()){
        %>
        <table class="table table-bordered table-hover">
            <tr class="info">
                <td class="col-md-4 text-left"><%=entry.getKey() %></td>
                <td class="col-md-8 text-left"><%=entry.getValue() %></td>
            </tr>
        </table>
<%
    }
%>
    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="./js/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="./js/bootstrap.min.js"></script>
</body>
</html>
</body>

</html>