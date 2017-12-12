var Upload = function() {

	var callBackFn;

	return {
		uploadCallback : function(data) {
			if (callBackFn) {
				callBackFn(data);
			}
			setTimeout(function() {
				bootbox.hideAll();
			}, 100);
		},
		open : function(options) {
			var type = options.type;
			var count = options.count;
			var callback = options.callback;
			var title = options.title != null ? options.title : '文件上传';
			var url = options.url != null ? options.url : '';
			var preurl = options.preurl != null ? options.preurl : '';
			var auto = options.auto != null ? options.auto : true;
			var url = conf.ctx + '/upload/upload' + type + '?num=' + count
					+ '&view=' + type + "&auto=" + auto + "&url=" + url + "&preurl=" + preurl;

			callBackFn = callback;

			bootbox.dialog({
				className : 'uploadDialog',
				message : "<div><iframe id='editframe' style='width:100%;height:280px;border:0;' src='"
						+ url + "' /></div>",
				title : title,
				onEscape : function() {
	
				},
				buttons : {
					// success : {
					// label : "确 定",
					// className : "green",
					// callback : function() {
					// var res = eval(dialog_click);
					// return res;
					// }
					// },
					danger : {
						label : " 关  闭 ",
						className : "red",
						callback : function() {
							return true;
						}
					}
				}
			});
		}
	}
}();