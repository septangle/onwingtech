<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->

<head>
<%@ include file="../inc/tag.jsp" %>
</head>
<body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">
	<div class="page-wrapper">
		<%@ include file="../inc/header.jsp" %>
		<!-- BEGIN HEADER & CONTENT DIVIDER -->
		<div class="clearfix"></div>
		<!-- END HEADER & CONTENT DIVIDER -->
		<!-- BEGIN CONTAINER -->
		<div class="page-container">
			<%@ include file="../inc/menu.jsp" %>
			<!-- BEGIN CONTENT -->
			<div class="page-content-wrapper">
				<!-- BEGIN CONTENT BODY -->
				<div class="page-content">
					<!-- BEGIN PAGE BAR -->
					<div class="page-bar">
						<ul class="page-breadcrumb">
							<li><a href="${ctx}/">主页</a><i class="fa fa-circle"></i></li>
							<li><span>Test</span></li>
						</ul>
					</div>
					<h1 class="page-title">Test</h1>
					<div class="page-content-wapper">
						<div class="page-table-search-wapper">
							<div class="portlet light bordered">
								<form action="" method="get" id="test-search-form">
									<table class="page-search-table">
										<tr>
											<td><label>编号：</label></td>
											<td><input class="form-control input-sm input-small input-inline" value="" placeholder="" name="parkTestDto.id"></td>
											<td><label>名称：</label></td>
											<td><input class="form-control input-sm input-small input-inline" placeholder="" name="parkTestDto.name" ></td>
											<td>
												<button type="button" class="reload-table btn sbold green min-btn">
													查询 <i class="fa fa-search"></i>
												</button>
												<button type="button" class="btn sbold red min-btn" onclick="$('#test-search-form').clear();">
													清空 <i class="fa fa-remove"></i>
												</button>
											</td>
										</tr>
										</table>
										
										<div class="page-search-more">
											<table class="page-search-table">
											<tr>
												<td><label>资产类型：</label></td>
												<td><select name="astTypeCd" class="bs-select form-control" data-width="145px">
														<option value="">--请选择--</option>
												</select></td>
												<td><label>状态：</label></td>
												<td><select name="rentalStatusCd" class="bs-select form-control" data-width="145px" >
														<option value="">--请选择--</option>
												</select></td>
												<td><label class="cc">起始日期:</label></td>
												<td>
													<div class="input-group input-medium date-picker input-daterange" data-date="2012-10-11" data-date-format="yyyy-mm-dd">
														<input name="startEffctiveFrom" value="" type="text" class="form-control"> 
														<span class="input-group-addon"> ~ </span> 
														<input name="endEffctiveFrom" value="" type="text" class="form-control">
													</div>
												</td>
											</tr>
											</table>
										</div>

									<div class="page-search-more">
										<table class="page-search-table">
											<tr>
												<td><label>租金范围：</label></td>
												<td>
													<div class="input-group input-medium input-daterange">
														<input type="text" class="form-control integer" name="startrentPerMonth" value=""> <span class="input-group-addon"> ~ </span> 
														<input type="text" class="form-control integer" name="endrentPerMonth" value="">
													</div>

												</td>
												<td><label>付费方式：</label></td>
												<td >
													<select class="bs-select form-control" data-width="145px" name="rentPayMethodCd">
													
													<option value="">--请选择--</option>
													</select></td>
												<td>
													<select class="bs-select form-control" data-width="145px" name="rentCalMethodCd">
													<option value="">--请选择--</option>
													</select>
												</td>
											</tr>
										</table>
									</div>
								</form>
							</div>
						</div>
						<div class="page-table-wapper">
							<!-- BEGIN EXAMPLE TABLE PORTLET-->
							<div class="portlet light bordered">
								<div class="portlet-body">
									<div class="table-toolbar">
										<div class="row">
											<div class="col-md-6">
												<div class="btn-group">
													<button id="addTestDataBtn" class="btn sbold green" data-toggle="modal">
														<i class="fa fa-plus"></i> 新增
													</button>
													<em></em>
													<button id="uploadfile" type="button" class="btn sbold green m10">
														<i class="fa fa-file-excel-o"></i> 导入
													</button>
												</div>
											</div>
											<div class="col-md-6">
													<div class="btn-group pull-right">
														<button type="button" class="btn green  btn-outline dropdown-toggle" data-toggle="dropdown">
															操作 <i class="fa fa-angle-down"></i>
														</button>
														<ul class="dropdown-menu pull-right">
															<li><a href="javascript:;" id="deleteTestDateBtn"> <i class="fa fa-remove"></i> 删除</a></li>
															<li><a href="javascript:;"> <i class="fa fa-print"></i> 打印
															</a></li>
															<li><a href="javascript:;"  onclick="exportexcl()"> <i class="fa fa-file-excel-o"></i> 导出
															</a></li>
														</ul>
													</div>
												</div>
										</div>
									</div>
									
									<div id="test-data-dt">
										<div class="dataTables_wrapper no-footer">
											<div class="table-scrollable auto min">
											<table class="data-table-min table table-striped table-bordered table-hover table-checkable order-column dataTable no-footer">
											</table>
											</div>
											<div class="dataTables-pageNav">
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- END EXAMPLE TABLE PORTLET-->
						</div>
					</div>
				</div>
				<!-- END CONTENT BODY -->
			</div>
			<!-- END CONTENT -->
		</div>
		<!-- END CONTAINER -->
		<%@ include file="../inc/footer.jsp" %>
	</div>
	
	
