/**
 * Created by JosephFourier on 2018/1/6.
 */

jQuery.validator.addMethod("phone", function(value, element, params) {
    return this.optional(element) || (/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(value));
});
jQuery.validator.addMethod("idcard", function(value, element, params) {
    return this.optional(element) || (/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value));
});
