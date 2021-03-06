package com.mingpinmall.me.ui;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.goldze.common.dmvvm.base.mvvm.AbsLifecycleFragment;
import com.goldze.common.dmvvm.constants.ARouterConfig;
import com.goldze.common.dmvvm.utils.ActivityToActivity;
import com.goldze.common.dmvvm.utils.ImageUtils2;
import com.goldze.common.dmvvm.utils.ResourcesUtils;
import com.goldze.common.dmvvm.utils.SharePreferenceUtil;
import com.goldze.common.dmvvm.utils.StatusBarUtils;
import com.goldze.common.dmvvm.utils.log.QLog;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mingpinmall.me.R;
import com.mingpinmall.me.databinding.FragmentMeBinding;
import com.mingpinmall.me.ui.adapter.MeItemAdapter;
import com.mingpinmall.me.ui.api.MeViewModel;
import com.mingpinmall.me.ui.bean.MeItemBean;
import com.mingpinmall.me.ui.bean.MyInfoBean;
import com.mingpinmall.me.ui.constants.Constants;
import com.mingpinmall.me.ui.widget.AutoColorView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 我的
 *
 * @author 小斌
 */
public class MeFragment extends AbsLifecycleFragment<FragmentMeBinding, MeViewModel> implements View.OnClickListener {

    private MeItemAdapter meItemAdapter;
    private AutoColorView autoColorView;
    private View headView;
    private boolean darkMode = false;
    private AppCompatImageView headImgView;

