package dao;

import java.util.List;

public interface DAO<T> {

	long insert(String sql,Object...args);
	
	void update(String sql,Object...args);
	
	T query(String sql,Object...args);
	
	List<T> queryForList(String sql,Object...args);
	
	<V> V getSingleValue(String sql,Object...args);
	
	void batch(String sql,Object[]...params);
	
	
}
