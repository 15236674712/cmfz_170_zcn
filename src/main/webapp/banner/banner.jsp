<%@ page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />

<script>

    $(function(){
        $("#bnTable").jqGrid({
            url : "${path}/banner/queryByPage",
            editurl : "${path}/banner/edit",
            datatype : "json",
            rowNum : 3,
            rowList : [3,5, 10, 20, 30 ],
            styleUI:"Bootstrap",
            pager : '#bnPage',
            height:"auto",
            autowidth:true,
            viewrecords : true, //展示总条数
            colNames : [ 'Id', '名称', '图片', '描述', '状态','上传时间' ],
            colModel : [
                {name : 'id',index : 'id',width : 55},
                {name : 'title',index : 'invdate',editable:true,width : 90,align:"center"},
                {name : 'url',index : 'name',editable:true,width : 100,align:"center",edittype:"file",formatter:function(cellvalue){
                        return "<img src='${path}/upload/photo/"+cellvalue+"' style='width:180px;height:80px'/>";
                    }
                },
                {name : 'description',editable:true,index : 'amount',width : 80,align : "right"},
                {name : 'status',index : 'tax',width : 80,align : "right",align:"center",formatter:function(cellvalue,option,rowObject){
                        if(cellvalue==1){
                            return "<button class='btn btn-success' onclick='updateStatus(\""+rowObject.id+"\",\""+cellvalue+"\")' >不展示</button>";
                        }else{
                            return "<button class='btn btn-danger' onclick='updateStatus(\""+rowObject.id+"\",\""+cellvalue+"\")' >展示</button>";
                        }
                    }
                },
                {name : 'up_date',index : 'note',width : 150,sortable : false}
            ]
        });

        //增删改查操作
        $("#bnTable").jqGrid("navGrid","#bnPage",{edit : true,add : true,del : true,addtext:"添加",edittext:"修改",deltext:"删除"},
            {
                closeAfterEdit:true,
                beforeShowForm:function(obj){
                    obj.find("#url").attr("disabled",true);
                }
            },  //执行修改之后的额外操作
            {
                closeAfterAdd:true,
                afterSubmit:function(data){
                    $.ajaxFileUpload({
                        url:"${path}/banner/uploadBanners",
                        type:"post",
                        data:{id:data.responseText},
                        fileElementId:"url",
                        datatype:"json",
                        success:function(){
                            //刷新页面
                            //$("#bnTable").trigger("reloadGrid");
                            $("#bnTable").trigger("reloadGrid");
                        }
                    });
                    return "hehe";
                }
            },  //执行添加之后的额外操作
            {}   //执行删除之后的额外操作
        );
    });

    //修改状态
    function updateStatus(id,status){
        if(status==1){
            //展示状态
            $.ajax({
                url:"${path}/banner/edit",
                type:"post",
                datatype:"json",
                data:{"id":id,"status":"0","oper":"edit"},
                success:function(){
                    //刷新页面
                    $("#bnTable").trigger("reloadGrid");
                }
            });
        }else{
            //不展示状态
            $.ajax({
                url:"${path}/banner/edit",
                type:"post",
                datatype:"json",
                data:{"id":id,"status":"1","oper":"edit"},
                success:function(){
                    //刷新页面
                    $("#bnTable").trigger("reloadGrid");
                }
            });
        }
    }
</script>

<%--初始化一个面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>轮播图信息</h2>
    </div>

    <%--选项卡--%>
    <div class="nav nav-tabs">
        <li class="active"><a href="">轮播图信息</a></li>
    </div>

    <%--初始化表单--%>
    <table id="bnTable" />

    <%--分页工具栏--%>
    <div id="bnPage"/>
</div>