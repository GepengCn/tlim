<%@ page import="com.itonglian.utils.XMLProperties" pageEncoding="utf-8"%>
<%@ page import="java.util.Iterator" %>
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
    Iterator<XMLProperties.Property> iterator = XMLProperties.getProperties().iterator();

    while(iterator.hasNext()){
        XMLProperties.Property property = iterator.next();
        %>
        <div>
            <span class="propName"><%=property.getName() %></span>
            &nbsp;- &nbsp;
            <span class="propValue"><%=property.getValue() %></span>
        </div>
<%
    }
%>
</body>

</html>