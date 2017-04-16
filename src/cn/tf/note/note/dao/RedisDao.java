package cn.tf.note.note.dao;

import java.util.List;

import cn.tf.note.note.bean.Note;
import cn.tf.note.note.bean.NoteBook;



public interface RedisDao {

	public List<NoteBook> getNotebook(String key);

	public List<Note> getNote(String key) ;

	public boolean saveNotebookToRedis(String userName, String string);

	public boolean delNotebookToRedis(String userName, String string);

	public boolean updateNotebookToRedis(String key, String oldValue,
			String newValue);

	public boolean saveNoteToRedis(String noteBookRowkey, String string);

	public boolean delNoteToRedis(String noteBookRowkey, String string);

	public boolean updateNoteToRedis(String noteBookRowkey, String string,
			String string2);

}
