/**
 * Created by JosephFourier on 2017/11/24.
 */

(function($, layui) {
    var layer = layui.layer
    , laydate = layui.laydate;

    $.fn.tips = function(opts) {
        var options = {
            ori: 1,
            msg: 'try some tips',
            time: 3000,
            tipsMore: false
        };
        $.extend(!0, options, opts);

        var idStr = $(this).attr('id');

        if (idStr == 'undefined') return;

        layer.tips(options.msg, '#' + idStr, {
            tips: options.ori,
            time: options.time,
            tipsMore: options.tipsMore
        })
    };

    $.open = function (opts) {
        var options = {
            skin: 'common',
            offset: '10px',
            fixed: false,
            maxmin: false,
            area:['95%','95%'],
            type: 2
        };

        if (!(opts.area instanceof Array)) { opts.area = ['95%', opts.area]; }

        $.extend(!0, options, opts);
        layer.open(options);
    };

    $.ajaxShow = function(opt) {
        var options = {
            msg: 'loading...',
            time: 100000,
            scrollbar: false,
            shade: [.5, '#000'],
            icon: 16
        };

        $.extend(!0, options, opt);

        return layer.msg(options.msg, {
            icon: options.icon,
            shade: options.shade,
            scrollbar: options.scrollbar,
            offset: options.offset,
            time:options.time}
        );
    };

    $.ajaxHide = function(index) {
        layer.close(index);
    };

    $.closeAll = function() {
        console.log(parent);
        layer.closeAll.call(parent);
    };

    $.date = function(opts) {
        if (!opts.elem) { console.log('no element found'); return; }

        var options = {
            btns: ['confirm','clear'],
            theme: '#0593fd',
            type: 'year'
        };

        $.extend(!0, options, opts);

        if (! (opts.elem instanceof Array)) {
            laydate.render(options);
        } else {
            var elems = options.elem;
            delete options.elem;
            for (var i = 0; i < elems.length; ++i) {
                options.elem = elems[i];
                laydate.render(options);
            }
        }
    };

    $.msg = function(opts) {
        layer.msg(opts);
    };

    $.confirm = function(message, opts, fun) {
        var msg = {
            class: 'msg',
            content: 'are you sure to do this?'
        };
        var options = {
            skin: 'common confirm',
            title: '提示',
            btn: ['确认','取消'] //按钮
        };
        $.extend(!0, options, opts);
        $.extend(!0, msg, message);

        var m = '<div class="' + msg.class + '">' + msg.content + '</div>';
        layer.confirm(m, options, fun);
    }
})(jQuery, layui);
