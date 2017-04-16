package cn.tf.note.login.service.impl;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tf.note.login.bean.User;
import cn.tf.note.login.dao.LoginDao;
import cn.tf.note.login.service.LoginService;
import cn.tf.note.note.dao.DataDao;
import cn.tf.note.note.dao.RedisDao;
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
		//return loginDao.getLoginInfo(userName,password);
		
		try {
			boolean exists = RedisTools.exists("INOTE_USER_INFO:"+ userName+ Constants.STRING_SEPARATOR + password);
			System.out.println("exists"+exists);
			if(exists){
				System.out.println("用户名正确");
				return TaotaoResult.ok();
			}else{
				System.out.println("用户名或密码错误");
				return TaotaoResult.build(400, "用户名或密码错误");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@Override
	public TaotaoResult createUser(User user) {

		TaotaoResult result;
		result=addUserToRedis(user.getLoginName(), user.getPassword(),
				user.getPersonalMail(), user.getRegistTime());
		
		result=addUserToHbase(user.getLoginName(),
				user.getPassword(), user.getPersonalMail(),
				user.getRegistTime());
		return result;
		/*boolean ifsuccess = false;
		// redis是否成功
		ifsuccess = addUserToRedis(user.getLoginName(), user.getPassword(),
				user.getPersonalMail(), user.getRegistTime());
		System.out.println("ifsuccess:"+ifsuccess);
		// 如果redis成功，保存hbase
		if (ifsuccess) {
			try {
				// 保存hbase是否成功
				ifsuccess = addUserToHbase(user.getLoginName(),
						user.getPassword(), user.getPersonalMail(),
						user.getRegistTime());
				// 如果不成功，删除redis
				if (!ifsuccess) {
					deleteUserRedis(user.getLoginName(), user.getPassword(),
							user.getPersonalMail(), user.getRegistTime());
				}
			} catch (Exception e) {
				// 报异常，删除redis，返回false
				deleteUserRedis(user.getLoginName(), user.getPassword(),
						user.getPersonalMail(), user.getRegistTime());
				e.printStackTrace();
				return false;
			}
		}
		return ifsuccess;*/

	}

	private boolean deleteUserRedis(String loginName, String password,
			String personalMail, long registTime) {
		return false;
		/*boolean ifSucess = false;
		ifSucess = deleteUserFromRedis(loginName, password, personalMail,
				registTime);
		if (ifSucess) {
			try {
				ifSucess = deleteUserFromHbase(loginName, password,
						personalMail, registTime);
				if (!ifSucess) {
					addUserToRedis(loginName, password, personalMail,
							registTime);
				}
			} catch (Exception e) {
				addUserToRedis(loginName, password, personalMail, registTime);
				e.printStackTrace();
				return false;
			}
		}
		return ifSucess;*/
	}

	// 从hbase中删除
	private boolean deleteUserFromHbase(String loginName, String password,
			String personalMail, long registTime) {
		// 拼接rowkey
		String rowKey = loginName.trim();
		// 删除用户信息
		return dataDao.deleteData(Constants.USER_FAMLIY_USERINFO, rowKey);
	}

	// 添加到hbase
	private TaotaoResult addUserToHbase(String loginName, String password,
			String personalMail, long registTime) {

		// 创建rowkey
		String rowKey = loginName.trim();

		// 封装二维数组，[[famliy，qualifier，value],……………………]，调用dao的公共方法
		String famQuaVals[][] = new String[4][3]; // 4行3列，列族列值
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
		// 调用dao的公共方法
		 try {
			dataDao.insertData(Constants.USER_TABLE_NAME,
					rowKey, famQuaVals);
		} catch (Exception e) {
			return TaotaoResult.build(400, "hbase缓存错误");
		}		
		 return TaotaoResult.ok();
	}

	// 添加到redis
	private TaotaoResult addUserToRedis(String loginName, String password,
			String personalMail, long registTime) {
		StringBuffer userString = new StringBuffer();

		userString.append(loginName + Constants.STRING_SEPARATOR)
				.append(password + Constants.STRING_SEPARATOR)
				.append(personalMail).append(Constants.STRING_SEPARATOR)
				.append(registTime);
		// 保存redis，用戶名為key，笔记本信息为value
		try {
			RedisTools.set("INOTE_USER_INFO:"+ personalMail+ Constants.STRING_SEPARATOR + password, userString.toString());// 将笔记本存放到redis中
			
		} catch (Exception e) {
			return TaotaoResult.build(400, "redis缓存错误");
		}

		return TaotaoResult.ok();
		
	}

	// 从redis中删除
	private void deleteUserFromRedis(String loginName, String password,
			String personalMail, long registTime) {
		StringBuffer userString = new StringBuffer();
		// 拼笔记本信息
		userString.append(loginName + Constants.STRING_SEPARATOR)
				.append(password + Constants.STRING_SEPARATOR)
				.append(personalMail).append(Constants.STRING_SEPARATOR)
				.append(registTime);
		// 从redis中删除list中的笔记本
		RedisTools.del("INOTE_USER_INFO:"+ personalMail+ Constants.STRING_SEPARATOR+password);
	}
}
