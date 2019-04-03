package com.mingpinmall.me.ui;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goldze.common.dmvvm.base.event.LiveBus;
import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.ToastUtils;
import com.google.gson.Gson;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentMeBinding;
import com.mingpinmall.me.ui.adapter.MeItemAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.MeItemBean;
import com.mingpinmall.me.ui.bean.MyInfoBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.mingpinmall.me.ui.widget.AutoColorView;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的
 */
public class MeFragment extends AbsLifecycleFragment<FragmentMeBinding, MeViewModel> implements View.OnClickListener {

    private MeItemAdapter meItemAdapter;
    private AutoColorView autoColorView;
    private View headView;

    private final int[] colorIds = new int[]{
            R.color.bg_color_0,
            R.color.bg_color_1,
            R.color.bg_color_2,
            R.color.bg_color_3,
            R.color.bg_color_4,
            R.color.bg_color_5,
            R.color.bg_color_6,
            R.color.bg_color_7,
            R.color.bg_color_8,
    };

    /**
     * 我的财产下方导航，动态添加
     */
    private String[] subEstate;

    public MeFragment() {
    }

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected int getContentResId() {
        return R.id.content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        initHeadView();
        initSettingItem();
        setItemClickListener();
        setListScrollListener();

        if (SharePreferenceUtil.isLogin()) {
            mViewModel.getUserInfo();
        }
    }

