/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.SimpleAdapter;

import com.lpf.mysuperdemo.R;
import com.lpf.mysuperdemo.db.DbImpl;
import com.lpf.mysuperdemo.db.MySQLiteOpenHelper;
import com.lpf.mysuperdemo.db.User;
import com.lpf.mysuperdemo.util.ToastUtil;

/**
 * @Title:
 * @Description:
 * @Author:liupf5
 * @Since:2015-6-30
 * @Version:1.1.0
 */
public class DataDbActivity extends Activity implements OnClickListener {
	
	MySQLiteOpenHelper dbHelper;
	SQLiteDatabase readableDataBase;
	
	DbImpl dbImpl = new DbImpl();
	
	List<Map<String,String>> dataList = null;
	List<User> userList = new ArrayList<User>();
	
	private Button mBtn_Create;
	private Button mBtn_Add;
	private Button mBtn_Del;
	private Button mBtn_Query;
	
	private ListView mListView;
	SimpleAdapter adapter = null;
//	ArrayAdapter adapter = null;
	private EditText mEditText_name;
	private EditText mEditText_age;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_db_sqlite);

		initViews();
		
		initListeners();
		
		createDB();
	}

	/**
	 * 
	 * @Description:
	 */
	private void initViews() {
		
		mBtn_Create = (Button) this.findViewById(R.id.id_data_create);
		mBtn_Add = (Button) this.findViewById(R.id.id_data_add);
		mBtn_Del = (Button) this.findViewById(R.id.id_data_delete);
		mBtn_Query = (Button) this.findViewById(R.id.id_data_query);
		
		mEditText_name = (EditText)this.findViewById(R.id.id_et_name);
		mEditText_age = (EditText)this.findViewById(R.id.id_et_age);
		
		mListView = (ListView)this.findViewById(R.id.id_data_list);
	}
	
	/**
	 * 
	 * @Description:
	 */
	private void initListeners() {
		// TODO Auto-generated method stub
		mBtn_Create.setOnClickListener(this);
		mBtn_Add.setOnClickListener(this);
		mBtn_Del.setOnClickListener(this);
		mBtn_Query.setOnClickListener(this);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.id_data_create:
			createDB();
			break;
		case R.id.id_data_add:
			AddData();
			break;
		case R.id.id_data_delete:
			DeleteAllData();
			break;
		case R.id.id_data_query:
			QueryData();
			break;
		}
	}

	/**
	 * 
	 * @Description:
	 */
	private void createDB() {
		
		dbHelper = new MySQLiteOpenHelper(DataDbActivity.this, "mydb.db3", 1);
		readableDataBase = dbHelper.getReadableDatabase();
		
		ToastUtil.showShort(DataDbActivity.this, "数据库创建成功");
	}

	/**
	 * 
	 * @Description:
	 */
	private void AddData() {
		
		String name = mEditText_name.getText().toString();
		String age = mEditText_age.getText().toString();
		ContentValues values = new ContentValues();
		if(name.length() > 0 && age.length() >0){
			values.put("name", name);
			values.put("age", age);
			long rowId = dbHelper.insertOrUpdate("name = ? and age = ?",
					new String[] { name, age }, values);
			if (rowId != -1) {
				if(rowId == 1){
					ToastUtil.showShort(DataDbActivity.this, "数据修改成功:");
				}else{
					Map<String, String> map = new HashMap<String, String>();
					map.put("name", name);
					map.put("age", age);
					dataList.add(map);
					adapter.notifyDataSetChanged();
					ToastUtil.showShort(DataDbActivity.this, "数据新增成功:");
				}
				
			} else {
				ToastUtil.showShort(DataDbActivity.this, "数据添加（修改）失败");
			}
		}else{
			ToastUtil.showShort(DataDbActivity.this, "name和age不能为空");
		}
		
	}

	/**
	 * 
	 * @Description:
	 */
	private void DeleteAllData() {
		
		long id = dbHelper.deleteAll();
		if(id!= -1){
			ToastUtil.showShort(DataDbActivity.this, "数据删除成功");
			dataList.clear();
			adapter.notifyDataSetChanged();
		}else{
			ToastUtil.showShort(DataDbActivity.this, "数据删除失败");
		}
		
		mBtn_Del.setVisibility(View.GONE);
	}

	/**
	 * 
	 * @Description:
	 */
	private void QueryData() {
		
		if( null == dbHelper || null == readableDataBase){
			createDB();
		}
		
		dataList = dbHelper.queryUsers("select * from user", null);
		
//		Cursor cursor = readableDataBase.rawQuery("select * from user ",null);
//		dataList = dbImpl.convertCursorToList(cursor);
		
		adapter = new SimpleAdapter(DataDbActivity.this, dataList, R.layout.activity_db_sqlite_listitem,new String[]{"name","age"},new int[]{R.id.id_user_name,R.id.id_user_age});
		mListView.setAdapter(adapter);
		
		ToastUtil.showShort(DataDbActivity.this, "数据显示....");
		
		mBtn_Del.setVisibility(View.VISIBLE);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final int pos = position;
				PopupMenu popup = new PopupMenu(getBaseContext(),view);
				popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
				popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						// TODO Auto-generated method stub
						if(R.id.delete == item.getItemId()){
							long id = dbHelper.delete(dataList.get(pos).get("name"));
							dataList.remove(pos);
							adapter.notifyDataSetChanged();
							if(id!=-1){
								ToastUtil.showShort(DataDbActivity.this, "数据删除成功");
							}else{
								ToastUtil.showShort(DataDbActivity.this, "数据删除失败");
							}
							
						}
						return true;
					}
				});
				popup.show();
			}
		});
	}

}
