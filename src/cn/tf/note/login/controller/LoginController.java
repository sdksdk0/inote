/**
 * 
 */
package cn.tf.note.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.tf.note.login.bean.User;
import cn.tf.note.login.service.LoginService;
import cn.tf.note.login.service.impl.LoginServiceImpl;
import cn.tf.note.util.ExceptionUtil;
import cn.tf.note.util.TaotaoResult;
import cn.tf.note.util.constans.Constants;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	
	LoginService  loginService=new LoginServiceImpl();
	
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	//@Resource
	//private LoginService loginService;
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showloginpage")
	public String login(HttpServletRequest request)throws Exception{
		
		return "login/login";
	}
	@RequestMapping("/showloginpage2")
	public String login2(HttpServletRequest request)throws Exception{
		
		return "login/login2";
	}
	@RequestMapping("/showloginpage3")
	public String login3(HttpServletRequest request)throws Exception{
		System.out.println("进来了");
		return "login/login3";
	}
	
	//去注册页面
	@RequestMapping("/toReg")
	public String toRegister(HttpServletRequest request)throws Exception{
		return "login/register";
	}
	
	//去注册
	@RequestMapping("/register")
	@ResponseBody
	public TaotaoResult register(HttpServletRequest request, User user,HttpSession session)throws Exception{
		
		// 创建时间戳
		Long createTime = System.currentTimeMillis();
		user.setRegistTime(createTime);
		//密码加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		TaotaoResult result;
		try {
			result = loginService.createUser(user);
		} catch (Exception e) {
			logger.error("创建用户失败：userName:"+user.getLoginName()+";");
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return result;
	}
	
	@RequestMapping("/loginnow")
	@ResponseBody
	public TaotaoResult loginin(HttpServletRequest request,String loginName,String password){
		TaotaoResult result=null;
		try {
			if (loginName==null||"".equals(loginName)||password==null||"".equals(password)) {
				return TaotaoResult.build(400, "邮箱密码不能为空或");
			}
			password=DigestUtils.md5DigestAsHex(password.getBytes());
			 result = loginService.login(loginName,password);
			 System.out.println("result.getStatus()"+result.getStatus());
			if(result.getStatus()==200){
				request.getSession().setAttribute(Constants.USER_INFO, loginName.trim());	
			}else{
				return TaotaoResult.build(400, "用户名或密码错误");
			}
		} catch (Exception e) {
			logger.error("登陆失败：loginName:"+loginName+";",e);
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		System.out.println(result.getStatus());
		return result;
	}

}
