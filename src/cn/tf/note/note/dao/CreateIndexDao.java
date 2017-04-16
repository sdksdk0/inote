package cn.tf.note.note.dao;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.LockObtainFailedException;

import cn.tf.note.note.bean.Article;



public interface CreateIndexDao {
	public boolean saveNoteToLucene(Article article) throws CorruptIndexException, IOException;
	public void forceDelete() throws CorruptIndexException,
	LockObtainFailedException, IOException;
	public void delete(String id) throws CorruptIndexException, LockObtainFailedException, IOException ;
}
