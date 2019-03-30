//package com.goldze.common.dmvvm.activity;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.goldze.common.dmvvm.R;
//import com.goldze.common.dmvvm.base.mvvm.base.BaseActivity;
//import com.goldze.common.dmvvm.base.mvvm.base.BaseFragment;
//import com.goldze.common.dmvvm.databinding.ActivityHorizontalTabBinding;
//import com.goldze.common.dmvvm.databinding.ActivityTabSegmentScrollableBinding;
//import com.xuexiang.xui.adapter.FragmentAdapter;
//import com.xuexiang.xui.widget.tabbar.EasyIndicator;
//import com.xuexiang.xui.widget.tabbar.TabSegment;
//
//import java.util.List;
//
///**
// * 固定Fragment
// */
//public class TabSegmentScrollableModeActivity extends BaseActivity<ActivityTabSegmentScrollableBinding> {
//
//    protected TabSegment mTabSegment;
//    protected ViewPager mViewPager;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_tab_segment_scrollable;
//    }
//
//    private FragmentPagerAdapter mPagerAdapter = new FragmentPagerAdapter() {
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public Fragment getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public int getCount() {
//            return length;
//        }
//
//        @Override
//        public Object instantiateItem(final ViewGroup container, int position) {
////            MultiPage page = MultiPage.getPage(position);
////            View view = getPageView(page);
////            view.setTag(page);
////            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            container.addView(view, params);
//            return view;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//
//        @Override
//        public int getItemPosition(@NonNull Object object) {
//            View view = (View) object;
//            Object page = view.getTag();
//            if (page instanceof MultiPage) {
//                int pos = ((MultiPage) page).getPosition();
//                if (pos >= mCurrentItemCount) {
//                    return POSITION_NONE;
//                }
//                return POSITION_UNCHANGED;
//            }
//            return POSITION_NONE;
//        }
//    };
//
//    int length;
//
//    @Override
//    protected void initViews(Bundle savedInstanceState) {
//        mTabSegment = binding.tabSegment;
//        mViewPager = binding.viewPager;
//
//        length = getTabTitles().length;
//        for (String title : getTabTitles()) {
//            mTabSegment.addTab(new TabSegment.Tab(title));
//        }
//        mTabSegment.setMode(TabSegment.MODE_SCROLLABLE);
//        mViewPager.setAdapter(mPagerAdapter);
//        mTabSegment.setupWithViewPager(mViewPager, false);
////        mEasyIndicator.setTabTitles(getTabTitles());
////        mEasyIndicator.setViewPager(mViewPager,
////                new FragmentAdapter<>(getSupportFragmentManager(),
////                        getTabFragments()));
//        mViewPager.setOffscreenPageLimit(getTabTitles().length);
//    }
//
//    /**
//     * 根据需求对象Fragment
//     *
//     * @param position
//     */
//    protected void onPageSelected(int position) {
//        mViewPager.setCurrentItem(position);
////        mEasyIndicator.onPageSelected(position);
//    }
//
//    protected abstract String[] getTabTitles();
//
//    protected abstract List<BaseFragment> getTabFragments();
//
//}
