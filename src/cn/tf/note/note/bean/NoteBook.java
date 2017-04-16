package cn.tf.note.note.bean;

import cn.tf.note.util.constans.Constants;



public class NoteBook {
	private String rowKey;
	private String name;
	private String createTime;
	private String status;

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(rowKey).append(Constants.STRING_SEPARATOR).append(name).append(Constants.STRING_SEPARATOR).append(createTime).append(Constants.STRING_SEPARATOR).append(status);
		return sb.toString();
	}
}
