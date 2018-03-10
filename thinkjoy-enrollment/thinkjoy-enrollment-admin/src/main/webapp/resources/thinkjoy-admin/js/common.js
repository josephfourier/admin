$(function() {
	// Waves初始化
	Waves.displayEffect();
	// 数据表格动态高度
	$(window).resize(function () {
		$('#table').bootstrapTable('resetView', {
			height: getHeight()
		});
	});
	// 设置input特效
	$(document).on('focus', 'input[type="text"]', function() {
		$(this).parent().find('label').addClass('active');
	}).on('blur', 'input[type="text"]', function() {
		if ($(this).val() == '') {
			$(this).parent().find('label').removeClass('active');
		}
	});
	// select2初始化
	$('select').select2();
});
// 动态高度
function getHeight() {
	return $(window).height() - 20;
}
// 数据表格展开内容
function detailFormatter(index, row) {
	var html = [];
	$.each(row, function (key, value) {
		html.push('<p><b>' + key + ':</b> ' + value + '</p>');
	});
	return html.join('');
}
// 初始化input特效
function initMaterialInput() {
	$('form input[type="text"]').each(function () {
		if ($(this).val() != '') {
			$(this).parent().find('label').addClass('active');
		}
	});
}




//格式化学制
function schoolFormatter(value, row, index){
	if (value == 101) {
		return '3年';
	}
	if (value == 102) {
		return '5年';
	}
	return '-';
}
//格式化专业性质
function specltyFormatter(value, row, index){
	if (value == 1001) {
		return '中职';
	}
	if (value == 1002) {
		return '高职';
	}
	if (value == 1003) {
		return '大专';
	}
	return '-';
}
//格式化招生对象
function tarFormatter(value, row, index){
	if (value == 110) {
		return '小学毕业';
	}
	if (value == 111) {
		return '初中毕业';
	}
	if (value == 112) {
		return '中职毕业';
	}
	if (value == 113) {
		return '高中毕业';
	}
	return '-';
}
//格式化专业类别
function typeFormatter(value, row, index){
	if (value == 01) {
		return '哲学';
	}
	if (value == 02) {
		return '经济学';
	}
	if (value == 03) {
		return '法学';
	}
	if (value == 04) {
		return '教育学';
	}
	if (value == 05) {
		return '文学';
	}
	if (value == 06) {
		return '历史学';
	}
	if (value == 07) {
		return '理学';
	}
	if (value == 08) {
		return '工学';
	}
	if (value == 09) {
		return '农学';
	}
	if (value == 10) {
		return '医学';
	}
	if (value == 11) {
		return '军事学';
	}
	if (value == 12) {
		return '管理学';
	}
	return '-';
}