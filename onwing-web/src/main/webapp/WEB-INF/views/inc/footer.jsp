<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!-- BEGIN FOOTER -->
 <div class="page-footer" >
     <div class="page-footer-inner"> Copyright ©上海天地汇供应链管理有限公司</div>
 </div>
 <!-- END FOOTER -->
 
 <div id="setting-user-password" class="modal fade" tabindex="-1" role="basic" aria-hidden="true">
	<div class="modal-dialog" style="width: 400px;margin-top:8%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">修改密码</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal validation">
					<div class="form-group">
						<label class="col-md-3 control-label" for="title">旧&nbsp;&nbsp;密&nbsp;&nbsp;码:</label>
						<div class="col-md-8">
							<input name="oldpwd" type="password" class="form-control validate[required,minSize[6]]" value="" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label" for="title">新&nbsp;&nbsp;密&nbsp;&nbsp;码:</label>
						<div class="col-md-8">
							<input name="newpwd" type="password" class="form-control validate[required,minSize[6]]" value="" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label" for="title">重复密码:</label>
						<div class="col-md-8">
							<input name="repwd" type="password" class="form-control validate[required,minSize[6]]" value="" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-outline sbold red" data-dismiss="modal">关 闭</button>
				<button type="button" class="btn btn-outline sbold blue" onclick="User.settingPassSave()">保 存</button>
			</div>
		</div>
	</div>
</div>