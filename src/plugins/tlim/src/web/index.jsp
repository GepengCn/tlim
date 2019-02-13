<%@ page import="com.itonglian.utils.XMLProperties" pageEncoding="utf-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.concurrent.ConcurrentHashMap" %>
<%@ page import="com.itonglian.utils.UserCacheManager" %>
<%@ page import="com.itonglian.entity.User" %>
<%@ page contentType="text/html; charset=utf-8"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="./css/spectre.min.css">
    </head>
<script>
</script>
<body>
    <h4>可配置属性<span class="label label-default">tlim</span></h4>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>属性名</th>
            <th>属性值</th>
        </tr>
        </thead>
<%
    ConcurrentHashMap<String,Object> concurrentHashMap = XMLProperties.getConcurrentHashMap();

    for(Map.Entry<String, Object> entry:concurrentHashMap.entrySet()){
        %>
            <tbody>
            <tr class="active">
                <td><%=entry.getKey() %></td>
                <td><%=entry.getValue() %></td>
            </tr>
            </tbody>

<%
    }
%>
    </table>

    <h4><a href="user.jsp">缓存用户</a></h4>

</body>
</html>
</body>

</html>