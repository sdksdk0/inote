package cn.tf.note.login.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import cn.tf.note.login.bean.User;
import cn.tf.note.login.dao.LoginDao;
import cn.tf.note.note.dao.DataDao;
import cn.tf.note.note.dao.RedisDao;
import cn.tf.note.util.ExceptionUtil;
import cn.tf.note.util.RedisTools;
import cn.tf.note.util.TaotaoResult;
import cn.tf.note.util.constans.Constants;

@Service("loginDaoImpl")
public class LoginDaoImpl implements LoginDao {


	@Override
	public TaotaoResult getLoginInfo(String userName, String password)
			throws Exception {
		try {
			RedisTools.exists("INOTE_USER_INFO:"+ userName+ Constants.STRING_SEPARATOR + password);
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

}

