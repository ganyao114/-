package com.just.weike.test;

import java.util.ArrayList;
import java.util.List;

import com.just.weike.Dao.bean.Books;
import com.just.weike.Dao.bean.ClassifyBean;
import com.just.weike.Dao.bean.PagerViewBean;
import com.just.weike.Dao.bean.PositionBean;
import com.just.weike.Dao.bean.TokeBean;

public class TestDao {

	public TestDao() {
		// TODO Auto-generated constructor stub
	}
	
	public static PagerViewBean getPagerViewBean()
	{	
		PagerViewBean bean = new PagerViewBean();
		bean.setTitle(new String[]{"����1","����2","����3","����4","����5"});
		bean.setImgsrc(new String[]{"http://down1.sucaitianxia.com/ppt/19/ppt5045.jpg","http://pic.58pic.com/58pic/16/84/10/558PICc58PICQ9N_1024.jpg"
				,"http://www.yanj.cn/upload/editor/20140120160119_13197.png","http://www.yanj.cn/upload/store/goods/144/144_869c84546a5fd9ca77cbf016c94f151e.jpg_max.jpg","http://pic25.nipic.com/20121209/9252150_194258033000_2.jpg"});
		return bean;
	}
	
	public static List<Books> getBooks()
	{
		List<Books> books = new ArrayList<Books>();
		books.add(new Books(1, "���������ϵͳ", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 1, "����һ�����ڼ��������ϵͳ����",20));
		books.add(new Books(1, "����ԭ��", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 0, "����һ�����ڼ��������ϵͳ����",20));
		books.add(new Books(1, "Android��̻���", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 1, "����һ�����ڼ��������ϵͳ����",20));
		books.add(new Books(1, "���������ϵͳ", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 1, "����һ�����ڼ��������ϵͳ����",20));
		books.add(new Books(1, "���������ϵͳ", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 0, "����һ�����ڼ��������ϵͳ����",20));
		books.add(new Books(1, "���������ϵͳ", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 0, "����һ�����ڼ��������ϵͳ����",20));
		books.add(new Books(1, "���������ϵͳ", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 0, "����һ�����ڼ��������ϵͳ����",20));
		books.add(new Books(1, "���������ϵͳ", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 1, "����һ�����ڼ��������ϵͳ����",20));
		books.add(new Books(1, "���������ϵͳ", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 1, "����һ�����ڼ��������ϵͳ����",20));
		books.add(new Books(1, "���������ϵͳ", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 0, "����һ�����ڼ��������ϵͳ����",20));
		books.add(new Books(1, "���������ϵͳ", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 1, "����һ�����ڼ��������ϵͳ����",20));
		books.add(new Books(1, "���������ϵͳ", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 1, "����һ�����ڼ��������ϵͳ����",20));
		books.add(new Books(1, "���������ϵͳ", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 1, "����һ�����ڼ��������ϵͳ����",20));
		books.add(new Books(1, "���������ϵͳ", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 1, "����һ�����ڼ��������ϵͳ����",20));
		return books;
	}
	
	public static List<ClassifyBean> getClassifyBeans()
	{
		List<ClassifyBean> list = new ArrayList<ClassifyBean>();
		list.add(new ClassifyBean("Java", "http://img.web07.cn/uploads/QQ/c111018/131Y02B2620-2V19.png"));
		list.add(new ClassifyBean("C", "http://img.web07.cn/uploads/QQ/c111018/131Y02B2620-2V19.png"));
		list.add(new ClassifyBean("C++", "http://img.web07.cn/uploads/QQ/c111018/131Y02B2620-2V19.png"));
		list.add(new ClassifyBean("C#", "http://img.web07.cn/uploads/QQ/c111018/131Y02B2620-2V19.png"));
		list.add(new ClassifyBean("Physon", "http://img.web07.cn/uploads/QQ/c111018/131Y02B2620-2V19.png"));
		list.add(new ClassifyBean("JSP", "http://img.web07.cn/uploads/QQ/c111018/131Y02B2620-2V19.png"));
		list.add(new ClassifyBean("ARM ASM", "http://img.web07.cn/uploads/QQ/c111018/131Y02B2620-2V19.png"));
		return list;
	}
	
	public static List<PositionBean> getPositionBeans(String kind)
	{	
		List<PositionBean> list = new ArrayList<PositionBean>();
		switch (kind) {
		case "init":
			list.add(new PositionBean("J", "����", "province"));
			break;
		case "province":
			list.add(new PositionBean("N", "�Ͼ�", "city"));
			list.add(new PositionBean("Z", "��", "city"));
			break;
		case "city":
			list.add(new PositionBean("J", "���տƼ���ѧ", "college"));
			list.add(new PositionBean("J", "���մ�ѧ", "college"));
			break;
		case "college":
			list.add(new PositionBean("J", "�������ѧ�뼼��", "subject"));
			list.add(new PositionBean("R", "�������", "subject"));
			break;
		case "subject":
			list.add(new PositionBean("D", "��һ", "grade"));
			list.add(new PositionBean("D", "���", "grade"));
			list.add(new PositionBean("D", "����", "grade"));
			list.add(new PositionBean("D", "����", "grade"));
			list.add(new PositionBean("Y", "��һ", "grade"));
			list.add(new PositionBean("Y", "�ж�", "grade"));
			list.add(new PositionBean("Y", "����", "grade"));
			break;
		default:
			break;
		}
		
		return list;
	}

	public static Books GetBook()
	{
		return new Books(1, "���������ϵͳ", "��Ң", "15MB", "����Ա", "PDF", null, "2015/9/13", 15, 1, "����һ�����ڼ��������ϵͳ����",20);
	}
	
	public static List<TokeBean> getToke()
	{
		List<TokeBean> list = new ArrayList<TokeBean>();
		list.add(new TokeBean("�����", "admin", 1, "���ԣ��Ȿ����ɶ��˼", "2015/9/13", 0, 0, 6, 0));
		list.add(new TokeBean("�����", "admin", 1, "���ԣ��Ȿ����ɶ��˼", "2015/9/13", 0, 0, 6, 0));
		list.add(new TokeBean("�����", "admin", 1, "���ԣ��Ȿ����ɶ��˼", "2015/9/13", 0, 0, 6, 0));
		list.add(new TokeBean("�����", "admin", 1, "���ԣ��Ȿ����ɶ��˼", "2015/9/13", 0, 0, 6, 0));
		list.add(new TokeBean("�����", "admin", 1, "���ԣ��Ȿ����ɶ��˼", "2015/9/13", 0, 0, 6, 0));
		list.add(new TokeBean("�����", "admin", 1, "���ԣ��Ȿ����ɶ��˼", "2015/9/13", 0, 0, 6, 0));
		list.add(new TokeBean("�����", "admin", 1, "���ԣ��Ȿ����ɶ��˼", "2015/9/13", 0, 0, 6, 0));
		list.add(new TokeBean("�����", "admin", 1, "���ԣ��Ȿ����ɶ��˼", "2015/9/13", 0, 0, 6, 0));
		list.add(new TokeBean("�����", "admin", 1, "���ԣ��Ȿ����ɶ��˼", "2015/9/13", 0, 0, 6, 0));
		list.add(new TokeBean("�����", "admin", 1, "���ԣ��Ȿ����ɶ��˼", "2015/9/13", 0, 0, 6, 0));
		return list;
	}
	
}
