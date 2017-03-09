package com.feicui.news.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 适配器的父类，声明和实例化一些必须的属性和方法
 * 
 * @author Administrator
 * 
 * @param <E>
 *            要保存的数据类型的泛型
 */
public class MyBaseAdapter<E> extends BaseAdapter {

	protected boolean isFling = false;

	// 集合用来保存数据
	protected List<E> list;
	// Context对象
	protected Context context;
	// LayoutInflater对象
	protected LayoutInflater inflater;

	protected static LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
			5 * 1024 * 1024) {
		protected int sizeOf(String key, Bitmap value) {

			return value.getByteCount();
		}
	};

	/**
	 * 构造方法实例化属性
	 * 
	 * @param context
	 */
	public MyBaseAdapter(Context context) {
		this.context = context;
		list = new ArrayList<E>();
		inflater = LayoutInflater.from(context);
	}

	public void setIsFling(boolean isFling) {
		this.isFling = isFling;
	}

	// 添加数据方法
	public void add(E e) {
		list.add(e);

	}

	public void add(List<E> eList) {
		list.addAll(eList);
		notifyDataSetChanged();
	}

	public List<E> getList() {
		return list;

	}

	// 清空数据方法
	public void clear() {
		list.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public E getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
