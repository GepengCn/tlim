<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.itonglian.view.MessageStatistics" %>
<html>
<head>
    <title>My Plugin Page</title>

    <meta name="pageID" content="myPluginPage"/>
</head>
<body>
Body here!
<ul>
<%
    List<String> list = MessageStatistics.queryList();
    for(int i=list.size()-1;i>0;i--){
        String msg = list.get(i);
        %>
        <li>
            <%=msg %>
        </li>
<%

    }
%>
</ul>
<script>
    setInterval(function () {
        window.location=window.location;
    },5000);

</script>
</body>
</html>