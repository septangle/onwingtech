String.prototype.len=function(){return this.replace(/[^\x00-\xff]/g,"aa").length;};
String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g, "");};

Array.prototype.clear = function() {
	this.length = 0;
}
Array.prototype.insertAt = function(index, obj) {
	this.splice(index, 0, obj);
}
Array.prototype.removeAt = function(index) {
	this.splice(index, 1);
}
Array.prototype.remove = function(obj) {
	var index = this.indexOf(obj);
	if (index >= 0) {
		this.removeAt(index);
	}
}
Array.prototype.get = function(name,val) {
	for(var i=0;i<this.length;i++){
		if(eval("this[i]."+name) == val){
			return this[i];
		}
	}
}
function toArray(arr,filed){
	var array = [];
	for(var i=0;i<arr.length;i++){
		var val = eval("arr[i]."+filed);
		array.push(val);
	}
	return array;
}

function toJoin(arr,filed){
	return toArray(arr,filed).join(",");
}

(function($) {
	$.fn.extend({
		clear : function() {
			$(this).find('input,select,textarea').not(
					':button, :submit, :reset, .notclear').val('').removeAttr(
					'checked').removeAttr('selected');

			$(this).find('.bs-select').selectpicker('val', '');

		}
	});
	
    //全局的ajax访问，处理ajax清求时sesion超时  
    $.ajaxSetup({   
//    	data:{aa:1},
//    	type:"post",
//      contentType:"application/x-www-form-urlencoded;charset=utf-8",   
        complete:function(XMLHttpRequest,textStatus){
        	//通过XMLHttpRequest取得响应头，sessionstatus，  
            var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); 
            if(sessionstatus=="timeout"){   
                  //如果超时就处理 ，指定要跳转的页面  
//                  window.location.replace("${path}/common/login.do");   
            }
        	if(textStatus == "success"){
        		
        	}
        	if(textStatus == "error"){
        		
        	}
          }   
      });
})(jQuery);

var Park = {
	initEvent : function() {
		/* JQuery 限制文本框只能输入数字 */
		$(".integer").unbind('keyup').bind('keyup', function() {
			var tmptxt = $(this).val();
			$(this).val(tmptxt.replace(/\D|^0/g, ''));
		}).unbind('paste').bind('paste', function() { // CTR+V事件处理
			var tmptxt = $(this).val();
			$(this).val(tmptxt.replace(/\D|^0/g, ''));
		}).css("ime-mode", "disabled"); // CSS设置输入法不可用

		/* JQuery 限制文本框只能输入数字和小数点 */
		$(".number").unbind('keyup').bind('keyup', function() {
			var tmptxt = $(this).val();
			tmptxt = tmptxt.replace(/[^0-9.]/g, '')
			$(this).val(tmptxt);
			if(tmptxt.split(".").length > 2){
				$(this).val(parseFloat(tmptxt));
			}
		}).unbind('paste').bind('paste', function() { // CTR+V事件处理
			var tmptxt = $(this).val();
			tmptxt = tmptxt.replace(/[^0-9.]/g, '')
			$(this).val(tmptxt);
			if(tmptxt.split(".").length > 2){
				$(this).val(parseFloat(tmptxt));
			}
		}).css("ime-mode", "disabled"); // CSS设置输入法不可用
	},
	initValidation : function() {
		if ($("form.validation").length) {
			// 自定义参数调用
			$('form.validation').each(function(i, item) {
				// $('#id').validationEngine('showPrompt','提示内容','load');
				var scroll = false;
				if ($(item).attr('scroll')) {
					scroll = true;
				}
				$(item).validationEngine('attach', {
					promptPosition : 'topLeft',
					scroll : scroll,
					maxErrorsPerField : true
				// ,addFailureCssClassToField:'validator'
				});
				var timer = -1;
				// 表单验证完成。返回参数说明：errorFound：表单验证不通过（true 或 false）
				$(item).bind('jqv.form.result', function(event, errorFound) {
					if (timer != -1) {
						clearTimeout(timer);
					}
					timer = setTimeout(function() {
						$(item).validationEngine('hideAll');
					}, 3000);
				});
				// 更新提示层的位置
				// setInterval(function(){
				// $(item).validationEngine('updatePromptsPosition')
				// },200);
			});
		}
	},
	initToastr : function() {
		toastr.options.closeButton = true;
		toastr.options.closeButton = true;
		toastr.options.timeOut = 3000;
		// toastr.options.extendedTimeOut = 1000;
	},
	initDialog : function() {
		var dialog_html = [];
		$('.simple-dialog').click(function() {
			var dialog_id = $(this).attr("dialog-id");
			var dialog_title = $(this).attr("dialog-title");
			var dialog_click = $(this).attr("dialog-click");
			var dialog_class = $(this).attr("dialog-class");
			var html = $(dialog_id).html();
			if (dialog_html[dialog_id]) {
				html = dialog_html[dialog_id];
				$(dialog_id).html("");
			} else {
				dialog_html[dialog_id] = html;
			}

			var dialog_class = $(dialog_id).attr("class")

			bootbox.dialog({
				className : dialog_class,
				message : html,
				title : dialog_title,
				onEscape : function() {

				},
				buttons : {
					success : {
						label : "确 定",
						className : "green",
						callback : function() {
							var res = eval(dialog_click);
							return res;
						}
					},
					danger : {
						label : "取 消",
						className : "red",
						callback : function() {
							return true;
						}
					}
				}
			});
		});
	},
	initDataTable : function() {
		if ($(".data-table .group-checkable").length) {
			$(".data-table .group-checkable").click(function() {
				var checked = $(this).prop('checked')
				if (checked) {
					$(".group-checkable-item").each(function() {
						$(this).prop('checked', true);
					});
				} else {
					$(".group-checkable-item").each(function() {
						$(this).prop('checked', false);
					});
				}
			});
		}
	},
	confirm:function(opts){
		bootbox.dialog({
			message : opts.message,
			title : opts.title?opts.title:"温馨提示",
			className : "confirm",
			onEscape : function() {

			},
			buttons : {
				success : {
					label : "确 定",
					className : "green",
					callback : function() {
						opts.callback();
					}
				},
				danger : {
					label : "取 消",
					className : "red",
					callback : function() {
						return true;
					}
				}
			}
		});
	}
}