<!-- data-backdrop="false" -->
	<div id="test-data-save" class="modal fade" tabindex="-1" role="basic" aria-hidden="true">
		<div class="modal-dialog" style="width: 400px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h4 class="modal-title" id="title"></h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal validation" id="test-data-save-form">
						<div class="form-group">
							<label class="col-md-3 control-label" for="title">编号:</label>
							<div class="col-md-8">
								<input type="text" class="form-control validate[required]" value="" name="parkTestDto.id"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label" for="title">名称:</label>
							<div class="col-md-8">
								<input type="text" class="form-control validate[required]" value="" name="parkTestDto.name"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label" for="title">性别:</label>
							<div class="col-md-8">
								<select class="bs-select form-control bs-select-form  validate[required]" data-width="125px" name="parkTestDto.sex">
									<option value="">--请选择--</option>
									<option value="1">男</option>
									<option value="0">女</option>
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-outline sbold red" data-dismiss="modal">关 闭</button>
					<button type="button" class="btn btn-outline sbold blue" id="saveTestDateBtn">保 存</button>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>
<%@ include file="../inc/js.jsp" %>

<script>
var TestDataDt;
$(function() {
	TestDataDt = new DataTable({
		id:"#test-data-dt",
		url:conf.ctx + "/demo/parkTest/queryParkTest.do",
		params:$('#test-search-form').serializeArray(),
		dataName:"parkTestDtoList",
		selectOne:true,
		pageName:"parkTestDto.pageNum",
		pageSizeName:"parkTestDto.pageSize",
		pageInterval:10,
			columns:[
			 {field:"id",title:"",checkbox:true}
			,{field:"id",title:"编号",className:"center"}
			,{field:"name",title:"名称",className:"center",format:function(val,row,i){
				return '<a href="javascript:;" onclick="toUpdate('+row.id+')">'+val+'</a>';
			}}
			,{field:"sex",title:"性别",className:"center",format:function(val,row,i){
				return '<span class="label label-sm '+(val == 1?"label-success":"label-danger")+'"> '+(val == 1?"男":"女")+'</span>';
			}}
		]
	});
	TestDataDt.init();
	
	$(".reload-table").click(function(){
		TestDataDt.reload($('#test-search-form').serializeArray());
	});
	
	$("#addTestDataBtn").click(function(){
		$('#test-data-save').modal("show");
	});
	
	$("#saveTestDateBtn").click(function(){
		var $from = $("#test-data-save-form");
		 if($from.validationEngine('validate')){
	    	App.blockUI({
	            target: $from.find(".modal-content"),
	            animate: true
	        });
			$.ajax({
				url:'${ctx}/demo/parkTest/mergerParkTest.do',
				type:'post',
				dataType:'json',
				data:$from.serializeArray(),
				success:function(data){
					if(!data.error){
						$('#test-data-save').modal('hide');
						$from.clear();
						toastr["success"]("保存成功", "温馨提示");
						TestDataDt.reload($('#test-search-form').serializeArray());
					}else{
						toastr["error"](data.msg ? data.msg : "操作失败");
					}
					App.unblockUI($from.find(".modal-content"));
				}
				,error:function(e){
					toastr["error"]("连接服务器超时，请稍后重试");
					App.unblockUI($from.find(".modal-content"));
				}
			}); 
		 }
	});
	
	$("#deleteTestDateBtn").click(function(){
		var row = TestDataDt.getSelected();
		if(!row){
			toastr["error"]("请选择一行数据", "温馨提示");
			return;
		}
		Park.confirm({
			message:"确定要删除吗？",
			title:"温馨提示",
			callback:function(){
				$.ajax({
					type:'POST',
					url:'${ctx}/demo/parkTest/deleteParkTest.do',
					data: {"id":row.id},
					dataType:'json',
					success:function(data){
						if(!data.error){
							toastr["success"]("删除成功", "温馨提示");
							TestDataDt.reload($('#test-search-form').serializeArray());
						}else{
							toastr["error"](data.msg ? data.msg : "删除失败","温馨提示");
						}
						App.unblockUI('#test-data-save .modal-content');
					},error:function(e){
						toastr["error"]("连接服务器超时，请稍后重试","温馨提示");
						App.unblockUI('#test-data-save .modal-content');
					}
				});
			}
		});
	});
});


	
//修改
function toUpdate(id){
	App.blockUI({
       target: '#test-data-save .modal-content',
       animate: true
   	});
	$.ajax({
		type:'POST',
		url:'${ctx}/demo/parkTest/findParkTestById.do',
		data: {"parkTestDto.id":id},
		dataType:'json',
		success:function(data){
			if(!data.error){
				$("input[name='parkTestDto.id']").val(data.parkTestDto.id);
				$("input[name='parkTestDto.name']").val(data.parkTestDto.name);
				$("select[name='parkTestDto.sex']").selectpicker('val', data.parkTestDto.sex);
				$('#test-data-save').modal("show");
				TestDataDt.reload($('#test-search-form').serializeArray());
			}else{
				toastr["error"](data.msg ? data.msg : "获取信息失败","温馨提示");
			}
			App.unblockUI('#test-data-save .modal-content');
		},error:function(e){
			toastr["error"]("连接服务器超时，请稍后重试","温馨提示");
			App.unblockUI('#test-data-save .modal-content');
		}
	});
}


function exportexcl(){
	$('#test-search-form').attr("action","${ctx}/assets/tenant/export");
	$('#test-search-form').attr("method","post");
	$('#test-search-form').submit();
}
</script>
