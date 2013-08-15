package by.deniotokiari.core.adapter.cursor;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseCursorAdapter extends CursorAdapter {

	protected abstract void bindData(View view, Context context, Cursor cursor,
			ViewHolder holder);

	protected abstract int[] getViewsIds();

	public BaseCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (!mCursor.moveToPosition(position)) {
			throw new IllegalStateException("couldn't move cursor to position "
					+ position);
		}
		View view = null;
		if (convertView == null) {
			view = newView(mContext, mCursor, parent);
			ViewHolder holder = getViewHolder(view);
			view.setTag(holder);
		} else {
			view = convertView;
		}
		bindView(view, mContext, mCursor);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		bindData(view, context, cursor, holder);
	}

	protected ViewHolder getViewHolder(View view) {
		ViewHolder holder = new ViewHolder();
		int[] ids = getViewsIds();
		for (int id : ids) {
			holder.add(id, view.findViewById(id));
		}
		return holder;
	}

	public class ViewHolder {

		private SparseArray<View> mViews;

		public ViewHolder() {
			mViews = new SparseArray<View>();
		}

		public void add(int id, View view) {
			mViews.put(id, view);
		}

		public View getViewById(int id) {
			return mViews.get(id);
		}
	}
	
}