$(function() {

	if (jQuery().datepicker) {
		$('.date-picker').datepicker({
			rtl : App.isRTL(),
			orientation : "left",
			autoclose : true,
			format:"yyyy-mm-dd"
		});
		// $('body').removeClass("modal-open"); // fix bug when inline picker is
		// used in modal
	}
	
	Park.initValidation();
	Park.initEvent();
	Park.initToastr();
	Park.initDialog();
	Park.initDataTable();

	$('.modal').on('hidden.bs.modal', function(e) {
		$(this).find("form").clear();
		$(this).find(".validation").validationEngine('hide');
	});
});

var SmsUtil = function(){
	
	function interval(opts){
		if(!opts.inittimer){
			opts.inittimer = 100;
		}
		if(!opts.params){
			opts.params = {};
		}
		
		var id = opts.btn_id;
		var key = opts.cookie_key;
		var label = opts.btn_label;
		var mobileId = opts.mobile_id;
		var params = opts.params;
		
		
		if(SmsUtil.intervalFlag){
			var cookietime = new Date(); 
			cookietime.setTime(cookietime.getTime() + (1 * 60 * 1000));
			var timer = $.cookie(key);
			if (timer == null || timer == '' || timer == 'NaN' || timer == 'undefined'  || timer <= 0) {
				$(id).val(label?label:"获取动态密码");
				$(id).html(label?label:"获取动态密码");
				$(id).unbind('click').bind('click', function(){
					sendTelDynamicCode(opts);
				});
				$(id).removeAttr('disabled');
				timer = 0;
				$.cookie(key, timer,{expires:cookietime});
			} else {
				$(id).val(timer + "秒后重新发送");
				$(id).html(timer + "秒后重新发送");
				$(id).unbind('click');
				$(id).attr('disabled', true);
				timer--;
				$.cookie(key, timer,{expires:cookietime});
			}
		}
	}
	
	function sendTelDynamicCode(opts){
		var id = opts.btn_id;
		var key = opts.cookie_key;
		var mobileId = opts.mobile_id;
		
		if(mobileId){
			var tel = $(mobileId).val();
			var reg = /^0?(13[0-9]|15[012356789]|18[012356789]|14[57]|17[02356789]|16[012356789])[0-9]{8}$/;
			if( tel=='' ){
				toastr["error"]("手机号码不能为空", "温馨提示");
				return;
			}
			if( !reg.test(tel) ){
				toastr["error"]("手机号码格式错误", "温馨提示");
				return;
			}
			opts.params.mobile = tel;
		}
		
		SmsUtil.intervalFlag = false;
		$(id).attr('disabled', true);
		var ladda = Ladda.create($(id)[0]);  
		ladda.start();
		
		$.ajax({
		   url:opts.url,
		   type:"post",
		   data:opts.params,
		   dataType:"json",
		   success:function(data){
			   ladda.stop();
			   SmsUtil.intervalFlag = true;
			   if(data.success){
				    var cookietime = new Date(); 
					cookietime.setTime(cookietime.getTime() + (1 * opts.inittimer * 1000));
					$(id).val(opts.inittimer + "秒后重新发送");
					$(id).html(opts.inittimer + "秒后重新发送");
					$(id).unbind('click');
					$(id).attr('disabled', true);
					$.cookie(key, opts.inittimer,{expires:cookietime});
					toastr["success"]("发送成功", "温馨提示");
				}else{
					toastr["error"]("发送短信失败", "温馨提示");
				}
		   },error:function(e){
			   toastr["error"]("发送短信失败", "温馨提示");
			   ladda.stop();
			   SmsUtil.intervalFlag = true;
		   }
		});
	}

	return {
		intervalFlag:true,
		sendSmsWait:function(opts) {
			interval(opts);
			setInterval(function() {
				interval(opts);
			}, '999');
		},
		resetSmsWait:function(opts) {
			var id = opts.btn_id;
			var key = opts.cookie_key;
			var label = opts.btn_label;
			$(id).val(label?label:"获取动态密码");
			$(id).html(label?label:"获取动态密码");
			$(id).unbind('click').bind('click', function(){
				sendTelDynamicCode(opts);
			});
			$(id).removeAttr('disabled');
			var cookietime = new Date(); 
			$.cookie(key, 0,{expires:cookietime});
		}
	};
}();

