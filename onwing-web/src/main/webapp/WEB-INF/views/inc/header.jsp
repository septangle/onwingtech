<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!-- BEGIN HEADER -->
 <div class="page-header navbar navbar-fixed-top" >
     <!-- BEGIN HEADER INNER -->
     <div class="page-header-inner ">
         <!-- BEGIN LOGO -->
         <div class="page-logo" style="width: auto">
         	 <div class="menu-toggler sidebar-toggler" style="float:left;">
                 <span></span>
             </div>
             <a href="${ctx}/" style="margin-left:20px;width: auto;">
                 <img src="${res}/images/logo1.png" height="25" alt="logo" class="logo-default2" />  <em></em>
                 <img src="${res}/images/logo2.png" class="logo-default2" style="margin-top:5px; " />
              </a>
         </div>
         <!-- END LOGO -->
         <!-- BEGIN RESPONSIVE MENU TOGGLER -->
         <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
             <span></span>
         </a>
         <!-- END RESPONSIVE MENU TOGGLER -->
         <!-- BEGIN TOP NAVIGATION MENU -->
         <div class="top-menu">
             <ul class="nav navbar-nav pull-right">
                 <!-- END TODO DROPDOWN -->
                 <!-- BEGIN USER LOGIN DROPDOWN -->
                 <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                 <li class="dropdown dropdown-user">
                     <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
<!--                          <img alt="" class="img-circle" src="${res}/assets/layouts/layout/img/avatar3_small.jpg" /> -->
                         <span class="username username-hide-on-mobile"> 天地汇会员 [李凯] </span>
                         <i class="fa fa-angle-down"></i>
                     </a>
                     <ul class="dropdown-menu dropdown-menu-default">
<!--                          <li> -->
<!--                              <a href="${ctx}/LogIn/login.htm" target="_blank"> -->
<!--                                  <i class="icon-user"></i> 我的信息 </a> -->
<!--                          </li> -->
                         <li>
                           <a href="javascript:;" onclick="User.repwd()">
                                 <i class="icon-user"></i> 修改密码 </a>
                         </li>
                         <li class="divider"> </li>
<!--                          <li> -->
<!--                              <a href="page_user_lock_1.html"> -->
<!--                                  <i class="icon-lock"></i> 锁 屏 </a> -->
<!--                          </li> -->
                         <li>
                             <a href="javascript:;" onclick="User.logout();">
                                 <i class="icon-key"></i> 退 出 </a>
                         </li>
                     </ul>
                 </li>
                 <!-- END USER LOGIN DROPDOWN -->
                 <!-- BEGIN QUICK SIDEBAR TOGGLER -->
                 <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                 <li class="dropdown dropdown-quick-sidebar-toggler">
                     <a href="javascript:;" onclick="User.logout();" class="dropdown-toggle">
                         <i class="icon-logout"></i>
                     </a>
                 </li>
                 <!-- END QUICK SIDEBAR TOGGLER -->
             </ul>
         </div>
         <!-- END TOP NAVIGATION MENU -->
     </div>
     <!-- END HEADER INNER -->
 </div>
 <!-- END HEADER -->
