package com.app.music.common;

import android.util.SparseArray;
import android.view.View;

/**
 * 公共的ViewHolder
 * @author songqy
 * @date 2015-12-1 上午9:18:30
 * @version V2.0.0
 */
public class ViewHolder {

	/**
	 * 获取子控件
	 * @param convertView 父view
	 * @param id 子控件id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View convertView, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			convertView.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = convertView.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

}
