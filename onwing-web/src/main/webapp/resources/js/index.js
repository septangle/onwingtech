$(function() {
	if (document.getElementById("report_1")) {
		var data = [0,0];
		reportDk(data,"report_1");
	}
	
	if (document.getElementById("report_2")) {
		var data = {};
		data.datax = [];
		data.data = [];
		data.max = [];
		data.data2 = [];
		reportParkingLL(data, "report_2");
	}
	
	
	var url = conf.ctx + "/resource/data/test.json";
	if( (10*Math.random()) >= 7){
		url = "error";
	}
	
	$.ajax({
		url : url,
		dataType : "json",
		success : function(data) {
			datatest = data;
			//图1
			if (document.getElementById("report_1")) {
				var data = ["150","100"];
				reportDk(data,"report_1");
			}
			//图2
			if (document.getElementById("report_2")) {
				var data = {};
				data.datax = ["10.01","10.02","10.03","10.04","10.05","10.06","10.07"];
				data.data = [21,22,23,24,25,26,27];
				data.max = 17 * 2;
				data.data2 = [10,11,12,13,14,15,16];
				reportParkingLL(data, "report_2");
			}
			//统计
			$(".font-green-sharp span").attr("data-value",250).counterUp();
			$(".font-red-haze span").attr("data-value",150).counterUp();
			$(".font-blue-sharp span").attr("data-value",200).counterUp();
			$(".font-purple-soft span").attr("data-value",200).counterUp();
		},
		error : function() {
			toastr["error"]("加载主页数据失败！", "温馨提示");
		}
	});
});

function reportParkingLL(data, id) {
	var option = {
		title : {
			text : '停车日流量统计',
			textStyle : {
				color : '#B8BECA',
				fontSize : 16,
			}
		},
		color : [ '#ff7f50', '#87cefa' ],
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : [ '入场', '出场' ]
		},
		toolbox : {
			show : true,
			feature : {
				// mark : {show: true},
				// dataView : {show: true, readOnly: false},
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			data : data["datax"]
		} ],
		yAxis : [ {
			max : data.max,
			type : 'value'
		} ],
		series : [ {
			name : '入场',
			type : 'line',
			itemStyle : {
				normal : {
					label : {
						show : true,
						position : 'top',
						formatter : '{c}'
					}
				}
			},
			data : data["data"]

		}, {
			name : '出场',
			type : 'line',
			itemStyle : {
				normal : {
					label : {
						show : true,
						position : 'top',
						formatter : '{c}'
					}
				}
			},
			data : data["data2"]
		} ]
	}
	var dom = document.getElementById(id);
	var myChart = echarts.init(dom);
	myChart.setOption(option, true);
}

function reportDk(data,id) {
	var dom = document.getElementById(id);
	var myChart = echarts.init(dom);
	option = {
		color : [ '#87cefa', '#ff7f50' ],
		title : {
			text : "档口出租情况统计",
			textStyle : {
				color : '#B8BECA',
				fontSize : 16,
			}
		},
		tooltip : {
			trigger : 'item',
			formatter : "{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			left : 'right',
			data : [ "出租", "空租" ]
		},
		series : [ {
			type : 'pie',
			radius : [ 0, '70%' ],
			data : [ {
				value : data[0],
				name : '出租'
			}, {
				value : data[1],
				name : '空租'
			} ],
			itemStyle : {
				emphasis : {
					shadowBlur : 10,
					shadowOffsetX : 0,
					shadowColor : 'rgba(0, 0, 0, 0.5)',
					formatter : "{b} : {c} ({d}%)"
				},
				normal : {
					label : {
						show : true,
						position : 'top',
						formatter : "{b} : ({d}%)"
					}
				}
			}
		} ]
	};
	myChart.setOption(option, true);
}