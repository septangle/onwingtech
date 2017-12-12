 <%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <!-- BEGIN SIDEBAR -->
 <div class="page-sidebar-wrapper">
     <!-- BEGIN SIDEBAR -->
     <div class="page-sidebar navbar-collapse collapse">
         <!-- BEGIN SIDEBAR MENU -->
         <ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200" style="padding-top: 20px">
             <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
             <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
             <li class="sidebar-toggler-wrapper hide">
                 <div class="sidebar-toggler">
                     <span></span>
                 </div>
             </li>
             <!-- END SIDEBAR TOGGLER BUTTON -->
             <li class="sidebar-search-wrapper">
                 <!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
                 <form class="sidebar-search" action="${ctx}/estate/contract" method="get">
                     <a href="javascript:;" class="remove">
                         <i class="icon-close"></i>
                     </a>
                     <div class="input-group">
                         <input type="text" name="contractNo" class="form-control" placeholder="Search">
                         <span class="input-group-btn">
                             <a href="javascript:;" class="btn submit">
                                 <i class="icon-magnifier"></i>
                             </a>
                         </span>
                     </div>
                 </form>
                 <!-- END RESPONSIVE QUICK SEARCH FORM -->
             </li>
             <li class="nav-item start ${(module == '')?'active open':''} ">
                 <a href="${ctx}" class="nav-link nav-toggle">
                     <i class="icon-home"></i>
                     <span class="title">主页</span>
                     <span class="arrow"></span>
                 </a>
                   </li>
					${module}
                   <li class="nav-item ${(module == 'demo')?'active open':''} ">
                       <a href="javascript:;" class="nav-link nav-toggle">
                           <i class="fa fa-cubes"></i> 
                           <span class="title">框架案例</span>
                           <span class="arrow"></span>
                       </a>
                       <ul class="sub-menu">
                           <li class="nav-item  ${(nav == 'login')?'active open':''}">
                               <a href="${ctx}/login" target="_blank" class="nav-link ">
                                   <span class="title">login</span>
                               </a>
                           </li>
                           <li class="nav-item  ${(nav == 'parkTest')?'active open':''}">
                               <a href="${ctx}/demo/parkTest.htm" class="nav-link ">
                                   <span class="title">parkTest</span>
                               </a>
                           </li>
                           <li class="nav-item  ${(nav == 'template')?'active open':''}">
                               <a href="${ctx}/demo/template.htm" class="nav-link ">
                                   <span class="title">template</span>
                               </a>
                           </li>
                       </ul>
                   </li>
                   

                   <li class="nav-item ${(module == 'component')?'active open':''}">
                       <a href="javascript:;" class="nav-link nav-toggle">
                           <i class="icon-bar-chart"></i>
                           <span class="title">组件API</span>
                           <span class="arrow"></span>
                       </a>
                       <ul class="sub-menu">
                      	 <li class="nav-item  ${(nav == 'table')?'active open':''}">
                               <a href="${ctx}/component/table.htm" class="nav-link ">
                                   <span class="title">table</span>
                               </a>
                           </li>
                           <li class="nav-item  ${(nav == 'form')?'active open':''}">
                               <a href="${ctx}/component/form.htm" class="nav-link ">
                                   <span class="title">form</span>
                               </a>
                           </li>
                           <li class="nav-item  ${(nav == 'dialog')?'active open':''}">
                               <a href="${ctx}/component/dialog.htm" class="nav-link ">
                                   <span class="title">dialog</span>
                               </a>
                           </li>
                            <li class="nav-item  ${(nav == 'chart')?'active open':''}">
                               <a href="${ctx}/component/chart.htm" class="nav-link ">
                                   <span class="title">chart</span>
                               </a>
                           </li>
                       </ul>
                   </li>
                   
                   <li class="nav-item ${(module == 'utils')?'active open':''}">
                       <a href="javascript:;" class="nav-link nav-toggle">
                           <i class="icon-settings"></i>
                           <span class="title">扩展API</span>
                           <span class="arrow"></span>
                       </a>
                        <ul class="sub-menu">
                        	<li class="nav-item  ${(nav == 'sms')?'active open':''}">
	                            <a href="${ctx}/utils/sms.htm" class="nav-link ">
	                                <span class="title">utils-sms</span>
	                            </a>
	                        </li>
	                       <li class="nav-item  ${(nav == 'date')?'active open':''}">
	                            <a href="${ctx}/utils/date.htm" class="nav-link ">
	                                <span class="title">utils-date</span>
	                            </a>
	                        </li>
	                        <li class="nav-item  ${(nav == 'cookie')?'active open':''}">
	                            <a href="${ctx}/utils/cookie.htm" class="nav-link ">
	                                <span class="title">jquery-cookie</span>
	                            </a>
	                        </li>
	                        <li class="nav-item ${(nav == 'json')?'active open':''}">
	                            <a href="${ctx}/utils/json.htm" class="nav-link ">
	                                <span class="title">jquery-json</span>
	                            </a>
	                        </li>
                        </ul>
                   </li>
               </ul>
               <!-- END SIDEBAR MENU -->
         <!-- END SIDEBAR MENU -->
     </div>
     <!-- END SIDEBAR -->
 </div>
 <!-- END SIDEBAR -->