// var str = getTableSelected('.data-table');
// function getTableSelected(id) {
// var arr = [];
// $(id).find(".group-checkable-item").each(function(i, item) {
// if ($(item).prop('checked')) {
// arr.push($(item).val());
// }
// });
// return arr.join(",");
// }

// function checkAll(id,check) {
// var $table = $(id);
// if (check === true || check === false) {
// $table.find(".group-checkable").prop('checked', check);
// }
// var checked = $table.find(".group-checkable").prop('checked')
// if (checked) {
// $table.find(".group-checkable-item").each(function() {
// $(this).prop('checked', true);
// });
// } else {
// $table.find(".group-checkable-item").each(function() {
// $(this).prop('checked', false);
// });
// }
// }


function defval(str,def){
	return str?str:def;
}

var User = function (){
	function repwd() {
		$("#setting-user-password form").clear();
		$("#setting-user-password").modal('show');
	}
	function settingPassSave() {
		if($("#setting-user-password form").validationEngine('validate')){
			var param = {};
			param.oldpwd = $("#setting-user-password form input[name=oldpwd]").val();
			param.newpwd = $("#setting-user-password form input[name=newpwd]").val();
			param.repwd = $("#setting-user-password form input[name=repwd]").val();
			
			if(param.newpwd != param.repwd){
				$("#setting-user-password form input[name=repwd]").validationEngine('showPrompt','两次密码不一致','error');
				return;
			}
			
			App.blockUI({
	            target: '#setting-user-password .modal-content',
	            animate: true
	        });
			
			$.ajax({
			   url:conf.ctx +"/repwd",
			   type:"post",
			   data:param,
			   dataType:"json",
			   success:function(res){
				    if(res.success){
					  	toastr["success"]("修改密码成功", "温馨提示");
						$('#setting-user-password').modal('hide');
				    } else {
				    	toastr["error"](res.msg ? res.msg : "保存失败", "温馨提示");
				    }
		            App.unblockUI('#setting-user-password .modal-content');
			   },error:function(e){
					toastr["error"]("修改密码失败，请稍后在试！", "温馨提示");
		            App.unblockUI('#setting-user-password .modal-content');
			   }
			});
		}
	}
	
	function logout(){
		Park.confirm({
			message : "确定要退出本系统吗?",
			callback:function(){
				window.location = conf.ctx+"/login"
			}
		});
	}
	return {
		repwd:repwd,
		settingPassSave:settingPassSave,
		logout:logout
	}
}();
