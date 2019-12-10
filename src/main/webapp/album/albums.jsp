<%@ page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script>
    $(function(){

        $("#abTable").jqGrid({
            url : '${path}/album/queryByPage',
            datatype : "json",
            rowNum : 8,
            rowList : [ 8, 10, 20, 30 ],
            pager : '#abpage',
            viewrecords : true,
            autowidth:true,
            height:"auto",
            multiselect : false,
            styleUI:"Bootstrap",
            colNames : [ 'Id', '名字', '作者', '封面','集数', '发布时间' ],
            colModel : [
                {name : 'id',index : 'id',  width : 55},
                {name : 'title',index : 'invdate',width : 90},
                {name : 'author',index : 'name',width : 100},
                {name : 'cover',index : 'amount',width : 80,align : "right",edittype:"file",
                    formatter:function(cellvalue){
                        return "<img src='${path}/upload/photo/"+cellvalue+"' style='width:180px;height:80px'/>";
                    }
                },
                {name : 'count',index : 'tax',width : 80,align : "right"},
                {name : 'publishDate',index : 'total',width : 80,align : "right"}
            ],
            subGrid : true, //开启子表格
            subGridRowExpanded : function(subgrid_id, row_id) {
                addSubGrid(subgrid_id,row_id)
            }
        });

        $("#abTable").jqGrid('navGrid', '#abPage', {edit : true,add : true,del : true,addtext:"添加",edittext:"修改",deltext:"删除"},
            {},
            {
                closeAfterAdd:true,
                afterSubmit:function(data){
                    $.ajaxFileUpload({
                        url:"${path}/chapter/uploadAlbum",
                        type:"post",
                        data:{id:data.responseText},
                        fileElementId:"cover",
                        datatype:"json",
                        success:function(){
                            //刷新页面
                            $("#abTable").trigger("reloadGrid");
                        }
                    });
                    return "hehe";
                }
            }
        );

    });

    //子表格
    function addSubGrid(subgridId,rowId){

        var subgridTableId = subgridId + "table";
        var pagerId = subgridId+"pager";

        $("#" + subgridId).html("<table id='"+subgridTableId+"' /><div id='"+pagerId+"' />");

        $("#" + subgridTableId).jqGrid({
            url : "${path}/chapter/queryByPage?albumId="+rowId,
            editurl:"${path}/chapter/edit?albumId="+rowId,
            pager : "#"+pagerId,
            datatype : "json",
            rowNum : 8,
            rowList : [ 8, 10, 20, 30 ],
            viewrecords : true,
            autowidth:true,
            height:"auto",
            multiselect : false,
            styleUI:"Bootstrap",
            colNames : [ 'Id', '名字', '音频大小', '音频时长','上传时间',"操作" ],
            colModel : [
                {name : "id",  index : "num",width : 80,key : true},
                {name : "url",editable:true,index : "item",  width : 130,edittype:"file"},
                {name : "size",index : "qty",width : 70,align : "right"},
                {name : "duration",index : "unit",width : 70,align : "right"},
                {name : "createDate",index : "total",width : 70,align : "right"},
                {name : "url",align : "center",
                    formatter:function(cellVale){
                        return "<a href='#' onclick='playerChapter(\""+cellVale+"\")' ><span class='glyphicon glyphicon-play-circle' /></a>&nbsp;&emsp;&emsp;" +
                               "<a href='#' onclick='downloadChapter(\""+cellVale+"\")' ><span class='glyphicon glyphicon-download' /></a>";
                    }
                }
            ]
        });

        $("#" + subgridTableId).jqGrid('navGrid',"#" + pagerId, {edit : true,add : true,del : true,addtext:"添加",edittext:"修改",deltext:"删除"},
            {},
            {
                closeAfterAdd:true,
                    afterSubmit:function(data){
                $.ajaxFileUpload({
                    url:"${path}/chapter/uploadChapter",
                    type:"post",
                    data:{id:data.responseText},
                    fileElementId:"url",
                    datatype:"json",
                    success:function(){
                        //刷新页面
                        $("#abTable").trigger("reloadGrid");
                    }
                });
                return "hehe";
            }
        });
    }


    //下载
    function downloadChapter(fileName){
        location.href="${path}/chapter/downloadChapter?fileName="+fileName;
    }

    //在线播放
    function playerChapter(fileName){
        /*展示音频标签*/
        $("#playModal").modal("show");
        $("#playAudio").attr("src","${path}/upload/audio/"+fileName)
    }


</script>

<%--初始化一个面板--%>
<div class="panel panel-success">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>专辑信息</h2>
    </div>

    <%--选项卡--%>
    <div class="nav nav-tabs">
        <li class="active"><a href="">专辑信息</a></li>
    </div>

    <%--初始化表单--%>
    <table id="abTable" />

    <%--分页工具栏--%>
    <div id="abPage"/>


    <%--在线播放--%>
    <div id="playModal" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <%--（controls属性告诉浏览器要有基本播放控件）--%>
            <audio id="playAudio" src="" controls/>
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</div>