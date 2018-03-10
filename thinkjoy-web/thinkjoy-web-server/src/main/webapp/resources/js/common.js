
/* 检查是否支持*/
function isSupportPlaceholder() {
	return 'placeholder' in document.createElement('input');
}