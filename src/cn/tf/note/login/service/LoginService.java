package cn.tf.note.login.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	 * @param request 
	 * @param user
	 * @param session 
	 */
	public TaotaoResult createUser(HttpServletRequest request, User user, HttpSession session);

	/**
	 * 
	 * 描述：
	 * @author 激活用户
	 * @created 2017年4月17日 下午12:19:53
	 * @since 
	 * @param usId
	 * @param pd 
	 * @return
	 */
	public boolean activeUser(String usId, String pd);

}
