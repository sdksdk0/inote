package cn.tf.note.login.service;

public interface LoginService {

	/**
	 * 登录
	 * @param userName
	 * @param password
	 */
	public boolean login(String userName,String password)throws Exception;

}
