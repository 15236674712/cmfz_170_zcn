<%@ page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />

<%--<script type="text/javascript" src="https://cdn-hangzhou.goeasy.io/goeasy.js"></script>--%>
<%--<script type="text/javascript" src="${path}/js/goeasy.js"></script>--%>
<script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
<script>
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-df0a45499f274b2bae29ae50a6a12dc9", //替换为您的应用appkey
    });
    goEasy.subscribe({
        channel: "cmfz", //替换为您自己的channel
        onMessage: function (message) {
            alert("Channel:" + message.channel + " content:" + message.content);
        }
    });
</script>

