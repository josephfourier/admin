﻿<%@ include file="/resources/inc/topS.jsp" %>
<%@ include file="/resources/inc/topJ.jsp" %>
<style>
    .lap-page{
        background: url(${basePath}/resources/lap/imgs/bg_bmsy.png);
        position: relative;
    }
    .lap-page>.img-container{
        position: relative;
        top: 42px;
        height: 100%;
        width: 750px;
        margin: 0 auto;
    }
    .lap-page>.img-container>img{
        position: absolute;
        left: 0;
        top: 50px;
    }
    .lap-page>.img-container>.address{
        bottom:0;
    }
    #bg{
        width: 750px;
        height: 530px;
    }
    #qrcode{
        width: 190px;
        height: 190px;
        top: 212px;
        position: absolute;
        left: 63px;
    }
    #qrcode>img{
        width: 100%;
        height: 100%;
    }
    #address-container{
        position: absolute;
        top: 0;
        overflow: hidden;
        width: 100%;
        top: -10px;
    }
    #address-container>span,
    #address-container>.w{
        float: left;
        font-size: 14px;
        color: #794e3b;}
    #address-container>.w{
        width: 600px;
        position: relative;
        padding: 10px 0;
    }
    #address-container>span{
        margin-right: 10px;
        margin-top: 10px;
    }
    #address-container>.w>input{
        border: none;
        width: 490px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
    #address-container>.w>a{
        position: absolute;
        right: 20px;
        top: 5px;
        outline: none;
    }
    .button{
        background-color: #0593fd;
        color: #ffffff;
        text-align: center;
        width: 80px;
        height: 30px;
        display: inline-block;
        line-height: 30px;
        -webkit-border-radius: 15px;
        -moz-border-radius: 15px;
        border-radius: 15px;
        margin-right: 10px;
    }
    .button.button-copy{
        background: none;
        border: 1px solid #794e3b;
        color: #794e3b;
        font-size: 12px;
    }
    a{
        text-decoration: none;
        color: #ffffff;
    }
    a:hover,
    a:active,
    a:link,
    a:visited{
        text-decoration: none;
        color: #ffffff;
    }
    #btn-container{
        position: absolute;
        left: 70px;
        top: 430px;
    }
</style>
<div class="lap-page" id="print">
    <div class="img-container">
        <img src="${basePath}/resources/lap/imgs/img_bmsy1.png" alt="" id="bg">

        <div id="qrcode">
            <img src="${basePath}/resources/thinkjoy-admin/images/qrcode/${relationCode}.png"/>
            <%--<img src="${basePath}/resources/thinkjoy-admin/images/qrcode/${relationCode}.png"/>--%>
        </div>
        <div id="address-container" class="no-print">
            <span>报名地址:</span>
            <div class="w">
                <input type="text" value="${address}" id="address">
                <a href="javascript:;" class="button button-copy" id="copy">复制地址</a>
            </div>
        </div>

        <div id="btn-container">
            <a href="javascript:;" class="button button-down" onclick="saveFile('/resources/thinkjoy-admin/images/qrcode/${relationCode}.png','qr_code')">下载</a>
            <a href="javascript:;" class="button button-print" onclick="doPrint();">快速打印</a>
        </div>


    </div>
</div>

<script type="text/javascript">
    var btn = document.getElementById('copy');
    btn.addEventListener('click', function(){
        var inputText = document.getElementById('address');
        var currentFocus = document.activeElement;
        inputText.focus();
        inputText.setSelectionRange(0, inputText.value.length);

        document.execCommand('copy', true);
        currentFocus.focus();
        $('#address-container').tips({
            msg: '复制成功'
        });
    });


    function doPrint() {
        $("#print").print({
            noPrintSelector: ".no-print",
        });
    }


    function saveFile (data, filename){
        var save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a');
        save_link.href = data;
        save_link.download = filename;

        var event = document.createEvent('MouseEvents');
        event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
        save_link.dispatchEvent(event);
    };
</script>


