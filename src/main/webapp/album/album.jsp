<%@ page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script>
    $(function(){

        $("#abtable").jqGrid({
            url : '${path}/album/album.json',
            datatype : "json",
            rowNum : 8,
            rowList : [ 8, 10, 20, 30 ],
            pager : '#abpage',
            sortname : 'id',
            viewrecords : true,
            sortorder : "desc",
            autowidth:true,
            height:"auto",
            multiselect : false,
            styleUI:"Bootstrap",
            colNames : [ 'Id', '名字', '作者', '封面','集数', '发布时间' ],
            colModel : [
                {name : 'id',index : 'id',  width : 55},
                {name : 'title',index : 'invdate',width : 90},
                {name : 'author',index : 'name',width : 100},
                {name : 'cover_img',index : 'amount',width : 80,align : "right",edittype:"file",
                    editoptions:{enctype:"multipart/from-data"}
                },
                {name : 'count',index : 'tax',width : 80,align : "right"},
                {name : 'pub_date',index : 'total',width : 80,align : "right"}
            ],
            subGrid : true, //开启子表格
            //子表格的id；当子表格展开的时候，在主表格中会创建一个div元素用来容纳子表格，subgrid_id就是这个div的id
            //2.子表格容器的id和需要展开子表格的行id
            subGridRowExpanded : function(subgrid_id, row_id) {

                addSubGrid(subgrid_id, row_id);
            },

            });

        $("#abtable").jqGrid('navGrid', '#abpage',
            {add : false,edit : false,del : false},
            {},
            {
                closeAfterAdd:true,
                afterSubmit:function(data){
                    $.ajaxFileUpload({
                        url:"${path}/album/uploadAlbum",
                        type:"post",
                        datatype:"json",
                        data:{id:data.responseText},
                        success:function(){

                            //刷新页面
                            $("#abtable").trigger("reloadGrid");

                        }
                    });

                    return "hello";
                }
            }
         );

        function addSubGrid(subgridId, rowId){

            var subgridTableId = subgridId + "table";
            var pagerId = subgridId+"pager";

            $("#" + subgridId).html("<table id='"+subgridTableId+"' /><div id='"+pagerId+"' />");

            $("#" + subgridTableId).jqGrid({
                url : "${path}/album/chapter.json",
                editurl:"${path}/chapter/edit",
                pager : "#"+pagerId,
                datatype : "json",
                rowNum : 8,
                rowList : [ 8, 10, 20, 30 ],
                pager : '#'+pagerId,
                sortname : 'id',
                viewrecords : true,
                sortorder : "desc",
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
                    {name : "up_date",index : "total",width : 70,align : "right"},
                    {name : "url",align : "center",
                        formatter:function(cellVale){
                            return "<a href='#' onclick='playerChapter(\""+cellVale+"\")' ><span class='glyphicon glyphicon-play-circle' /></a>&nbsp;&emsp;&emsp;" +
                                "<a href='#' onclick='downloadChapter(\""+cellVale+"\")' ><span class='glyphicon glyphicon-download' /></a>";
                        }
                    }
                ]
            });

            $("#" + subgridTableId).jqGrid('navGrid',"#" + pagerId,
                {edit : false,add : true,del : false},
                {},
                {
                    closeAfterAdd:true,
                    afterSubmit:function(data){
                        $.ajaxFileUpload({
                            url:"${path}/chapter/uploadChapter",
                            type:"post",
                            datatype:"json",
                            fileElementId:"url",
                            data:{id:data.responseText},
                            success:function(){
                                //刷新页面
                                $("#abtable").trigger("reloadGrid");
                            }
                        });
                        return "hello";
                    }
                }
            );
        }
    })

    //播放
    function playerChapter(url){
        $("#playAudioDiv").modal("show");
        $("#playAudioId").attr("src","${path}/upload/audio/"+url);
    }

    //下载
    function downloadChapter(url){
        location.href="${path}/chapter/downloadAudio?url="+url;
    }

</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>专辑信息</h2>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#">专辑信息</a></li>
    </ul>

    <%--初始化表单--%>
    <table id="abtable" />

    <%--分页工具栏--%>
    <div id="abpage" />

    <%--在线播放--%>
    <div id="playAudioDiv" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <audio id="playAudioId" src="" controls></audio>
        </div>
    </div>
</div>