package cn.tf.note.login.dao.impl;

import org.springframework.stereotype.Service;

import cn.tf.note.login.dao.LoginDao;
import cn.tf.note.util.RedisTools;
import cn.tf.note.util.constans.Constants;



@Service("loginDaoImpl")
public class LoginDaoImpl implements LoginDao {

	@Override
	public boolean getLoginInfo(String userName, String password) throws Exception {
		boolean flag = false;
		String userInfo = RedisTools.get(userName);
		if (userInfo!=null) {
			String[] split = userInfo.split("\\"+Constants.STRING_SEPARATOR);
			if (password.equals(split[0])) {
				flag=true;
			}
		}
		return flag;
	}

}
