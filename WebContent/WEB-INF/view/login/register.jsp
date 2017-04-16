<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>小汇云笔记</title>
<link rel="stylesheet" href="<%=basePath%>/styles/basic.css" />
<link href="<%=basePath%>/styles/autoemail.css" type="text/css" rel="stylesheet" />
 <script src="<%=basePath%>/javascripts/jquery-1.12.4.js"></script>
<script type="text/javascript"  src="<%=basePath%>/javascripts/login/reg.js"></script>
<script src="<%=basePath%>/javascripts/login/emailComplete.js"></script>	    
	    
</head>

<body>
    	
        <div class="ucenter clearfix">
            <div class="uc-box clearfix">
            	<div class="title_main">
                    用户注册
                    <ul class="clearfix">
                    <li class="on">邮箱注册</li>
                    </ul>
            	</div>
            	<div id="register" class="ucon">
            		 <form id="personRegForm" method="post" onsubmit="return false;">
            			<table class="log-reg-table" width="620" cellspacing="0" cellpadding="0" border="0">
            				<tbody>
            					
                                <tr>
                                    <td align="right">用户名：</td>
                                    <td>
                                        <div class="intbox">
                                        <input id="username" class="int-type w-thir" type="text" tips="用户名" name="loginName" rule="">
                                        </div>
                                        <label id="username_msg" class="labelbox seclabel" style="width:240px;"></label>
                                    </td>
                                </tr>
                                <tr>
                                     <td align="right">密码：</td>
                                    <td>
                                        <div class="intbox">
                                        <input id="password"  class="int-type w-thir" type="password" tips="密码" name="password" rule="">
                                        </div>
                                        <label id="password_msg" class="labelbox seclabel" style="width:240px"></label>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="nopad"></td>
                                    <td class="nopad">
                                        <ul class="ucr-stronger clearfix">
                                            <li id="weak">弱</li>
                                            <li id="middle">中</li>
                                            <li id="strength">强</li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">确认密码：</td>
                                    <td>
                                        <div class="intbox">
                                        <input id="reppassword" class="int-type w-thir" type="password" tips="确认密码" name="reppassword" rule="">
                                        </div>
                                        <label id="reppassword_msg" class="labelbox"></label>
                                    </td>
                                </tr>
                                <tr>
            						<td width="130" align="right">E-mail：</td>
                                    <td width="490">
                                    	<div class="toemail">
                                    	 <div class="intbox" style="position: relative;">
		                                     <div class="parentCls">
		      									  <input id="regemail" class="inputElem" type="text" name="personalMail"  />
		    								</div>
		    							</div>	
		    								 <label id="regemail_msg" class="labelbox"  style="float:left"></label>
	    								 </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right"> </td>
                                    <td class="checkbox">
                                    <p>
                                        <label>
                                        <input id="agree" class="check" type="checkbox" name="agree" value="1" checked="checked">我已阅读并同意<a class="tips" href="#">《小汇云笔记服务条款》</a>
                                        </label>
                                    </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td> </td>
                                    <td>
                                        <a id="button"   class="ubg bigtn reggrey redbig" href="javascript:registUserInfo()">注册</a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
				</div>
				<div class="uside">
                    <div class="side-top nonebor tologin">
                        <p class="tit">已有小汇云账号?</p>
                        <a class="ubg bigtn secbig hov_on" href="/inote/login/showloginpage2">登录</a>
                    </div>
               </div>
            </div>
        </div>
    </body>

</html>
