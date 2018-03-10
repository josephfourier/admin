(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    } else if (typeof module === 'object' && module.exports) {
        module.exports = factory(required('jquery'));
    } else {
        factory(window.jQuery || window.Zepto);
    }
})(function ($) {

    var OtherPicker = function (el, opts, fun) {
        var options = {};

        this.el = $(el);

        this.options = $.extend(!0, options, opts);

        this.specialty = null
        , this.specialtyCode = null
        , this.direction = null
        , this.directionCode = null
        , this.fun = fun || null
        , this.hiddenList = {}
        , this.expose = null;

        this.schoolCode = this.options.schoolCode || 0;

        this.hidden = $(this.options.hidden) || null;

        this.elType = this.el.is('input');
        this.event = this.el.is('input') ? 'focus' : 'click';
        this.specialties = options.specialties || null;
        this.init();

    };

    $.extend(OtherPicker.prototype, {
        init: function () {
            this.initEvent();
            this.preventKeyBoardEvent();
        },

        initEvent: function () {
            this.el.on(this.event, function (event) {
                var pickerBox = $('.picker-box-specialty');
                if (pickerBox[0]) pickerBox.remove();
                else this.create();
            }.bind(this));
        },

        create: function () {
            this.createSpecialtyPickerBox();
            this.createSpecialtyList();
            this.specialtyClick();
        },

        createSpecialtyList: function () {

            var specialties = this.specialties;
            var dl = '';

            for (var key in specialties) {
                var specialtycode = key;

                var dd = '';
                dd += '<dd data-specialtycode="' + specialtycode + '">' + specialties[key] + '</dd>'
                dl += '<dl>' + dd + '</dl>';
            }
            $('.picker-box-specialty').append('<section class="specialty-picker">' + dl + '</section>');
        },

        createDirectionList: function (url, specialtyCode, schoolCode) {
            var that = this;

            $.ajax({
                type: 'post',
                url: url,
                data: {specialtyCode: specialtyCode, schoolcode: schoolCode},
                success: function (result) {
                    that.expose = result;
                    var directionList = result;
                    var ul, li = '';


                    $.each(directionList, function () {
                        if (this.trdrId)
                            li += '<li data-directioncode="' + this.trdrId + '">' + this.trdrName + '</li>';
                        else
                            li += '<li>' + '暂无培养方向' + '</li>';
                    });
                    ul = '<ul class="direction-picker">' + li + "</ul>";
                    $('.picker-box-specialty').find('.direction-picker')
                        .remove().end()
                        .append(ul);

                    $('.specialty-picker').hide();
                    that.directionClick();
                }
            });
        },

        specialtyClick: function () {
            var that = this;
            $('.specialty-picker').on('click', function (e) {
                var $target = $(e.target);
                if ($target.is('dd')) {
                    that.specialty = $target.html();

                    that.specialtyCode = $target.data('specialtycode');
                    that.createDirectionList(that.options.remote.specialty, that.specialtyCode, that.schoolCode);
                    //$(this).hide();

                    $.extend(!0, that.hiddenList, {
                        specialty:  that.specialty,
                        specialtyCode:  that.specialtyCode
                    });
                }
            })
        },
        directionClick: function () {
            var that = this;
            $('.direction-picker').off('click').on('click', function (e) {
                var $target = $(e.target);
                if ($target.is('li')) {
                    that.direction = $target.html();
                    that.directionCode = $target.data('directioncode');

                    $.extend(!0, that.hiddenList, {
                        direction       :  that.direction,
                        directionCode   :  that.directionCode
                    });

                    if (that.elType) {
                        that.el.val(that.specialty + "-" + that.direction);

                    } else {
                        that.el.html(that.specialty + "-" + that.direction);
                    }

                    if (that.hidden && that.hidden.is('input')) {
                        that.hidden.val(that.specialty + "-" + that.direction);
                    }

                    $(".picker-box-specialty").hide();
                    $(".specialty-picker").show();
                    $(this).hide();

                    $('.picker-box-specialty').remove();

                    if (that.fun) that.fun.call(that, that.expose);
                    that.clearup();
                }
            });
        },

        clearup: function() {
            this.hiddenList = {};
            this.expose = {};
        },

        createSpecialtyPickerBox: function () {
            $('body').append('<div class="picker-box-specialty"></div>');
        },

        preventKeyBoardEvent: function () {
            if (this.elType)
                this.el.prop('readonly', true);
        }
    });

    $.fn.otherPicker = function (options, fun) {
        return new OtherPicker(this, options, fun);
    }
});