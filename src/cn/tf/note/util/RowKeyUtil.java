package cn.tf.note.util;

import cn.tf.note.util.constans.Constants;



public class RowKeyUtil {
	public static String getRowKeyUserName(String rowKey) {
		String[] split = rowKey.split(Constants.ROWKEY_SEPARATOR);
		String name = "";
		for (int i = 0; i < split.length - 1; i++) {
			name = name + split[i] + Constants.ROWKEY_SEPARATOR;
		}
		return name.substring(0, name.length() - 1);
	}

	public static String getRowKeyCreateTime(String rowKey) {
		String[] split = rowKey.split(Constants.ROWKEY_SEPARATOR);
		return split[split.length - 1];
	}
}
