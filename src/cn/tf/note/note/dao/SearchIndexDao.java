package cn.tf.note.note.dao;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import cn.tf.note.note.bean.Article;
import cn.tf.note.note.bean.SearchBean;



public interface SearchIndexDao {
	public List<Article> searchIndex(SearchBean searchBean) throws ParseException, IOException, InvalidTokenOffsetsException;
}
