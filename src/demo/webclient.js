var utils = {

    isNullOrEmpty:function(value){

        if(value ==null|| value== "" || value=="undefined" ||value ==undefined){
            return true;
        }
        return false;
    },
    getUUID:function(){
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
        s[8] = s[13] = s[18] = s[23] = "-";

        var uuid = s.join("");
        return uuid;
    },
    getJid:function (user_id) {
        return user_id+'@'+webclient.DEFAULT_DOMAIN;
    },
    parse : function(protocol){
        var uuid = utils.getUUID();
        var protocol_ = {
            msg_id:uuid,
            msg_type:'MTS-000',
            body:null,
            compress:'0',
            encode:'1',
            encrypt:'0',
            version:'2.0.0',
            msg_from:webclient.DEFAULT_USER,
            msg_to:webclient.DEFAULT_USER,
            msg_time:new Date().getTime()+''
        };
        protocol_ = $.extend({}, protocol_, protocol);//合并参数
        return protocol_;
    },
    hideTestButton:function () {
        $("#btn_test_1").hide();
        $("#btn_test_2").hide();
    }



};
var webclient={
    DEFAULT_IP:'coolweb.club',//默认IP
    DEFAULT_LOCAL_IP:'123.207.154.188',
    DEFAULT_DOMAIN:'im.itonglian.com',//默认域名
    DEFAULT_USER:'673b15e889df4e4aaa33b46d1b433189',//默认用户
    DEFAULT_PWD:'123',//默认密码
    DEFAULT_PORT:'7070',//bosh 绑定端口
    DEFAULT_RESOURCE:utils.getUUID()+'[web]',//默认资源名称
    DEBUG:true,//debug打印日志
    CONNECTION:null,
    CONNECTED:false,
    xmlns:'im.itonglian.com',
    location:'web'

};



