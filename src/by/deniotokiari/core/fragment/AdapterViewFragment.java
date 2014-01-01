package by.deniotokiari.core.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.*;

/*
    Adapter View fragment with pagination, header, footer and other staff
 */
public abstract class AdapterViewFragment extends AbstractFragment implements AdapterView.OnItemClickListener,
        AbsListView.OnScrollListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String KEY_RES_HEADER = "key:res_header";
    public static final String KEY_RES_FOOTER = "key:res_footer";
    public static final String KEY_RES_PROGRESS = "key:res_progress";

    private boolean isLoading = false;
    private boolean isFirst = true;

    private View mHeader;
    private View mFooter;
    private View mProgress;
    private AdapterView mAdapterView;

    private Adapter mAdapter;

    public abstract Adapter getAdapter();

    @Override
    public void onViewCreated(View view) {
        mAdapterView = (AdapterView) view;

        if (getArguments() != null) {
            Bundle args = getArguments();
            if (args.containsKey(KEY_RES_FOOTER)) {
                initFooter(args.getInt(KEY_RES_FOOTER));
                setFooterVisibility(View.INVISIBLE);
            }
            if (args.containsKey(KEY_RES_HEADER)) {
                initHeader(args.getInt(KEY_RES_HEADER));
                setHeaderVisibility(View.INVISIBLE);
            }
            if (args.containsKey(KEY_RES_PROGRESS)) {
                initProgress(args.getInt(KEY_RES_PROGRESS));
                setProgressVisibility(View.VISIBLE);
            }
        }

        mAdapterView.setOnItemClickListener(this);
        if (mAdapterView instanceof AbsListView) {
            ((AbsListView) mAdapterView).setOnScrollListener(this);
        }

        mAdapter = getAdapter();
        if (mAdapter instanceof CursorAdapter) {
            this.getLoaderManager().initLoader(hashCode(), null, this);
        }
    }

    public void load() {
        isLoading = true;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;//new CursorLoader(getActivity(), );
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (cursor.getCount() > 0) {
            isFirst = false;
            if (mAdapterView.getAdapter() == null) {
                mAdapterView.setAdapter(mAdapter);
            }
            ((CursorAdapter) mAdapter).swapCursor(cursor);
            setProgressVisibility(View.GONE);
            isLoading = false;
        } else if (isFirst && cursor.getCount() == 0) {
            isLoading = false;
            load();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        ((CursorAdapter) mAdapter).swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    public void initProgress(int res) {
        mProgress = View.inflate(getActivity(), res, null);
    }

    public void setProgressVisibility(int visibility) {
        if (mProgress != null) {
            mProgress.setVisibility(visibility);
        }
    }

    public void addHeader() {
        if (mAdapterView instanceof ListView && mHeader != null && mAdapterView.getAdapter() == null) {
            ((ListView) mAdapterView).addHeaderView(mHeader, null, false);
        }
    }

    public void removeHeader() {
        if (mAdapterView instanceof ListView && mHeader != null) {
            ((ListView) mAdapterView).removeHeaderView(mHeader);
        }
    }

    public void initHeader(int res) {
        mHeader = View.inflate(getActivity(), res, null);
    }

    public View getHeader() {
        return mHeader;
    }

    public void addFooter() {
        if (mAdapterView instanceof ListView && mFooter != null && mAdapterView.getAdapter() == null) {
            ((ListView) mAdapterView).addFooterView(mFooter, null, false);
        }
    }

    public void removeFooter() {
        if (mAdapterView instanceof ListView && mFooter != null) {
            ((ListView) mAdapterView).removeFooterView(mFooter);
        }
    }

    public void initFooter(int res) {
        mFooter = View.inflate(getActivity(), res, null);
    }

    public View getFooter() {
        return mFooter;
    }

    public void setHeaderVisibility(int visibility) {
        if (mHeader != null) {
            mHeader.setVisibility(visibility);
        }
    }

    public void setFooterVisibility(int visibility) {
        if (mFooter != null) {
            mFooter.setVisibility(visibility);
        }
    }

}
