<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <script src="${path}/js/jquery.min.js"></script>
        <!-- 引入 ECharts 文件 -->
        <script src="${path}/js/echarts.min.js"></script>
        <script type="text/javascript">
            $(function(){

                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));

                $.get("${path}/test/user.json",function(data){

                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '用户注册量展示', //标题
                            link:"${path}/main/main.jsp",
                            target:"self",
                            subtext:"用户信息"
                        },
                        tooltip: {},  //鼠标的提示
                        legend: {
                            // show:false,  是否展示 选项
                            data:['小男孩',"小女孩"]  //选项
                        },
                        xAxis: {
                            data: data.month  //横坐标
                        },
                        yAxis: {},  //纵坐标   自适应
                        series: [{
                            name: '小男孩',
                            type: 'line',
                            data: data.boys
                        },{
                            name: '小女孩',
                            type: 'bar',
                            data: data.girls
                        }]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);

                },"json");

            });
        </script>
    </head>
    <body>
        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
        <div id="main" style="width: 600px;height:400px;"></div>

    </body>
</html>