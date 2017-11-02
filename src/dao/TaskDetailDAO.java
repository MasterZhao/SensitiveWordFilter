package dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import po.Doc;
import po.TaskDetail;

public interface TaskDetailDAO {
	//����д��detail
	public void batchSave(Collection<TaskDetail> taskDetails);
	//������ȡdetails
	public Set<TaskDetail> getTaskDetails(Integer id);
	//����id��ȡTask
	public TaskDetail getTaskDetail(Integer id);
	//��ȡָ����Դ��Ӧ�����д���
	public long getWordNum(String docpath);
	
	//����ͨ����˵��ĵ���ɾ�����ĵ���tradedetail�еļ�¼
	public void deletedocdetail(String docpath);
	//ͳ��ָ�������±����������ĵ���
	public long getDocNumber(Integer id);
	//ͳ��ָ�������¼����������д�����
	public long getWordNumByTaskId(Integer taskId);
	//ͳ��ָ�������¼����������д�
	public List<String> getWordsByTaskId(Integer taskId);
	//ͳ��ָ�������¼����������д�ȥ�غ������
	public long getWordDistinctNumByTaskId(Integer taskId);
	
	public long getDiscoveredTaskNum();
	
	public long getDiscoveredWordNum();
}