    @Override
    protected void dataObserver() {
        KLog.i("绑定事件监听");
        LiveBus.getDefault().subscribe("LoginSuccess").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object isLogin) {
                KLog.i("登陆成功，刷新数据");
                mViewModel.getUserInfo();
            }
        });

        LiveBus.getDefault().subscribe("LOGIN_OUT").observeForever(new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object isLogin) {
                KLog.i("退出登录，清除数据");
                clearnDatas();
            }
        });

        LiveBus.getDefault().subscribe(Constants.EVENT_KEY_ME_GETUSERINFO, MyInfoBean.class).observeForever(new Observer<MyInfoBean>() {
            @Override
            public void onChanged(@Nullable MyInfoBean result) {
                KLog.i("获取成功，刷新展示内容。");
                setNewData(result);
                SharePreferenceUtil.saveBooleanKeyValue("needRefresh", false);
            }
        });
    }

    private void setNewData(MyInfoBean result) {
        headView.findViewById(R.id.iv_headItem1).setBackgroundColor(Color.parseColor("#00000000"));
        headView.findViewById(R.id.iv_headItem2).setBackgroundColor(Color.parseColor("#00000000"));

        MyInfoBean.DatasBean.MemberInfoBean datas = result.getDatas().getMember_info();
        SharePreferenceUtil.saveKeyValue("USER_INFO", new Gson().toJson(datas));

        ((AppCompatTextView)headView.findViewById(R.id.tv_name)).setText(datas.getUser_name());
        ((AppCompatTextView)headView.findViewById(R.id.tv_level)).setText(datas.getLevel_name());
        ((AppCompatTextView)headView.findViewById(R.id.iv_headItem1)).setText(datas.getFavorites_goods());
        ((AppCompatTextView)headView.findViewById(R.id.iv_headItem2)).setText(datas.getFavorites_store());

        ImageUtils.loadImageAsGIF((AppCompatImageView) headView.findViewById(R.id.iv_headImage), datas.getAvatar());
        meItemAdapter.getData().get(2).setSubCorner(new int[]{
                datas.getOrder_nopay_count(),
                datas.getOrder_noreceipt_count(),
                0,
                datas.getOrder_noeval_count(),
                datas.getOrder_notakes_count(),
        });
        meItemAdapter.notifyDataSetChanged();
    }

    private void clearnDatas() {
        headView.findViewById(R.id.iv_headItem1).setBackgroundResource(R.drawable.ic_me_favorite);
        headView.findViewById(R.id.iv_headItem2).setBackgroundResource(R.drawable.ic_me_store);

        SharePreferenceUtil.saveKeyValue("USER_INFO", null);

        ((AppCompatTextView)headView.findViewById(R.id.tv_name)).setText(R.string.label_click_login);
        ((AppCompatTextView)headView.findViewById(R.id.tv_level)).setText("");
        ((AppCompatTextView)headView.findViewById(R.id.iv_headItem1)).setText("");
        ((AppCompatTextView)headView.findViewById(R.id.iv_headItem2)).setText("");

        ImageUtils.loadImageCircle((AppCompatImageView) headView.findViewById(R.id.iv_headImage), R.drawable.ic_user_head);
        meItemAdapter.getData().get(2).setSubCorner(new int[]{
                0, 0, 0, 0, 0,
        });
        meItemAdapter.notifyDataSetChanged();
    }

    @Override
    protected Object getStateEventKey() {
        return "ME_FRAGMENT";
    }

    private void initHeadView() {
        //初始化适配器和头部
        meItemAdapter = new MeItemAdapter();
        headView = LayoutInflater.from(activity).inflate(R.layout.view_me_user_head, binding.recyclerView, false);
        meItemAdapter.addHeaderView(headView);
        meItemAdapter.bindToRecyclerView(binding.recyclerView);
        //拿到头部 View
        ImageUtils.loadImageCircle((AppCompatImageView) headView.findViewById(R.id.iv_headImage), R.drawable.ic_user_head);
        autoColorView = headView.findViewById(R.id.iv_bg);
        autoColorView.setColors(colorIds);
        autoColorView.start();
        headView.findViewById(R.id.iv_setting).setOnClickListener(this);
        headView.findViewById(R.id.iv_message).setOnClickListener(this);
        headView.findViewById(R.id.iv_headImage).setOnClickListener(this);
        headView.findViewById(R.id.ll_headItem1).setOnClickListener(this);
        headView.findViewById(R.id.ll_headItem2).setOnClickListener(this);
        headView.findViewById(R.id.ll_headItem3).setOnClickListener(this);
    }


    private void setListScrollListener() {
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //当前条目索引
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = layoutManager.findFirstVisibleItemPosition();
                if (position == 0) {
                    View firstView = layoutManager.findViewByPosition(position);
                    float scrollY = Math.abs(firstView.getTop());
                    float firstViewHeight = firstView.getHeight();
                    float alpha = scrollY / firstViewHeight;
                    if (alpha > 0) {
                        binding.titleBar.setVisibility(View.VISIBLE);
                        binding.titleBar.setAlpha(alpha);
                    } else {
                        binding.titleBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * 监听点击事件
     */
    private void setItemClickListener() {
        binding.ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //左上角设置 来自：界面上划后出现的覆盖层
                if (!SharePreferenceUtil.isLogin()) {
                    ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                    return;
                }
                ActivityToActivity.toActivity(ARouterConfig.SETTINGACTIVITY);
            }
        });
        binding.ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SharePreferenceUtil.isLogin()) {
                    ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                    return;
                }
                ActivityToActivity.toActivity(ARouterConfig.MESSAGEACTIVITY);
            }
        });
        /**
         * 子Item点击事件
         */
        meItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!SharePreferenceUtil.isLogin()) {
                    ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                    return;
                }
                int i = view.getId();
                int funCode = meItemAdapter.getData().get(position).getFunCode();
                if (i == R.id.ll_item1) {
//                    ToastUtils.showShort(funCode == -1 ? "点击了 待付款" : "点击了 预存款");
                    if (funCode == -1) {
                        ActivityToActivity.toActivity(ARouterConfig.ORDERACTIVITY, "pageIndex", 1);
                    } else if (funCode == -2) {
                        ActivityToActivity.toActivity(ARouterConfig.ACCOUNTSURPLUSACTIVITY);
                    }
                } else if (i == R.id.ll_item2) {
//                    ToastUtils.showShort(funCode == -1 ? "点击了 待收货" : "充值卡");
                    if (funCode == -1) {
                        ActivityToActivity.toActivity(ARouterConfig.ORDERACTIVITY, "pageIndex", 2);
                    } else if (funCode == -2) {
                        ActivityToActivity.toActivity(ARouterConfig.CARDSURPLUSACTIVITY);
                    }
                } else if (i == R.id.ll_item3) {
//                    ToastUtils.showShort(funCode == -1 ? "点击了 待自提" : "代金券");
                    if (funCode == -1) {
                        ActivityToActivity.toActivity(ARouterConfig.ORDERACTIVITY, "pageIndex", 3);
                    } else if (funCode == -2) {
                        ActivityToActivity.toActivity(ARouterConfig.COUPONACTIVITY);
                    }
                } else if (i == R.id.ll_item4) {
//                    ToastUtils.showShort(funCode == -1 ? "点击了 待评价" : "红包");
                    if (funCode == -1) {
                        ActivityToActivity.toActivity(ARouterConfig.ORDERACTIVITY, "pageIndex", 4);
                    } else if (funCode == -2) {
                        ActivityToActivity.toActivity(ARouterConfig.STOREPACKETACTIVITY);
                    }
                } else if (i == R.id.ll_item5) {
//                    ToastUtils.showShort(funCode == -1 ? "点击了 退款/退货" : "积分");
                    if (funCode == -1) {
                        ActivityToActivity.toActivity(ARouterConfig.REFUNDACTIVITY);
                    } else if (funCode == -2) {
                        ActivityToActivity.toActivity(ARouterConfig.VIPINTERGRALACTIVITY);
                    }
                }
            }
        });
        /**
         * item点击事件
         */
        meItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!SharePreferenceUtil.isLogin()) {
                    ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                    return;
                }
                MeItemBean itemBean = meItemAdapter.getData().get(position);
                if (itemBean.getItemType() == 1) {
                    switch (itemBean.getFunCode()) {
                        case 0:
//                            ToastUtils.showShort("我的订单");
                            ActivityToActivity.toActivity(ARouterConfig.ORDERACTIVITY);
                            break;
                        case 1:
//                            ToastUtils.showShort("我的财产");
                            ActivityToActivity.toActivity(ARouterConfig.PROPERTYACTIVITY);
                            break;
                        case 2:
//                            ToastUtils.showShort("我的分销管理");
                            ActivityToActivity.toActivity(ARouterConfig.DISRTIBUTIONACTIVITY);
                            break;
                        case 3:
                            ToastUtils.showShort("我的推广码");
                            break;
                        case 4:
//                            ToastUtils.showShort("收货地址管理");
                            ActivityToActivity.toActivity(ARouterConfig.ADDRESSMANAGERACTIVITY);
                            break;
                        case 5:
//                            ToastUtils.showShort("用户设置");
                            ActivityToActivity.toActivity(ARouterConfig.SETTINGACTIVITY);
                            break;
                    }
                }

            }
        });
    }

    /**
     * 初始化按钮
     */
    private void initSettingItem() {
        // init list item data.
        subEstate = new String[]{
                getString(R.string.me_estate_item1),
                getString(R.string.me_estate_item2),
                getString(R.string.me_estate_item3),
                getString(R.string.me_estate_item4),
                getString(R.string.me_estate_item5)
        };
        String[] titles = new String[]{
                getString(R.string.me_item_item1),
                getString(R.string.me_item_item2),
                getString(R.string.me_item_item3),
                getString(R.string.me_item_item4),
                getString(R.string.me_item_item5),
                getString(R.string.me_item_item6)
        };
        int[] titleImgs = new int[]{
                R.drawable.mc_01,
                R.drawable.mc_02,
                R.drawable.mc_05,
                R.drawable.mc_05,
                R.drawable.mc_03,
                R.drawable.mc_04,
        };
        int[] subImage0 = new int[]{
                R.drawable.mcc_01,
                R.drawable.mcc_02,
                R.drawable.mcc_03,
                R.drawable.mcc_04,
                R.drawable.mcc_05,
        };
        int[] subImage1 = new int[]{
                R.drawable.mcc_06_b,
                R.drawable.mcc_07_b,
                R.drawable.mcc_08_b,
                R.drawable.mcc_09_b,
                R.drawable.mcc_10_b
        };
        String[] subOrder = new String[]{
                getString(R.string.me_order_item1),
                getString(R.string.me_order_item2),
                getString(R.string.me_order_item3),
                getString(R.string.me_order_item4),
                getString(R.string.me_order_item5)
        };
        int[] funCodes = new int[]{
                0, 1, 2, 3, 4, 5
        };
        String[] titles2 = new String[]{
                getString(R.string.me_item_sub_item1),
                getString(R.string.me_item_sub_item2),
                "", "", "", "",
        };
        List<MeItemBean> data = new ArrayList<>();
        /** 空行，分隔行 **/
        MeItemBean space0 = new MeItemBean();
        space0.setItemType(2);
        data.add(space0);
        /** 按钮 **/
        for (int i = 0; i < titles.length; i++) {
            MeItemBean itemBean = new MeItemBean();
            itemBean.setItemType(1);
            itemBean.setFunCode(funCodes[i]);
            itemBean.setDrawable(titleImgs[i]);
            itemBean.setTitle(titles[i]);
            itemBean.setTitle2(titles2[i]);
            itemBean.setPoint(0);
            data.add(itemBean);
            if (i == 0 || i == 1 || i == 3 || i == 5) {
                /**
                 * 添加空白行，分隔行
                 */
                MeItemBean spaceItem = new MeItemBean();
                spaceItem.setItemType(2);
                data.add(spaceItem);
            }
        }

        /** 我的订单下方导航 **/
        MeItemBean itemBean0 = new MeItemBean();
        itemBean0.setItemType(0);
        itemBean0.setSubLabel(subOrder);
        itemBean0.setSubCorner(new int[]{0, 0, 0, 0, 0});
        itemBean0.setFunCode(-1);
        itemBean0.setSubImage(subImage0);
        data.add(2, itemBean0);

        /** 我的财产下方导航 **/
        MeItemBean orderItem = new MeItemBean();
        orderItem.setItemType(0);
        orderItem.setSubLabel(subEstate);
        orderItem.setSubCorner(new int[]{0, 0, 0, 0, 0});
        orderItem.setFunCode(-2);
        orderItem.setSubImage(subImage1);
        data.add(5, orderItem);

        meItemAdapter.setNewData(data);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("我的", "onResume: 恢复");
        if (meItemAdapter != null && meItemAdapter.getData().size() > 0) {
            Log.d("我的", "onResume: 更新");
            autoColorView.start();
        }
        if (SharePreferenceUtil.getBooleanKeyValue("needRefresh")) {
            if (SharePreferenceUtil.isLogin()) {
                mViewModel.getUserInfo();
            } else {
                clearnDatas();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("我的", "onPause: 暂停");
        if (meItemAdapter != null && meItemAdapter.getData().size() > 0) {
            autoColorView.pause();
        }
    }

    @Override
    protected void onInVisible() {
        super.onInVisible();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (!SharePreferenceUtil.isLogin()) {
            ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
            return;
        }
        if (i == R.id.iv_setting) {
//                    ToastUtils.showShort("点击了 左上角设置");
            ActivityToActivity.toActivity(ARouterConfig.SETTINGACTIVITY);
        } else if (i == R.id.iv_message) {
//                    ToastUtils.showShort("点击了 右上角消息");
            ActivityToActivity.toActivity(ARouterConfig.MESSAGEACTIVITY);
        } else if (i == R.id.ll_headItem1) {
//                    ToastUtils.showShort("点击了 商品收藏");
            ActivityToActivity.toActivity(ARouterConfig.COLLECTIONACTIVITY, "pageIndex", 0);
        } else if (i == R.id.ll_headItem2) {
//                    ToastUtils.showShort("点击了 店铺收藏");
            ActivityToActivity.toActivity(ARouterConfig.COLLECTIONACTIVITY, "pageIndex", 1);
        } else if (i == R.id.ll_headItem3) {
//                    ToastUtils.showShort("点击了 我的足迹");
            ActivityToActivity.toActivity(ARouterConfig.FOOTPRINTACTIVITY);
        } else if (i == R.id.iv_headImage) {
//                    ToastUtils.showShort("点击了 头像");
        }
    }
}
