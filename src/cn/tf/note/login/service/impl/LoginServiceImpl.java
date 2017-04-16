package cn.tf.note.login.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tf.note.login.dao.LoginDao;
import cn.tf.note.login.service.LoginService;


@Service
public class LoginServiceImpl implements LoginService{

	@Resource(name="loginDaoImpl")
	private LoginDao loginDao;
	
	@Override
	public boolean login(String userName, String password) throws Exception{
		return loginDao.getLoginInfo(userName,password);
		
	}
}
