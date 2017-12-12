<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- 
	****************************************
	****************************************
	****************************************
	!!!!!!!!!!!!这是一个空白的模版!!!!!!!!!!!
    ****************************************
    ****************************************
    ****************************************
-->

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head><#include "/inc/tag.htm" />
</head>
<body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">
	<div class="page-wrapper">
		<#include "/inc/header.htm" />
		<div class="clearfix"></div>
		<div class="page-container">
			<#include "/inc/menu.htm" />
			<div class="page-content-wrapper">
				<!-- BEGIN CONTENT BODY -->
				<div class="page-content">
					<div class="page-bar">
						<ul class="page-breadcrumb">
							<li><a href="${ctx}/">主页</a><i class="fa fa-circle"></i></li>
						</ul>
					</div>
					
					<h1 class="page-title">Template</h1>
					
					<div class="portlet light bordered btn-outline">
						这是一个空白的模版
						<br/><br/><br/><br/><br/><br/>
						<br/><br/><br/><br/><br/><br/>
					</div>
					
				</div>
				<!-- END CONTENT BODY -->
			</div>
			<#include "/inc/footer.htm" />
		</div>
</body>
</html>
<#include "/inc/js.htm" />
