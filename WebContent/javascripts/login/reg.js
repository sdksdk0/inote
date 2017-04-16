
function toReg(){
	location.href="http://localhost:8100/inote/login/toReg";
	
}


var flag1=false;  //用户名
var flag2=false;   //密码
var flag3=false;
var flag4=false;   //邮箱
var flag5=false;
var flag6=false;   //确认密码




$(function () {

    $('#regemail').emailComplete({
        opacity: 1,
	     radius: 4
    });
    $('#login_name').emailComplete({
        opacity: 1,
	     radius: 4
    });
})

			  
			function checkInfo(){
				
				$("#username_msg").css("color","red");
				$("#username_msg").css("font-size","14px");
				
				$("#password_msg").css("color","red");
				$("#password_msg").css("font-size","14px");
				
				$("#reppassword_msg").css("color","red");
				$("#reppassword_msg").css("font-size","14px");
				
				$("#regemail_msg").css("color","red");
				$("#regemail_msg").css("font-size","14px");
				
				$("#regemail").bind({
					blur:function(){
						var val=$("#regemail").val();
						var reg=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/; 
						
						var tdInfo="";
				
						if(val==""){
							tdInfo="*请输入邮箱";
						}else if(!reg.test(val)){
							tdInfo="*请输入正确的邮箱";	
						}else{
							tdInfo="";	
							flag4=true;
						}
						
						$("#regemail_msg").text(tdInfo);
					},
					focus:function(){
						$("#regemail_msg").text("");
					}
				});

				$("#username").bind({
					blur:function(){
						var val=$("#username").val();
						var reg=/^[a-zA-Z\u4e00\u9fa5][a-zA-Z0-9_\u4e00\u9fa5]{2,17}$/;
						
						var tdInfo="";
				
						if(val==""){
							tdInfo="*请输入用户名";
							flag1=false;
						}else if(!reg.test(val)){
							tdInfo="*2-17位的中文、字母、数字和下划线";	
							flag1=false;
						}else{
							tdInfo="";	
							flag1=true;
						}
						
						$("#username_msg").text(tdInfo);
					},
					focus:function(){
						$("#username_msg").text("");
					}
				});
				
				
				$("#password").bind({
					blur:function(){
						var val=$("#password").val();
						var reg=/^\w{6,16}$/;
						
						var tdInfo="";
				
						if(val==""){
							tdInfo="*请输入密码";
							flag2=false;
						}else if(!reg.test(val)){
							tdInfo="*由6到16位的字母数字或下划线组成";	
							flag2=false;
						}else{
							tdInfo="";	
							flag2=true;
						}
						
						$("#password_msg").text(tdInfo);
						
					},
					focus:function(){
						$("#password_msg").text("");
					},
					change:function(){
						var val3=$("#password").val();
						AuthPasswd(val3);
					}
				});
				
				$("#reppassword").bind({
					blur:function(){
						var val=$("#password").val();
						var reval=$("#reppassword").val();
				
						if(reval==""){
							tdInfo="*密码不能为空";
							flag6=false;
						}else if(val!=reval){
							tdInfo="*两次密码不一致";	
							flag6=false;
						}else{
							tdInfo="";	
							flag6=true;
						}
						
						$("#reppassword_msg").text(tdInfo);
					},
					focus:function(){
						$("#reppassword_msg").text("");
					}
				});
			}

			window.onload=function(){
				checkInfo();
			}
			
			function addOption(node,obj){
				var opt=document.createElement("option");
				opt.appendChild(document.createTextNode(node.getAttribute("name")));
				opt.setAttribute("value",node.getAttribute("postcode"));
				obj.appendChild(opt);	
			}
			
	

	
function AuthPasswd(string) {
    if(string.length >=6) {
        if(/[a-zA-Z]+/.test(string) && /[0-9]+/.test(string) && /\W+\D+/.test(string)) {
            noticeAssign(1);
        }else if(/[a-zA-Z]+/.test(string) || /[0-9]+/.test(string) || /\W+\D+/.test(string)) {
            if(/[a-zA-Z]+/.test(string) && /[0-9]+/.test(string)) {
                noticeAssign(-1);
            }else if(/\[a-zA-Z]+/.test(string) && /\W+\D+/.test(string)) {
                noticeAssign(-1);
            }else if(/[0-9]+/.test(string) && /\W+\D+/.test(string)) {
                noticeAssign(-1);
            }else{
                noticeAssign(0);
            }
        }
    }else{
        noticeAssign(null); 
    }
}
 
function noticeAssign(num) {
    if(num == 1) {
        $('#weak').css({backgroundColor:'#009900'});
        $('#middle').css({backgroundColor:'#009900'});
        $('#strength').css({backgroundColor:'#009900'});
        $('#strength').html('很强');
       
    }else if(num == -1){
        $('#weak').css({backgroundColor:'#ffcc33'});
        $('#middle').css({backgroundColor:'#ffcc33'});
        $('#strength').css({backgroundColor:''});
        
        $('#middle').html('中');
       
    }else if(num ==0) {
        $('#weak').css({backgroundColor:'#dd0000'});
        $('#middle').css({backgroundColor:''});
        $('#strength').css({backgroundColor:''});
        $('#weak').html('弱');
  
    }else{
        $('#weak').html('&nbsp;');
        $('#middle').html('&nbsp;');
        $('#strength').html('&nbsp;');
        $('#weak').css({backgroundColor:''});
        $('#middle').css({backgroundColor:''});
        $('#strength').css({backgroundColor:''});
    }
}






$(function() {
	$("#regsafecode").focus(function(){
		$("#regsafecode").css("border-color","#eee");
		$("#regsafecode_msg").text("");
	});
	
});



	function registUserInfo(){
		
		var agree=$(":checkbox:checked").size();
		
		if(flag1==true && flag2==true && flag4==true   && flag6==true &&   agree>0 ){
			$.post("/inote/login/register",$("#personRegForm").serialize(), function(data){
				if(data.status == 200){
					alert('恭喜你注册成功,请登录');
					location="/inote/login/showloginpage2";
				}else{
					alert("注册失败！");
				}
			});
		}
	}

	
	function login(){
		var login_name=$("#login_name").val();
		var password=$("#password").val();
		var reg=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/; 
		if(login_name==""){
			alert("邮箱不能为空");
			return;
		}else  if(!reg.test(login_name)){
			alert("请输入正确的邮箱格式");
			return;
		}else  if(password==""){
			alert("密码不能为空");
		}else{
			$.post("/inote/login/loginnow",$("#login_form").serialize(), function(data){
				if(data.status == 200){
					location="/inote/note/inotecenter";
				}else{
					alert("用户名或密码错误!");
				}
			});
		}
		
		
	}


