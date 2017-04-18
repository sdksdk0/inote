package cn.tf.note.login.service.impl;


import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import cn.tf.note.login.bean.User;
import cn.tf.note.login.dao.LoginDao;
import cn.tf.note.login.service.LoginService;
import cn.tf.note.note.dao.DataDao;
import cn.tf.note.note.dao.impl.DataDaoImpl;
import cn.tf.note.util.ExceptionUtil;
import cn.tf.note.util.RedisTools;
import cn.tf.note.util.TaotaoResult;
import cn.tf.note.util.constans.Constants;


@Service
public class LoginServiceImpl implements LoginService{

	@Resource(name="loginDaoImpl")
	private LoginDao loginDao;
	
	private DataDao dataDao=new DataDaoImpl();
	
	@Override
	public TaotaoResult login(String userName, String password) throws Exception{
		
		try {
			boolean exists = RedisTools.exists("INOTE_USER_INFO:"+ userName+ Constants.STRING_SEPARATOR + password+":1");
			System.out.println("exists"+exists);
			if(exists){
				
				return TaotaoResult.ok();
			}else{
				
				return TaotaoResult.build(400, "用户名或密码错误");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@Override
	public TaotaoResult createUser(HttpServletRequest request, User user,HttpSession session) {

		TaotaoResult result;
		session.setAttribute("email",user.getPersonalMail());
		
		result=addUserToRedis(user.getLoginName(), user.getPassword(),
				user.getPersonalMail(), user.getRegistTime(),user.getType());
		
		result=addUserToHbase(user.getLoginName(),
				user.getPassword(), user.getPersonalMail(),
				user.getRegistTime(),user.getType());
		result=active(request,user,session);
		System.out.println(result);
		return result;
		
	}

	
	// 添加到hbase
	private TaotaoResult addUserToHbase(String loginName, String password,
			String personalMail, long registTime,int type) {

		// 创建rowkey
		String rowKey = loginName.trim();

		// 封装二维数组，[[famliy，qualifier，value],……………………]，调用dao的公共方法
		String famQuaVals[][] = new String[5][3]; // 5行3列，列族列值
		famQuaVals[0][0] = Constants.USER_FAMLIY_USERINFO;
		famQuaVals[0][1] = Constants.USER_NOTEBOOKINFO_CLU_USERNAME;
		famQuaVals[0][2] = loginName; // 用户名
		famQuaVals[1][0] = Constants.USER_FAMLIY_USERINFO;
		famQuaVals[1][1] = Constants.USER_NOTEBOOKINFO_CLU_CREATETIME;
		famQuaVals[1][2] = registTime + ""; // 注册时间
		famQuaVals[2][0] = Constants.USER_FAMLIY_USERINFO;
		famQuaVals[2][1] = Constants.USER_NOTEBOOKINFO_CLU_PASSWORD;
		famQuaVals[2][2] = password; // 密码
		famQuaVals[3][0] = Constants.USER_FAMLIY_USERINFO;
		famQuaVals[3][1] = Constants.USER_NOTEBOOKINFO_CLU_EMAIL;
		famQuaVals[3][2] = personalMail; // 邮箱
		famQuaVals[4][0] = Constants.USER_FAMLIY_USERINFO;
		famQuaVals[4][1] = Constants.USER_NOTEBOOKINFO_CLU_TYPE;
		famQuaVals[4][2] = type+""; // 类型
		// 调用dao的公共方法
		 try {
			dataDao.insertData(Constants.USER_TABLE_NAME,
					rowKey, famQuaVals);
		} catch (Exception e) {
			System.out.println(e);
			return TaotaoResult.build(400, "hbase缓存错误");
		}		
		 return TaotaoResult.ok();
	}

	// 添加到redis
	private TaotaoResult addUserToRedis(String loginName, String password,
			String personalMail, long registTime, int type) {
		StringBuffer userString = new StringBuffer();

		userString.append(loginName + Constants.STRING_SEPARATOR)
				.append(password + Constants.STRING_SEPARATOR)
				.append(personalMail).append(Constants.STRING_SEPARATOR)
				.append(registTime).append(Constants.STRING_SEPARATOR)
				.append(type);
		// 保存redis，用戶名為key，笔记本信息为value
		try {
			RedisTools.set("INOTE_USER_INFO:"+ personalMail+ Constants.STRING_SEPARATOR + password+ Constants.STRING_SEPARATOR +type, userString.toString());// 将笔记本存放到redis中
			
		} catch (Exception e) {
			return TaotaoResult.build(400, "redis缓存错误");
		}

		return TaotaoResult.ok();
		
	}
	
	//发送激活邮件
	public TaotaoResult active(HttpServletRequest request,User user,HttpSession session){
		
				String activeCode=UUID.randomUUID().toString();
				/*request.getSession().setAttribute("activeCode", activeCode);*/
				Object email= session.getAttribute("email");
				//把激活码存到redis中
				try {
					RedisTools.set("INOTE_ACTIVECODE_KEY:"+email+":"+user.getPassword(),activeCode);
					//设置过期时间，过期后无法激活
					RedisTools.expire("INOTE_ACTIVECODE_KEY:"+email+":"+user.getPassword(),1800);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				//session.setAttribute("activeCode", activeCode);
				String activePath=request.getRequestURL().toString().replace("login/register", "login/active/"+email+"/"+user.getPassword()+"/"+activeCode);
				
				try {
					Properties props=new Properties();
					
					props.setProperty("mail.transport.protocol", "smtp");//规范规定的参数
					props.setProperty("mail.host", "smtp.exmail.qq.com.");//
					props.setProperty("mail.smtp.auth", "true");//请求认证，不认证有可能发不出去邮件。
					
					Session session1=Session.getInstance(props);
					MimeMessage message=new MimeMessage(session1);
					
					message.setFrom(new InternetAddress("info@tianfang1314.cn"));
					message.setRecipients(Message.RecipientType.TO, user.getPersonalMail());
					
					message.setSubject("小汇云笔记用户激活");
	
					message.setContent("<a href='"+activePath+"'>小汇云笔记用户激活</a><br>如果链接无效请把激活地址复制到浏览器地址"+"<hr />"
					+"如果直接跳转到登录页面则表示激活成功，请在十分钟内进行激活!"+"激活地址:"+activePath,"text/html;charset=UTF-8");
					message.saveChanges();
					
					Transport ts = session1.getTransport();

					ts.connect("info@tianfang1314.cn", "87654320bB");
					ts.sendMessage(message, message.getAllRecipients());
					ts.close();
				
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println(e.getMessage());
				}
				return TaotaoResult.ok();
			}

	@Override
	public boolean activeUser(String email,String pd) {
		

		RedisTools.rename("INOTE_USER_INFO:"+ email+ Constants.STRING_SEPARATOR + pd+ Constants.STRING_SEPARATOR +0,
				"INOTE_USER_INFO:"+ email+ Constants.STRING_SEPARATOR + pd+ Constants.STRING_SEPARATOR +1);
		
		return true;
	}


	
	
	
	
	
}
