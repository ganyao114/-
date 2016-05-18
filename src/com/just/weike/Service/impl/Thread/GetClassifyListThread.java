package com.just.weike.Service.impl.Thread;

import java.io.Serializable;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.just.weike.Dao.bean.Books;
import com.just.weike.Dao.bean.HttpTheadConfigBean;
import com.just.weike.Service.ServiceRulesException;
import com.just.weike.Service.impl.Thread.Templet.HttpThreadTemplet;
import com.just.weike.test.TestDao;
import com.just.weike.utils.StaticDataPackage;

public class GetClassifyListThread extends HttpThreadTemplet {
	
	private String classify;
	
	public GetClassifyListThread(Handler handler,String classiy) {
		super(handler);
		// TODO Auto-generated constructor stub
		this.classify = classiy;
	}

	@Override
	protected void OnRun() throws Exception {
		// TODO Auto-generated method stub
	//	if(StaticDataPackage.LoginName == null)
	//		throw new ServiceRulesException("δ��¼");
		List<Books> list = TestDao.getBooks();
		Message msg = new Message();
		msg.what = 1;
		Bundle data = new Bundle();
		data.putSerializable("list", (Serializable) list);
		msg.setData(data);
		handler.sendMessage(msg);
	}

	@Override
	protected HttpTheadConfigBean SetConfig() {
		// TODO Auto-generated method stub
		HttpTheadConfigBean configBean = new HttpTheadConfigBean
				(false, 0, "���ӳ�ʱ", "���س�ʱ","���ش���");
		return configBean; 
	}

}
