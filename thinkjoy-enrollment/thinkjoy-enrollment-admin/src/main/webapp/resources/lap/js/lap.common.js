/**
 * Created by JosephFourier on 2017/12/18.
 */
/* 绑定表格事件 */
$(document).on('click', '.lap-btn', function(e) {
    var index = '_' + $(this).prop('class').replace(/lap-btn|btn-|\s/gi, '')
        , that = this
        , id;
    if ($input = $(this).parents('td').find('input')) {
        id = $input.val();
        window[index]["call"](that, id);
    }
});

/* 绑定工具栏事件 */
$(document).on('click', '.lap-toolbar .btn', function() {
    var action = $(this).prop('class').replace(/btn|\s/gi, '');
    try {
        window[action + 'Action']['call'](this);
    } catch (e) {}
});



/* 复选框事件 */
$(document).on('click', '.lap-chk', function () {
   // $(this).children('label')
});

LAP = function() {};
LAP.util = {
    getScreenDim: function () {
        return [window.screen.width, window.screen.height];
    },

    getContentWidth: function() {
        return this.getScreenDim()[0] - 70 * 2 - 160;
    }
};

var ajaxIdx;
$( document ).ajaxSend(function() {
    var el =arguments[0].target.activeElement;
    var show = $(el).data('ajax') || false;
    if (show)
        ajaxIdx = $.ajaxShow({
            msg: '正在提交数据',
            time: 100000,
            scrollbar: false,
            shade: [.5, '#000'],
            offset: '100px'
        });
}).ajaxStop(function() {
    $.ajaxHide(ajaxIdx);
});
