<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commons/jquery-1.9.1.min.js"></script>
<title>登陆</title>
<style type="text/css">
body {
    background: #ebebeb;
    font-family: "Helvetica Neue", "Hiragino Sans GB", "Microsoft YaHei",
        "\9ED1\4F53", Arial, sans-serif;
    color: #222;
    font-size: 12px;
}

* {
    padding: 0px;
    margin: 0px;
}

.top_div {
    background: #008ead;
    width: 100%;
    height: 400px;
}

.ipt {
    border: 1px solid #d3d3d3;
    padding: 10px 10px;
    width: 290px;
    border-radius: 4px;
    padding-left: 35px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
    -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow
        ease-in-out .15s;
    -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out
        .15s;
    transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s
}

.ipt:focus {
    border-color: #66afe9;
    outline: 0;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px
        rgba(102, 175, 233, .6);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px
        rgba(102, 175, 233, .6)
}

.u_logo {
    background:
        url("<%=request.getContextPath()%>/images/login/username.png")
        no-repeat;
    padding: 10px 10px;
    position: absolute;
    top: 43px;
    left: 40px;
}

.p_logo {
    background:
        url("<%=request.getContextPath()%>/images/login/password.png")
        no-repeat;
    padding: 10px 10px;
    position: absolute;
    top: 12px;
    left: 40px;
}

a {
    text-decoration: none;
}

.tou {
    background: url("<%=request.getContextPath()%>/images/login/tou.png")
        no-repeat;
    width: 97px;
    height: 92px;
    position: absolute;
    top: -87px;
    left: 140px;
}

.left_hand {
    background:
        url("<%=request.getContextPath()%>/images/login/left_hand.png")
        no-repeat;
    width: 32px;
    height: 37px;
    position: absolute;
    top: -38px;
    left: 150px;
}

.right_hand {
    background:
        url("<%=request.getContextPath()%>/images/login/right_hand.png")
        no-repeat;
    width: 32px;
    height: 37px;
    position: absolute;
    top: -38px;
    right: -64px;
}

.initial_left_hand {
    background: url("<%=request.getContextPath()%>/images/login/hand.png")
        no-repeat;
    width: 30px;
    height: 20px;
    position: absolute;
    top: -12px;
    left: 100px;
}

.initial_right_hand {
    background: url("<%=request.getContextPath()%>/images/login/hand.png")
        no-repeat;
    width: 30px;
    height: 20px;
    position: absolute;
    top: -12px;
    right: -112px;
}

.left_handing {
    background:
        url("<%=request.getContextPath()%>/images/login/left-handing.png")
        no-repeat;
    width: 30px;
    height: 20px;
    position: absolute;
    top: -24px;
    left: 139px;
}

.right_handinging {
    background:
        url("<%=request.getContextPath()%>/images/login/right_handing.png")
        no-repeat;
    width: 30px;
    height: 20px;
    position: absolute;
    top: -21px;
    left: 210px;
}
</style>

<script type="text/javascript">
	function login(){
	    var userName= $("#userName").val().trim();
	    var password=$("#password").val().trim();
	    if(userName==""||password==""){
	        alert("请输入账号和密码");
	    }else{
	        $("#form").submit();
	    }
	}

    $(function(){
        //得到焦点
        $("#password").focus(function(){
            $("#left_hand").animate({
                left: "150",
                top: " -38"
            },{step: function(){
                if(parseInt($("#left_hand").css("left"))>140){
                    $("#left_hand").attr("class","left_hand");
                }
            }}, 2000);
            $("#right_hand").animate({
                right: "-64",
                top: "-38px"
            },{step: function(){
                if(parseInt($("#right_hand").css("right"))> -70){
                    $("#right_hand").attr("class","right_hand");
                }
            }}, 2000);
        });
        //失去焦点
        $("#password").blur(function(){
            $("#left_hand").attr("class","initial_left_hand");
            $("#left_hand").attr("style","left:100px;top:-12px;");
            $("#right_hand").attr("class","initial_right_hand");
            $("#right_hand").attr("style","right:-112px;top:-12px");
        });
    });
</script>
</head>
<body>
    <div class="top_div"></div>
    <div style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 200px; text-align: center;">
        <form id="form" action="<%=request.getContextPath()%>/index" method="post">
	        <div style="width: 165px; height: 96px; position: absolute;">
	            <div class="tou"></div>
	            <div class="initial_left_hand" id="left_hand"></div>
	            <div class="initial_right_hand" id="right_hand"></div>
	        </div>
	        <p style="padding: 30px 0px 10px; position: relative;">
	            <span class="u_logo"></span> <input class="ipt" type="text" id="userName" name="userName"
	                placeholder="请输入用户名" value="">
	        </p>
	        <p style="position: relative;">
	            <span class="p_logo"></span> <input class="ipt" id="password" name="password"
	                type="password" placeholder="请输入密码" value="">
	        </p>
	        <div style="height: 50px; line-height: 50px; margin-top: 30px; border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
	            <p style="margin: 0px 35px 20px 45px;"><span style="color:red">${errorMsg}</span>
	                <span style="float: right;"> <a
	                    style="background: rgb(0, 142, 173); padding: 7px 10px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;"
	                    href="javascript:login();">登录</a>
	                </span>
	            </p>
	        </div>
        </form>
    </div>
    <div style="text-align: center;">
        <p>天天向上</p>
    </div>
</body>
</html>