package by.deniotokiari.core.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public abstract class CommonPagerFragment extends Fragment {
	
	private PagerAdapter mAdapter;
	private ViewPager mViewPager;
		
	protected abstract PagerAdapter getAdapter();
	
	protected abstract ViewPager getViewPager();
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mAdapter = getAdapter();
		mViewPager = getViewPager();
		
		mViewPager.setAdapter(mAdapter);
	}
	
	protected void seetPage(int page) {
		mViewPager.setCurrentItem(page);
	}
	
}