webclient.instance = function (params) {
    var that = webclient;
    var getBosh_,
        bosh_,
        jid_
    ;
    var params_ = {
        ip:that.DEFAULT_IP,
        port:that.DEFAULT_PORT,
        user:that.DEFAULT_USER,
        pwd:that.DEFAULT_PWD,
        resource:that.DEFAULT_RESOURCE,
        domain:that.DEFAULT_DOMAIN,
        onConnect:onConnect,
        onConnected:onConnected,
        debug:that.DEBUG,
        onUnConnected:onUnConnected,
        onReConnecting:onReConnecting,
        onMessageHandler:onMessageHandler
    };
    that.DEFAULT_USER = params.user;
    params_=$.extend({}, params_, params);//合并参数
    that.DEBUG = params_.debug;
    /*组装bosh连接**/
    getBosh_=function(){
        if(typeof(window.WebSocket)=="function"||window.WebSocket){
            return 'ws://'+params_.ip+':'+params_.port+'/ws/server';//当浏览器支持websocket时使用
        }	else{
            return 'http://'+params_.ip+':'+params_.port+'/http-bind/';//不支持启用长连接轮训机制
        }
    };
    /**获取openfire连接*/
    var connect_=function(){
        if(!that.CONNECTED) {
            that.log("登陆用户jid:"+jid_);
            that.log("连接方式:"+bosh_);
            that.CONNECTION = new Strophe.Connection(bosh_);
            that.CONNECTION.connect(jid_, params_.pwd, params_.onConnect);
        }
    };
    function getJid(){
        return params_.user+"@"+params_.domain+"/"+params_.resource;
    }

    function onConnect(status) {
        switch (status) {
            case Strophe.Status.CONNECTED:
                that.log("已连接...");
                that.CONNECTED = true;
                onConnected();
                break;
            case Strophe.Status.DISCONNECTED:
                that.log("连接已终止...");
                that.CONNECTED = false;
                params_.onUnConnected()
                break;
            case Strophe.Status.DISCONNECTING:
                that.log("正在终止连接...");
                break;
            case Strophe.Status.CONNTIMEOUT:
                that.log("连接超时...");
                that.CONNECTED = false;
                break;
            case Strophe.Status.ERROR:
                that.log("连接出错...");
                that.CONNECTED = false;
                break;
            case Strophe.Status.CONNECTING:
                that.log("正在连接...");
                break;
            case Strophe.Status.CONNFAIL:
                that.log("连接失败...");
                that.CONNECTED = false;
                break;
            case Strophe.Status.AUTHENTICATING:
                that.log("正在校验身份...");
                break;
            case Strophe.Status.AUTHFAIL:
                that.log("身份校验失败...");
                that.CONNECTED = false;
                break;
            case Strophe.Status.ATTACHED:
                that.log("已附加连接...");
                break;
            default:
                that.log("无效状态...");
                that.CONNECTED = false;
                params_.onUnConnected()
                break;

        }
    }
    function onConnected() {
        that.log('注册消息监听onMessageCallback');
        that.CONNECTION.addHandler(onMessageCallback, null, 'message', null, null, null);
        that.CONNECTION.send($pres().tree());
        if(params.onConnected!=null){
            params_.onConnected();
        }
    }

    var onMessageCallback = function (message) {
        that.log("消息监听:", "收到新消息...");

        var tagBodys = message.getElementsByTagName('body');

        if (tagBodys == null || tagBodys == "" || tagBodys.length == 0) {
            that.log('消息验证失败:不存在消息内容body,' + message);
            return true;
        }
        var tagBody = tagBodys[0];

        var msg = JSON.parse(Strophe.xmlunescape(Strophe.getText(tagBody)));

        params_.onMessageHandler(msg);

        return true;
    };

    function onMessageHandler(msg) {
        that.log('默认onMessageHandler' + msg);
    }

    function onUnConnected() {
        that.log("onUnConnected...");
    }

    function onReConnecting() {
        that.log("onReConnecting...");
    }

    function unValid(message) {
        that.warn('消息验证失败:无效消息,', message);
        return false;
    }

    (function () {
        bosh_ = getBosh_();
        jid_ = getJid();
        connect_();
    })();
    return that;
};
webclient.log = function (info) {
    var that = webclient;
    if(that.DEBUG){
        console.log(getNowFormatDate(),info);
        $("#log").append("<div style='font-size: 12px;;'>"+getNowFormatDate()+"   :   "+info+"</div>");
    }
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
        return currentdate;
    }
};
webclient.msghandler = {
    sendFrom:function(from,to,protocol){
        var connection = webclient.CONNECTION;
        if(connection==null){
            webclient.log('未初始化连接，发送消息失败...');
            return;
        }
        var msg = $msg({
            from: utils.getJid(from),
            to: utils.getJid(protocol.session_id),
            type:'chat'
        }).c('tlim',{
            xmlns:'im.itonglian.com',
            location:'web'
        }).up().c('body',null,JSON.stringify(protocol)).tree();
        webclient.log('发送消息...');
        connection.send(msg);
    },
    send:function(to,protocol){
        var connection = webclient.CONNECTION;
        if(connection==null){
            webclient.log('未初始化连接，发送消息失败...');
            return;
        }
        var msg = $msg({
            from: utils.getJid(webclient.DEFAULT_USER),
            to: utils.getJid(protocol.session_id),
            type:'chat'
        }).c('tlim',{
            xmlns:'im.itonglian.com',
            location:'web'
        }).up().c('body',null,JSON.stringify(protocol)).tree();
        webclient.log('发送消息...');
        connection.send(msg);
    },

};

var isInit = false;
$(document).ready(function () {

    $("#btn_test_1").click(function () {
        if(!isInit){
            webclient.log('请先选择用户...');
        }
        utils.hideTestButton();
        $("#user").text('当前登录用户:张三');
        init('673b15e889df4e4aaa33b46d1b433189');
    });

    $("#btn_test_2").click(function () {
        if(!isInit){
            webclient.log('请先选择用户...');
        }
        utils.hideTestButton();
        $("#user").text('当前登录用户:李四');
        init('33c70c78c04344dd81be0451044c001c');
    });

    $("#send").click(function () {
        var session_id = '7a5c0813-b2b3-4b92-8df6-fb9918401cee';
        webclient.log('会话id:'+session_id);
        var text = $("#content").val();
        var protocol = utils.parse({
            body:[
                {
                    "session_id":session_id,
                    "text":text
                }
            ],
            msg_type:'MTS-000',
            msg_to:session_id
        });
        webclient.msghandler.send(webclient.DEFAULT_USER,protocol);
    });

});

function init(user_id){
    isInit = true;
    new webclient.instance({
        debug:true,
        user:user_id,
        onConnected:OverrideOnConnected,
        onMessageHandler:OverrideOnMessageHandler

    });

    function OverrideOnConnected(){
        webclient.log('OverrideOnConnected...');
    }
    function OverrideOnMessageHandler(message) {
        webclient.log('OverrideOnMessageHandler...');
        console.info(message);
        webclient.log(message.body[0].text);
    }
}

