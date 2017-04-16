package cn.tf.note.login.service;

import cn.tf.note.login.bean.User;

public interface LoginService {

	/**
	 * 登录
	 * @param userName
	 * @param password
	 */
	public boolean login(String userName,String password)throws Exception;

	/**
	 * 创建用户
	 * @param user
	 */
	public boolean createUser(User user);

}
