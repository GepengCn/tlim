<%@ page import="com.itonglian.utils.XMLProperties" pageEncoding="utf-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.concurrent.ConcurrentHashMap" %>
<%@ page contentType="text/html; charset=utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
    </head>
<script>
</script>
<body>
    <h1>Openfire用户同步及数据库属性配置</h1>
<%
    ConcurrentHashMap<String,Object> concurrentHashMap = XMLProperties.getConcurrentHashMap();

    for(Map.Entry<String, Object> entry:concurrentHashMap.entrySet()){
        %>
        <div>
            <span class="propName"><%=entry.getKey() %></span>
            &nbsp;- &nbsp;
            <span class="propValue"><%=entry.getValue() %></span>
        </div>
<%
    }
%>
</body>

</html>