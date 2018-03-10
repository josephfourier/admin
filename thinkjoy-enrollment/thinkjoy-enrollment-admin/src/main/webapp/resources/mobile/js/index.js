/**
 * Created by JosephFourier on 2017/12/25.
 */

var calendar = new LCalendar();
calendar.init({
    'trigger': '#birthday',
    'type': 'date', //date datetime  time ym ,
    'minDate': '1900-1-1',
    'maxDate': new Date().getFullYear() + 10 + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate()
});

$('.btn-submit').click(function (e) {
    e.preventDefault();
    $.dialog( {
        content: $('#success')
    });
});


var provinces = {};
var options = $('#pro>option');

$.each(options, function() {
    provinces[this.value] = this.innerHTML;
});

$('#hometown').cityPicker({
    provinces: provinces,
    hidden: '#origin',
    remote: { city: '${basePath}/manage/enroll/areaList' }
});