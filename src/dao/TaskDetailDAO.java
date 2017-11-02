package dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import po.Doc;
import po.TaskDetail;

public interface TaskDetailDAO {
	//批量写入detail
	public void batchSave(Collection<TaskDetail> taskDetails);
	//批量读取details
	public Set<TaskDetail> getTaskDetails(Integer id);
	//根据id获取Task
	public TaskDetail getTaskDetail(Integer id);
	//获取指定资源对应的敏感词数
	public long getWordNum(String docpath);
	
	//对于通过审核的文档，删除该文档在tradedetail中的记录
	public void deletedocdetail(String docpath);
	//统计指定任务下被检索出的文档数
	public long getDocNumber(Integer id);
	//统计指定任务下检索出的敏感词总数
	public long getWordNumByTaskId(Integer taskId);
	//统计指定任务下检索出的敏感词
	public List<String> getWordsByTaskId(Integer taskId);
	//统计指定任务下检索出的敏感词去重后的总数
	public long getWordDistinctNumByTaskId(Integer taskId);
	
	public long getDiscoveredTaskNum();
	
	public long getDiscoveredWordNum();
}
