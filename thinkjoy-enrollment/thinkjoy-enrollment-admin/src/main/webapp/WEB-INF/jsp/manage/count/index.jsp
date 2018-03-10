<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/tags/taglibs.jsp" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>

<%@ include file="/resources/inc/top.jsp" %>

<style>
    html,body{height:100%;}
    .lap-page .lap-section:nth-child(3),
    .lap-page .lap-section:nth-child(4){background: #F0F4FA}
    .lap-section>.w{width: 1020px;margin: 0 auto;padding:10px 0;}
    .lap-section>.w>.lap-simple{width: 100%;}
    .lap-section>.w>.lap-simple td{text-align: center;font-size:12px;color:#444;padding:5px 0;background-color:#fffaf4;}

    .lap-page .lap-section .echarts{width: 1020px;margin: 0 auto;padding:20px 0 10px 0;overflow: hidden;}
    .lap-page .lap-section .echart{margin-bottom: 20px; width: 500px;height: 385px;display: inline-block;}
    .lap-page .lap-section .echart {float: left;background: #fff;position: relative;}
    .lap-page .lap-section .echart:nth-child(2n) {float: right; }

    .lap-page .lap-searchbar.no{padding: 0;}
    .innerSelect{position: absolute;  top: 80px;  left: 50%;  margin-left: -90px;z-index: 999;}
    .lap-page .echarts:last-child{margin-top: -30px;}
    .lap-page .echarts #large{background: #fff; width: 100%;height: 350px;}
    .lap-page .echarts #chart5{ margin: 20px auto 0; }
</style>

<div class="lap-page">
    <div class="lap-searchbar">
        <label class="sl2">
            <span>招生年度</span>
            <select name="batchYear" id="batchYear"></select>
        </label>
    </div>

    <div class="lap-section">
        <div class="w">
            <table border="1" borderColor="#ffad43" id="target" class="lap-simple">
                <tr>
                    <td>批次名称</td>
                    <td>招生年份</td>
                    <td>报名男生人数</td>
                    <td>报名女生人数</td>
                    <td>报名总人数</td>
                    <td>录取男生人数</td>
                    <td>录取女生人数</td>
                    <td>录取总人数</td>
                </tr>
            </table>
        </div>
    </div>

    <div class="lap-section">
        <div class="echarts">
            <div class="echart">
                <div id="chart1" style="width:450px;height:350px;margin: 0 auto;margin-top:50px;"></div>
            </div>
            <div class="echart">
                <div id="chart2" style="width:450px;height:350px;margin: 0 auto;margin-top:50px;"></div>
            </div>
            <div class="echart">
                <div class="lap-searchbar no">
                    <label class="sl2 innerSelect">
                        <select name="faculty" id="batchSelect2"></select>
                    </label>
                </div>
                <div id="chart3" style="width:450px;height:350px;margin: 0 auto;margin-top:50px;"></div>
            </div>
            <div class="echart">
               <div class="lap-searchbar no">
                   <label class="sl2 innerSelect">
                       <select name="faculty" id="facultySelect2"></select>
                   </label>
               </div>
                <div id="chart4" style="width:450px;height:350px;margin: 50px auto 0;"></div>
            </div>
        </div>

        <div class="echarts">
           <div class="echart" id="large">
               <div id="chart5" style="width:900px;height:350px;"></div>
           </div>
        </div>
    </div>


    <script src="${basePath}/resources/thinkjoy-admin/plugins/echarts/echarts.min.js"></script>
    <script src="${basePath}/resources/thinkjoy-admin/plugins/echarts/macarons.js"></script>
    <script>

        var schoolcode = ${schoolcode}

        , batchYearSelect2
        , facultySelect2
        , batchSelect2
        , thisYear = new Date().getFullYear()
        , batchId = null
        , doRefresh = false;

        fillSelect();

        $("#batchYear").change('change', function() {
            var year;

            try {
                year = (this.options[this.selectedIndex].value);
            } catch (e) {
                year = thisYear
            }
            if (doRefresh)
                refreshTable('target', year, schoolcode);
            doRefresh = true;

            getJsoncsData(year);
            getJsonData(year);
            getJsonlsData(year);

            thisYear = batchYearSelect2.val();

            try {
                getJsonzyData(thisYear, facultySelect2.val());
            } catch (e) {}
        });

        $('#facultySelect2').change('change', function() {
            getJsonzyData(thisYear, facultySelect2.val());
        });

        $('#batchSelect2').change('change', function() {
            getJsonlsData(thisYear, batchSelect2.val());
        });

        function fillTable(id, year, sid) {
            var data = {schoolcode: sid};
            if (year != '') data.batchYear = year;

            $.ajax({
                type: 'post',
                url: '${basePath}/manage/enroll/selectbatch',
                data: data,
                success: function(data){
                    var jData = JSON.parse(data);
                    for (var i = 0; i < jData.length; ++i) {
                        d = jData[i];

                        var tr = '<tr>';
                        var batchName = d.batchName;
                        var batchYear = d.batchYear;
                        var totalOfMan = d.bmnum;
                        var totalOfWom = d.bwnum;
                        var total = d.bzrnum;
                        var ltotalOfMan = d.lmnum;
                        var ltotalOfWom = d.lwnum;
                        var ltotal = d.lzrnum;

                        tr += '<td>' + batchName + '</td>';
                        tr += '<td>' + batchYear + '</td>';
                        tr += '<td>' + totalOfMan + '</td>';
                        tr += '<td>' + totalOfWom + '</td>';
                        tr += '<td>' + total + '</td>';
                        tr += '<td>' + ltotalOfMan + '</td>';
                        tr += '<td>' + ltotalOfWom + '</td>';
                        tr += '<td>' + ltotal + '</td>';
                        tr += '</tr>';

                        $('#' + id).append(tr);
                    }
                    fillbatchSelect2(jData);
                }

            });
        }

        function refreshTable(id, year, sid) {
            $('#' + id).find('tr:gt(0)').remove();
            fillTable(id, year, sid);
        }

        function fillSelect() {
            $.getJSON('${basePath}/manage/batch/list', {
                limit: "10000"
            }, function (json) {
                var datasYear = [];
                var tmpYears = [];
                for (var i = 0; i < json.rows.length; i++) {
                    data = {};
                    data.id = json.rows[i].batchYear;
                    data.text = json.rows[i].batchYear + '年';

                    if ($.inArray(data.text, tmpYears) == -1) {
                        datasYear.push(data);
                        tmpYears.push(data.text);
                    }
                }

                batchYearSelect2 = $('#batchYear').select2({
                    width: 160,
                    minimumResultsForSearch: Infinity,
                    data: datasYear
                });
                batchYearSelect2.val(thisYear).trigger('change');
                fillTable('target', thisYear, schoolcode);
            });
        }

        function fillFacultySelect2(data) {
            var datasFaculty = [];
            for (var i = 0; i < data.length; ++i) {
                datasFaculty.push({id: data[i].facultyCode, text: data[i].facultyName});
            }
            facultySelect2 = $('#facultySelect2').select2({
                data: datasFaculty,
                width: 180,
                minimumResultsForSearch: 5
            });

            getJsonzyData(thisYear, facultySelect2.val());
        }

        function fillbatchSelect2(data) {
            var datasBatch = [];
            for (var i = 0; i < data.length; ++i) {
                datasBatch.push({id: data[i].batchId, text: data[i].batchName});
            }
            $('#batchSelect2').empty();
            batchSelect2 = $('#batchSelect2').select2({
                data: datasBatch,
                width: 180,
                minimumResultsForSearch: 5
            });

            getJsonlsData(thisYear, batchSelect2.val());
        }


        var chart1 = echarts.init($('#chart1')[0])
            , chart2 = echarts.init($('#chart2')[0])
            , chart3 = echarts.init($('#chart3')[0])
            , chart4 = echarts.init($('#chart4')[0])
            , chart5 = echarts.init($('#chart5')[0])
            , defaultColor = ['#36CB73', '#8B6AFF' ,'#3A99D8','#2ED2DD'];

        var option = {
            title : {
                text: '院系招生人数占比',
                x:'center',
                textStyle: {'fontWeight': 'normal', 'fontSize': '14', 'marginBottom': '0','color':'#222'}
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            color: defaultColor,
            series: [
                {
                    name:'院系招生人数占比',
                    type:'pie',
                    radius: ['50%', '40%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: true,
                            color: 'red',
                            formatter: function (param) {
                                return param.name + ' ' + param.percent + '%';
                            }
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '14',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            length: 25,
                            lineStyle: {
                                width: 2,
                                color: '#ddd'
                            },
                            formatter: function (param) {
                                return param.name + ':\n' + param.value + '%';
                            }
                        }
                    },
                    data:[]
                }
            ]
        };

        var nothingToShow = {
            tooltip : {
                trigger: 'item',
                formatter: "暂无数据"
            },
            color : ['#e2e2e2']
        };

        var option1 = option
        , option2 = {}
        , option3 = {}
        , option4 = {};


        $.extend(!0, option2, option);
        $.extend(!0, option3, option);
        $.extend(!0, option4, option);

        option2.title.text = '生源地人数占比';
        option2.series[0].name = '生源地人数占比';

        option3.title.text = '招生人员招生人数占比';
        option3.series[0].name = '招生人员招生人数占比';

        option4.title.text = '专业招生人数占比';
        option4.series[0].name = '专业招生人数占比';

        option5 = {
            title : {
                text: '近5年招生人数对比',
                y:'left',
                itemGap:50,
                textStyle: {'fontWeight': 'normal', 'fontSize': '13', 'marginBottom': '0','color':'#222'}

            },
            color: defaultColor,
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow',
                    crossStyle: {
                        color: '#999'
                    }
                }
            },
            toolbox: {
                feature: {
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            legend: {
                data:['报名人数','录取人数','录取率']
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['2012年','2013年','2014年','2015年','2016年','2017年'],
                    axisPointer: {
                        type: 'line'
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '报名人数',
                    min: 0,
                    max: 4000,
                    interval:400,
                    axisLabel: {
                        formatter: '{value} 人'
                    }
                },
                {
                    type: 'value',
                    name: '录取率',
                    min: 0,
                    max: 100,
                    interval:10,
                    axisLabel: {
                        formatter: '{value} %'
                    }
                }
            ],
            series: [
                {
                    barWidth: 20,
                    name:'报名人数',
                    type:'bar',
                    data:[300, 900, 3200, 2500, 1800,1500]
                },
                {
                    barWidth: 20,
                    name:'录取人数',
                    type:'bar',
                    data:[240, 730,3000 ,2000,1700, 1300]
                },
                {
                    name:'录取率',
                    type:'line',
                    yAxisIndex: 1,
                    data:[80.0, 90.0, 90.8, 70.0, 60.0, 40.0]
                }
            ]
        };

        chart1.setOption(option);
        chart2.setOption(option2);
        chart3.setOption(option3);
        chart4.setOption(option4);
        chart5.setOption(option5);

        //查询院系学生信息
        //查询数据
        var batchYear = thisYear;
        function getJsonData(year){
            if(schoolcode!=''){
                $.ajax({
                    type: 'post',
                    url: '${basePath}/manage/enroll/selectfaculty',
                    data: {batchYear:year, schoolcode:schoolcode},
                    success: function(data){
                        var data = eval('(' + data + ')');
                        _checkData(data, chart1, getOptionData)
                        fillFacultySelect2(data);
                    }
                })
            }
        }
        getJsonData(thisYear);

        function _checkData(data, chart, fun) {
            var result = true;

            if (data == '') {
                data = [{}];
                chart.setOption({tooltip : nothingToShow.tooltip});
                chart.setOption({color : nothingToShow.color});
            } else {
                chart.setOption({color : defaultColor});
                result = false;
            }
            chart.setOption({series : fun.call(this, data)});

            return result;
        }

        function getOptionData(data){
            var series=[];
            var arrays = new Array();
            for(var i in data){
                var obj = data[i];
                //console.log("院系名称："+obj.facultyName+",百分比："+obj.num);
                arrays[i]={value: obj.fnum, name: obj.facultyName};
            }
            series.push({data:arrays});
            return series;
        }

        //查询专业学生数据
        function getJsonzyData(year, facultyCode){
            if(schoolcode!=''){
                $.ajax({
                    type: 'post',
                    url: '${basePath}/manage/enroll/selectspecialty',
                    data: {batchYear:year, schoolcode:schoolcode,facultyCode:facultyCode},
                    success: function(data){
                        var data = eval('(' + data + ')');
                        console.log(data);
                        _checkData(data, chart4, getOptionzyData);
                    }
                });
            }
        }

        function getOptionzyData(data){
            var series=[];
            var arrays = new Array();
            for(var i in data){
                var obj=data[i];
                //console.log("专业名称："+obj.specialtyName+",百分比："+obj.fnum);
                arrays[i]={value: obj.fnum,name: obj.specialtyName};
            }
            series.push({data:arrays});
            return series;
        }

        //查询生源地学生数据
        function getJsoncsData(year){
            if(schoolcode!=''){
                $.ajax({
                    type: 'post',
                    url: '${basePath}/manage/enroll/selectfromplace',
                    data: {batchYear:year,schoolcode:schoolcode},
                    success: function(data){
                        var data = eval('(' + data + ')');
                       _checkData(data, chart2, getOptioncsData);
                    }
                });
            }
        }

        getJsoncsData(batchYear);
        function getOptioncsData(data){
            var series=[];
            var arrays = new Array();
            for(var i in data){
                var obj=data[i];
                //console.log("城市名称："+obj.cityName+",百分比："+obj.fnum);
                arrays[i]={value: obj.fnum, name: obj.cityName};
            }
            series.push({data:arrays});

            return series;
        }

        //查询招生老师学生数据
        function getJsonlsData(year, batchId){
            var enrollteacherId='null';
            if(schoolcode!=''){
                $.ajax({
                    type: 'post',
                    url: '${basePath}/manage/enroll/selectteacher',
                    data: {batchYear:year, schoolcode:schoolcode, batchId:batchId},
                    success: function(data){
                        var data = eval('(' + data + ')');

                        if (!_checkData(data, chart3, getOptionlsData)) {}
                    }
                });
            }
        }
        getJsonlsData();


        function getOptionlsData(data){

            var series=[];
            var arrays = new Array();
            for(var i in data){
                var obj=data[i];
               // console.log("专业名称："+obj.teacherName+",百分比："+obj.fnum);
                arrays[i]={value: obj.fnum, name: obj.teacherName};
            }
            series.push({data:arrays});
            return series;
        }
    </script>
</div>
<%--
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>统计分析</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>

<body>

    <table style="width: 100%;border:none;">
        <table border="1">
            <tr>
                <tr >
                    <td>批次名称</td>
                    <td>计划招生人数</td>
                    <td>报名总人数</td>
                    <td>报名男生人数</td>
                    <td>报名女生人数</td>
                    <td>录取总人数</td>
                    <td>录取男生人数</td>
                    <td>录取女生人数</td>
                </tr>
                <c:forEach items="${enrollStudentList}" var="obj" varStatus="objIndex">
                    <tr>
                        <td>${obj.batchName}</td>
                        <td>${obj.plannum}</td>
                        <td>${obj.bzrnum}</td>
                        <td>${obj.bmnum}</td>
                        <td>${obj.bwnum}</td>
                        <td>${obj.lzrnum}</td>
                        <td>${obj.lmnum}</td>
                        <td>${obj.lwnum}</td>
                    </tr>
                </c:forEach>
            </tr>
            <tr>
                <td>
                    <table>
                        &lt;%&ndash;<tr><p class="title">院系招生人数占比</p></tr>&ndash;%&gt;
                        <tr>
                            <div id="main" style="width:900px;height:300px;"></div>
                        </tr>
                    </table>
                </td>
                <td>
                    <table>
                        &lt;%&ndash;<tr><p class="title">院系招生人数占比</p></tr>&ndash;%&gt;
                        <tr>
                            <div id="main1" style="width:900px;height:300px;"></div>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table>
                        &lt;%&ndash;<tr><p class="title">院系招生人数占比</p></tr>&ndash;%&gt;
                        <tr>
                            <div id="main2" style="width:900px;height:300px;"></div>
                        </tr>
                    </table>
                </td>
                <td>
                    <table style="float:left;">
                        &lt;%&ndash;<tr><p class="title">院系招生人数占比</p></tr>&ndash;%&gt;
                        <tr>
                            <div id="main3" style="width:900px;height:300px;"></div>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <table>
                    &lt;%&ndash;<tr><p class="title">近5年招生人数对比</p></tr>&ndash;%&gt;
                    <tr>
                        <div id="main4" style="width:1600px;height:500px;"></div>
                    </tr>
                </table>
            </tr>
        </table>
    </table>


    <SCRIPT type="text/javascript" >

        var schoolCode = ${schoolcode};
        batchYear = 2017;


        $(function() {
            getJsonData();
            getJsonzyData();
            getJsonlsData();
            getJsoncsData();
        });

        var myChart = echarts.init(document.getElementById('main'));
        var myChart1 = echarts.init(document.getElementById('main1'));
        var myChart2 = echarts.init(document.getElementById('main2'));
        var myChart3 = echarts.init(document.getElementById('main3'));
        var myChart4 = echarts.init(document.getElementById('main4'));
        //var colorList =  ['#C23531','#61A0A8','#2F4554','#D48265','#DE9325','#BDA29A'];
       // var color=[ "#86D560", "#AF89D6", "#59ADF3", "#FF999A", "#FFCC67" ,"#0092FF"];
        //院系招生人数占比
        option = {
            title : {
                text: '院系招生人数占比',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },

//            legend: {
//                orient: 'vertical',
//                left: 'left',
//                data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
//            },
            series : [
                {
                    name: '院系招生人数占比',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[
                        {value:0, name:'院系'}
                    ],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);

        //专业招生人数占比
        option1 = {
            title : {
                text: '专业招生人数占比',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            series : [
                {
                    name: '专业招生人数占比',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[
                        {value:0, name:'专业'}
                    ],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        myChart1.setOption(option1);

        //生源地人数占比
        option2 = {
            title : {
                text: '生源地人数占比',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            series : [
                {
                    name: '生源地人数占比',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[
                        {value:0, name:'生源地'}
                    ],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        myChart2.setOption(option2);

        //招生人员招生人数占比
        option3 = {
            title : {
                text: '招生人员招生人数占比',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            series : [
                {
                    name: '招生人员招生人数占比',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[
                        {value:0, name:'招生人员'},
                    ],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        myChart3.setOption(option3);

        //近5年招生人数对比
        option4 = {
            title : {
                text: '近5年招生人数对比',
                y:'left',
                itemGap:50
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow',
                    crossStyle: {
                        color: '#999'
                    }
                }
            },
            toolbox: {
                feature: {
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            legend: {
                data:['报名人数','录取人数','录取率']
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['2012年','2013年','2014年','2015年','2016年','2017年'],
                    axisPointer: {
                        type: 'line'
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '报名人数',
                    min: 0,
                    max: 4000,
                    interval:400,
                    axisLabel: {
                        formatter: '{value} 人'
                    }
                },
                {
                    type: 'value',
                    name: '录取率',
                    min: 0,
                    max: 100,
                    interval:10,
                    axisLabel: {
                        formatter: '{value} %'
                    }
                }
            ],
            series: [
                {
                    name:'报名人数',
                    type:'bar',
                    data:[300, 900, 3200, 2500, 1800,1500]
                },
                {
                    name:'录取人数',
                    type:'bar',
                    data:[240, 730,3000 ,2000,1700, 1300]
                },
                {
                    name:'录取率',
                    type:'line',
                    yAxisIndex: 1,
                    data:[80.0, 90.0, 90.8, 70.0, 60.0, 40.0]
                }
            ]
        };
        myChart4.setOption(option4);

        //查询院系学生信息
        //查询数据
        var schoolcode=${schoolcode};
        var batchYear='2017';
        function getJsonData(){
            if(schoolcode!=''){
                $.ajax({
                    type: 'post',
                    url: '${basePath}/manage/enroll/selectfaculty',
                    data: {batchYear:batchYear, schoolcode:schoolcode},
                    success: function(data){
                        var data = eval('(' + data + ')');
                        //console.log("院系名称："+data);
                        myChart.setOption({series : getOptionData(data)});
                    }
                });
            }
        }
        function getOptionData(data){
            var series=[];
            var arrays = new Array();
            for(var i in data){
                var obj=data[i];
                //console.log("院系名称："+obj.facultyName+",百分比："+obj.num);
                arrays[i]={value: obj.fnum,name: obj.facultyName};
            }
            series.push({data:arrays});
            return series;
        }

        //查询专业学生数据
        function getJsonzyData(){
            var facultyCode='1200';
            if(schoolcode!=''){
                $.ajax({
                    type: 'post',
                    url: '${basePath}/manage/enroll/selectspecialty',
                    data: {batchYear:batchYear,schoolcode:schoolcode,facultyCode:facultyCode},
                    success: function(data){
                        var data = eval('(' + data + ')');
                        myChart1.setOption({series : getOptionzyData(data)});
                    }
                });
            }
        }
        function getOptionzyData(data){
            var series=[];
            var arrays = new Array();
            for(var i in data){
                var obj=data[i];
                console.log("专业名称："+obj.specialtyName+",百分比："+obj.fnum);
                arrays[i]={value: obj.fnum,name: obj.specialtyName};
            }
            series.push({data:arrays});
            return series;
        }

        //查询生源地学生数据
        function getJsoncsData(){
            if(schoolcode!=''){
                $.ajax({
                    type: 'post',
                    url: '${basePath}/manage/enroll/selectfromplace',
                    data: {batchYear:batchYear,schoolcode:schoolcode},
                    success: function(data){
                        var data = eval('(' + data + ')');
                        myChart2.setOption({series : getOptioncsData(data)});
                    }
                });
            }
        }
        function getOptioncsData(data){
            var series=[];
            var arrays = new Array();
            for(var i in data){
                var obj=data[i];
                console.log("城市名称："+obj.cityName+",百分比："+obj.fnum);
                arrays[i]={value: obj.fnum,name: obj.cityName};
            }
            series.push({data:arrays});
            return series;
        }

        //查询招生老师学生数据
        function getJsonlsData(){
            var enrollteacherId='null';
            if(schoolcode!=''){
                $.ajax({
                    type: 'post',
                    url: '${basePath}/manage/enroll/selectteacher',
                    data: {batchYear:batchYear,schoolcode:schoolcode,enrollteacherId:enrollteacherId},
                    success: function(data){
                        var data = eval('(' + data + ')');
                        myChart3.setOption({series : getOptionlsData(data)});
                    }
                });
            }
        }
        function getOptionlsData(data){
            var series=[];
            var arrays = new Array();
            for(var i in data){
                var obj=data[i];
                console.log("专业名称："+obj.teacherName+",百分比："+obj.fnum);
                arrays[i]={value: obj.fnum,name: obj.teacherName};
            }
            series.push({data:arrays});
            return series;
        }


        fillTable('target', 2017, schoolcode);
        function fillTable(id, year, sid) {
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/enroll/selectbatch',
                data: {batchYear:year, schoolcode:sid},
                success: function(data){
                    var jData = JSON.parse(data);
                    console.log(jData);
                    for (var i = 0; i < jData.length; ++i) {
                        d = jData[i];
                        var tr = '<tr>';
                        var batchName = d.batchName;
                        var batchYear = d.batchYear;
                        var totalOfMan = d.bmnum;
                        var totalOfWom = d.bwnum;
                        var total = d.bzrnum;
                        var ltotalOfMan = d.lmnum;
                        var ltotalOfWom = d.lwnum;
                        var ltotal = d.lzrnum;

                        tr += '<td>' + batchName + '</td>';
                        tr += '<td>' + batchYear + '</td>';
                        tr += '<td>' + totalOfMan + '</td>';
                        tr += '<td>' + totalOfWom + '</td>';
                        tr += '<td>' + total + '</td>';
                        tr += '<td>' + ltotalOfMan + '</td>';
                        tr += '<td>' + ltotalOfWom + '</td>';
                        tr += '<td>' + ltotal + '</td>';
                        tr += '</tr>';
                        $('#' + id).append(tr);
                    }
                }
            });
        }

        function refreshTable(id, year, sid) {
            $('#' + id).find('tr:gt(0)').remove();
            fillTable(id, year, sid);
        }

        $("#batchYear").change('change', function() {
            var year = (this.options[this.selectedIndex].value);
            refreshTable('target', year, schoolcode);
        });
    </SCRIPT>
</body>
</html>
--%>
