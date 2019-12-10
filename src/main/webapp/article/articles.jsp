<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />


<script>
    $(function(){

        /*jqgrid表格*/
        $("#atctable").jqGrid({
            url:"${path}/article/queryByPage",
            editurl:"${path}/article/edit",
            datatype : "json",
            autowidth:true,  //自适应父容器
            height:'auto',
            rownumbers:true,
            styleUI:'Bootstrap', //使用BootStrap风格的样式
            rowNum : 2,
            rowList : [ 2,5,10, 20, 30 ],
            pager : '#atcpager',
            viewrecords:true,  //显示总条数
            colNames : [ 'Id', '名字', '内容', '状态', '上传时间','所属上师' ],
            colModel : [
                {name : 'id',width : 55,hidden:true},
                {name : 'title',editable:true,width : 90},
                {name : 'content',editable:true,width : 100,align : "center"},
                {name : 'status',width : 80,align : "center"},
                {name : 'createDate',width : 80,align : "right"},
                {name : 'guruid',editable:true,width : 80,align : "right"}
            ]
        });

        //增删改查操作
        $("#atctable").jqGrid('navGrid', '#atcpager',
            {edit : false,add : false,add : false,search:false,del : true,edittext:"编辑"}
        );
    });

</script>

<%--初始化一个面板--%>
<div class="panel panel-danger">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>文章信息</h2>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#"><b>文章信息</b></a></li>
    </ul>

    <div class="panel panel-body">
        <button type="button" id="btn1" class="btn btn-info" >查看文章</button>&emsp;
        <button type="button" id="btn2" class="btn btn-success" >添加文章</button>&emsp;
        <button type="button" id="btn3" class="btn btn-warning" >删除文章</button>&emsp;
    </div>

    <%--初始化表单--%>
    <table id="atctable" />

    <%--分页工具栏--%>
    <div id="atcpager" />

</div>
<%--初始化模态框--%>

<div id="myModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document" style="width: 730px">
        <div class="modal-content" >
            <%--模态框标题--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="gridSystemModalLabel">Modal title</h4>
            </div>
            <%--模态框内容--%>
            <div class="modal-body" >
                <form class="form-horizontal" id="articleFrom" >

                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1">标题</span>
                        <input id="title" name="title" type="text" class="form-control" aria-describedby="basic-addon1">
                    </div><br>
                    <div class="input-group">
                        <%--初始化富文本编辑器--%>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px;">

                        </textarea>
                    </div>
                </form>
            </div>
            <%--  模态框按钮  --%>
            <div class="modal-footer" id="modalFooter">
                <%--<button type="button" class="btn btn-default">提交</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>--%>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

