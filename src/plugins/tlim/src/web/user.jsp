<%@ page import="com.itonglian.entity.User" pageEncoding="utf-8"%>
<%@ page import="com.itonglian.utils.UserCacheManager" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.concurrent.ConcurrentHashMap" %>
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

<h4>缓存用户<span class="label label-default">tlim</span></h4>
<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th>用户id</th>
        <th>用户名</th>
    </tr>
    </thead>
    <%
        ConcurrentHashMap<String, User> userConcurrentHashMap = UserCacheManager.findAll();

        for(Map.Entry<String, User> entry:userConcurrentHashMap.entrySet()){
    %>
    <tbody>
    <tr class="active">
        <td><%=entry.getValue().getUser_id() %></td>
        <td><%=entry.getValue().getUser_name() %></td>
    </tr>
    </tbody>

    <%
        }
    %>
</table>
</body>
</html>
</body>

</html>