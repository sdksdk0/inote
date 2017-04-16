package cn.tf.note.login.service;

import cn.tf.note.login.bean.User;
import cn.tf.note.util.TaotaoResult;

public interface LoginService {

	/**
	 * 登录
	 * @param userName
	 * @param password
	 */
	public TaotaoResult login(String userName,String password)throws Exception;

	/**
	 * 创建用户
	 * @param user
	 */
	public TaotaoResult createUser(User user);

}