    private AppCompatTextView ivHeadItem1, ivHeadItem2;
    private AppCompatTextView tvName, tvLevel;

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
        setTitlePadding(binding.titleBar);
        setDarkMode(false);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            if (SharePreferenceUtil.isLogin()) {
                mViewModel.getUserInfo();
            } else {
                refreshLayout.finishRefresh(false);
            }
        });

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
        super.dataObserver();
        registerObserver(ARouterConfig.LOGIN_SUCCESS, Object.class).observeForever(isLogin -> mViewModel.getUserInfo());

        registerObserver(ARouterConfig.LOGIN_OUT, Object.class).observeForever(isLogin -> clearnDatas());

        registerObserver(Constants.GET_USER_INFO, Object.class).observeForever(result -> {
            if (result instanceof String) {
                binding.refreshLayout.finishRefresh(false);
            } else {
                binding.refreshLayout.finishRefresh();
                MyInfoBean myInfoBean = (MyInfoBean) result;
                setNewData(myInfoBean);
                SharePreferenceUtil.saveBooleanKeyValue("needRefresh", false);
            }
        });
    }

    private void setNewData(MyInfoBean result) {
        if (result == null) {
            return;
        }
        MyInfoBean.MemberInfoBean data = result.getMember_info();
        SharePreferenceUtil.saveKeyValue("USER_INFO", new Gson().toJson(data));

        tvName.setText(data.getUser_name());
        tvLevel.setText(data.getLevel_name());
        tvLevel.setVisibility("".equals(data.getLevel_name()) ? View.GONE : View.VISIBLE);

        ivHeadItem1.setBackgroundColor(Color.parseColor("#00000000"));
        ivHeadItem2.setBackgroundColor(Color.parseColor("#00000000"));

        ivHeadItem1.setText(data.getFavorites_goods());
        ivHeadItem2.setText(data.getFavorites_store());

        /*ImageUtils.loadImageCircle(headImgView, data.getAvatar());*/
        ImageUtils2.loadImage(headImgView, data.getAvatar(), ImageUtils2.ImageType.CIRCLE);
        meItemAdapter.getData().get(2).setSubCorner(new int[]{
                data.getOrder_nopay_count(),
                data.getOrder_noreceipt_count(),
                data.getOrder_notakes_count(),
                data.getOrder_noeval_count(),
                0
        });
        meItemAdapter.notifyDataSetChanged();
    }

    private void clearnDatas() {
        try {
            ivHeadItem1.setBackgroundResource(R.drawable.ic_me_favorite);
            ivHeadItem2.setBackgroundResource(R.drawable.ic_me_store);
            tvLevel.setVisibility(View.GONE);
        } catch (Exception e) {
            QLog.i(e.toString());
        }
        SharePreferenceUtil.saveKeyValue("USER_INFO", null);

        tvName.setText(R.string.label_click_login);
        tvLevel.setText("");

        ivHeadItem1.setText("");
        ivHeadItem2.setText("");

        headImgView.setImageResource(R.drawable.ic_user_head);
        meItemAdapter.getData().get(2).setSubCorner(new int[]{
                0, 0, 0, 0, 0
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

        ivHeadItem1 = headView.findViewById(R.id.iv_headItem1);
        ivHeadItem2 = headView.findViewById(R.id.iv_headItem2);

        tvName = headView.findViewById(R.id.tv_name);
        tvLevel = headView.findViewById(R.id.tv_level);

        //拿到头部 View
        headImgView = headView.findViewById(R.id.iv_headImage);
        autoColorView = headView.findViewById(R.id.iv_bg);
        //设置控件上部增加一个状态栏的高度
        StatusBarUtils.setPaddingSmart(activity, headView.findViewById(R.id.top_btn_content));
        StatusBarUtils.setMargin(activity, headImgView);
        headImgView.setImageResource(R.drawable.ic_user_head);
        autoColorView.setColors(colorIds);
        //添加headview
        meItemAdapter.addHeaderView(headView);
        meItemAdapter.bindToRecyclerView(binding.recyclerView);
        //开始自动变换背景色
        autoColorView.start();
        //设置监听
        headImgView.setOnClickListener(this);
        headView.findViewById(R.id.iv_setting).setOnClickListener(this);
        headView.findViewById(R.id.iv_message).setOnClickListener(this);
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
                        darkMode = alpha > 128;
                        setDarkMode(darkMode);
                    } else {
                        binding.titleBar.setVisibility(View.GONE);
                        darkMode = false;
                        setDarkMode(false);
                    }
                }
            }
        });
    }

    /**
     * 监听点击事件
     */
    private void setItemClickListener() {
        binding.ivSetting.setOnClickListener(v -> {
            //点击了设置按钮 来自：界面上划后出现的覆盖层
            if (!SharePreferenceUtil.isLogin()) {
                ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                return;
            }
            ActivityToActivity.toActivity(ARouterConfig.Me.SETTINGACTIVITY);
        });
        binding.ivMessage.setOnClickListener(v -> {
            if (!SharePreferenceUtil.isLogin()) {
                ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                return;
            }
            //点击了消息按钮  来自：界面上划后出现的覆盖层
            ActivityToActivity.toActivity(ARouterConfig.Me.MESSAGEACTIVITY);
        });
        meItemAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (!SharePreferenceUtil.isLogin()) {
                ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                return;
            }
            //item子控件点击事件
            int i = view.getId();
            int funCode = meItemAdapter.getData().get(position).getFunCode();
            router2Activity(i, funCode);
        });
        meItemAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!SharePreferenceUtil.isLogin()) {
                ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
                return;
            }
            MeItemBean itemBean = meItemAdapter.getData().get(position);
            if (itemBean.getItemType() == 1) {
                //item点击事件
                router2Activity(0, itemBean.getFunCode());
            }
        });
    }

    /**
     * 导航到某个页面
     *
     * @param viewId  点击的ViewId
     * @param funCode 功能码
     */
    private void router2Activity(int viewId, int funCode) {
        if (viewId == 0) {
            //主item点击
            switch (funCode) {
                case 0:
                    //我的订单
                    ActivityToActivity.toActivity(ARouterConfig.Me.ORDERACTIVITY);
                    break;
                case 1:
                    //我的财产
                    ActivityToActivity.toActivity(ARouterConfig.Me.PROPERTYACTIVITY);
                    break;
                case 2:
                    //我的分销管理
                    ActivityToActivity.toActivity(ARouterConfig.Me.DISRTIBUTIONACTIVITY);
                    break;
                case 3:
                    //我的推广码
                    ActivityToActivity.toActivity(ARouterConfig.Me.REDUCECASHACTIVITY);
                    break;
                case 4:
                    //收货地址管理
                    ActivityToActivity.toActivity(ARouterConfig.Me.ADDRESSMANAGERACTIVITY);
                    break;
                case 5:
                    //用户设置
                    ActivityToActivity.toActivity(ARouterConfig.Me.SETTINGACTIVITY);
                    break;
                default:
                    break;
            }
        } else {
            //item子控件点击
            if (viewId == R.id.ll_item1) {
                //点击了 待付款 or 点击了 预存款
                if (funCode == -1) {
                    ActivityToActivity.toActivity(ARouterConfig.Me.ORDERACTIVITY, "pageIndex", 1);
                } else if (funCode == -2) {
                    ActivityToActivity.toActivity(ARouterConfig.Me.ACCOUNTSURPLUSACTIVITY);
                }
            } else if (viewId == R.id.ll_item2) {
                //点击了 待收货 or 充值卡
                if (funCode == -1) {
                    ActivityToActivity.toActivity(ARouterConfig.Me.ORDERACTIVITY, "pageIndex", 2);
                } else if (funCode == -2) {
                    ActivityToActivity.toActivity(ARouterConfig.Me.CARDSURPLUSACTIVITY);
                }
            } else if (viewId == R.id.ll_item3) {
                //点击了 待自提 or 代金券
                if (funCode == -1) {
                    ActivityToActivity.toActivity(ARouterConfig.Me.ORDERACTIVITY, "pageIndex", 3);
                } else if (funCode == -2) {
                    ActivityToActivity.toActivity(ARouterConfig.Me.COUPONACTIVITY);
                }
            } else if (viewId == R.id.ll_item4) {
                //点击了 待评价 or 红包
                if (funCode == -1) {
                    ActivityToActivity.toActivity(ARouterConfig.Me.ORDERACTIVITY, "pageIndex", 4);
                } else if (funCode == -2) {
                    ActivityToActivity.toActivity(ARouterConfig.Me.STOREPACKETACTIVITY);
                }
            } else if (viewId == R.id.ll_item5) {
                //点击了 退款/退货 or 积分
                if (funCode == -1) {
                    ActivityToActivity.toActivity(ARouterConfig.Me.REFUNDACTIVITY);
                } else if (funCode == -2) {
                    ActivityToActivity.toActivity(ARouterConfig.Me.VIPINTERGRALACTIVITY);
                }
            }
        }
    }

    /**
     * 初始化按钮
     */
    private void initSettingItem() {
        // init list item data.
        //我的财产下方导航文字
        String[] subEstate = ResourcesUtils.getInstance().getStringArray(R.array.home_me_estate_items);
        //我的财产下方导航图标
        TypedArray subImage1 = ResourcesUtils.getInstance().obtainTypedArray(R.array.home_me_estates_image);
        //列表item文字
        String[] titles = ResourcesUtils.getInstance().getStringArray(R.array.home_me_items);
        //列表item图标
        TypedArray titleImgs = ResourcesUtils.getInstance().obtainTypedArray(R.array.home_me_items_image);
        //列表item功能码
        int[] funCodes = ResourcesUtils.getInstance().getIntArray(R.array.home_me_item_funcode);
        //我的订单下方导航文字
        String[] subOrder = ResourcesUtils.getInstance().getStringArray(R.array.home_me_order_items);
        //我的订单下方导航图标
        TypedArray subImage0 = ResourcesUtils.getInstance().obtainTypedArray(R.array.home_me_orders_image);
        //列表item右侧副标题文字
        String[] titles2 = ResourcesUtils.getInstance().getStringArray(R.array.home_me_item_sublabel);

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
            itemBean.setDrawable(titleImgs.getResourceId(i, 0));
            itemBean.setTitle(titles[i]);
            itemBean.setTitle2(titles2[i]);
            itemBean.setPoint(0);
            data.add(itemBean);
            if (i == 0 || i == 1 || i == 2 || i == 4) {
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
    protected void onVisible() {
        setDarkMode(darkMode);
        if (meItemAdapter != null && meItemAdapter.getData().size() > 0) {
            Log.d("我的", "开始播放");
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
    protected void onInVisible() {
        if (meItemAdapter != null && meItemAdapter.getData().size() > 0) {
            Log.d("我的", "暂停播放");
            autoColorView.pause();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (!SharePreferenceUtil.isLogin()) {
            ActivityToActivity.toActivity(ARouterConfig.LOGINACTIVITY);
            return;
        }
        if (i == R.id.iv_setting) {
            //点击了 左上角设置
            ActivityToActivity.toActivity(ARouterConfig.Me.SETTINGACTIVITY);
        } else if (i == R.id.iv_message) {
            //点击了 右上角消息
            ActivityToActivity.toActivity(ARouterConfig.Me.MESSAGEACTIVITY);
        } else if (i == R.id.ll_headItem1) {
            //点击了 商品收藏
            ActivityToActivity.toActivity(ARouterConfig.Me.COLLECTIONACTIVITY, "pageIndex", 0);
        } else if (i == R.id.ll_headItem2) {
            //点击了 店铺收藏
            ActivityToActivity.toActivity(ARouterConfig.Me.COLLECTIONACTIVITY, "pageIndex", 1);
        } else if (i == R.id.ll_headItem3) {
            //点击了 我的足迹
            ActivityToActivity.toActivity(ARouterConfig.Me.FOOTPRINTACTIVITY);
        } else if (i == R.id.iv_headImage) {
            //点击了 头像
            PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                    .imageSpanCount(3)
                    .selectionMode(PictureConfig.SINGLE)
                    .previewImage(true)
                    .isCamera(true)
                    .sizeMultiplier(0.5f)
                    .compress(true)
                    .hideBottomControls(false)
                    .isGif(true)
                    .enableCrop(true)
                    // int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .withAspectRatio(1, 1)
                    // 裁剪框是否可拖拽
                    .freeStyleCropEnabled(true)
                    // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropFrame(true)
                    // 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .showCropGrid(true)
                    // 是否圆形裁剪 true or false
                    .circleDimmedLayer(true)
                    .minimumCompressSize(100)
                    // 裁剪是否可旋转图片
                    .rotateEnabled(true)
                    // 裁剪是否可放大缩小图片
                    .scaleEnabled(true)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    String headImagePath = "";
                    for (LocalMedia lm : selectList) {
                        if (lm.isCompressed()) {
                            headImagePath = lm.getCompressPath();
                        } else if (lm.isCut()) {
                            headImagePath = lm.getCutPath();
                        } else {
                            headImagePath = lm.getPath();
                        }
                    }
//                    PictureSelector.create(this).themeStyle(R.style.picture_default_style).openExternalPreview(0, selectList);
                    break;
                default:
                    break;
            }
        }
    }
}
