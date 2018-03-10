/*
* 弹窗
* */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		define(['jquery'], factory);
	} else if (typeof module === "object" && module.exports) {
		module.exports = factory(required('jquery'));
	} else {
		factory(jQuery);
	}
})(function($){
	// animateCss
    $.fn.extend({
        animateCss: function(animationName, fun) {
            var animationEnd = 'webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend';
            $(this).addClass('animated ' + animationName).one(animationEnd, function() {
                $(this).removeClass('animated ' + animationName);
                if (fun)
                	fun.call(this);
            });

            return $(this);
        }
    });

	var Dialog = function(opts, fun) {
		var options = {};
		this.options = $.extend(!0, options, opts);

		this.mask = null;
		this.dialogContent = null;
		this.dialogAnimation = null;

		this.contentClass = this.options.contentClass || 'dialog-content';
        this.maskClass = this.options.maskClass || 'dialog-mask';
		this.maskZIndex = this.options.ZIndex || 10;
		this.type = this.options.type || 1;
		this.dialogAnimation = this.options.animation || 'bounceInDown';
		this.closeBtn = null;
		this.fun = fun || null;

		this.init();
	};

	$.extend(Dialog.prototype, {
		init: function (opts) {
			this.createMask();
			this.createContent();
			this.bindEvent();
        },

        createMask: function () {
            if ($('.' + this.maskClass).length == 0) {
                this.mask = $('<div class="' + this.maskClass + '"></div>');
                this.mask.css({
					'zIndex' : this.maskZIndex,
                }).appendTo('body');
            } else {
                this.mask = $('.' + this.maskClass);
                this.mask.css({
                    'display': 'block'
				});
            }
        },

		createContent: function() {
			switch (this.type) {
				case 1: this.createType1(); break;
			}
		},

		createType1: function () {
			var content = this.options.content || 'some html here';

			if ($('.' + this.contentClass).length == 0) {
				this.dialogContent = $('<div class="' + this.contentClass + '"></div>');
                this.dialogContent.css('zIndex', (1 + this.maskZIndex)).appendTo(this.mask);
            } else {
                this.dialogContent = $('.' + this.contentClass);
			}

            this.dialogContent.animateCss(this.dialogAnimation);

			if (typeof content == 'string') {
				this.dialogContent.html(content);
			} else if (typeof content == 'object') {
				content.clone(true).css('display', 'block').appendTo(this.dialogContent);
			}
        },

		bindEvent: function(e) {
            var that = this;
            this.closeBtn = $('.dialog-close');
            this.closeBtn.each(function() {
            	$(this).off('click').on('click', function(e) {
                    that.mask.hide();
                    that.destory();
					if (that.fun) that.fun.call(that);
                });
			})
		},
		destory: function() {
			this.dialogContent.empty();
			return this;
		}
	});

	$.dialog = function(options, fun) {
		return new Dialog(options, fun);
	}
});