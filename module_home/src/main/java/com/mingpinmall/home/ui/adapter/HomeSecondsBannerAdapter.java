package com.mingpinmall.home.ui.adapter;

import android.graphics.Paint;
import android.os.CountDownTimer;

import com.goldze.common.dmvvm.adapter.BaseBannerAdapter;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.databinding.LayoutBannerHomeBinding;
import com.mingpinmall.home.ui.bean.HomeItemBean;

/**
 * 功能描述：首页限购轮播图适配器
 * @author 小斌
 * @date 2019/5/5
 **/
public class HomeSecondsBannerAdapter extends BaseBannerAdapter<HomeItemBean.DatasBean.Goods1Bean.ItemBeanXX, LayoutBannerHomeBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.layout_banner_home;
    }

    @Override
    protected void convert(final LayoutBannerHomeBinding binding, HomeItemBean.DatasBean.Goods1Bean.ItemBeanXX item, int position) {
        binding.setData(item);
        ImageUtils.loadImage(binding.ivImage, item.getGoods_image());
        binding.tvOldPay.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        long currt = System.currentTimeMillis();
        new CountDownTimer(Long.parseLong(item.getEnd_time()) * 1000 - currt, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.tvTime.setText("剩余时间:" + formatDuring(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                binding.tvTime.setText("限时抢购结束");
            }
        }.start();
    }

    /**
     * @param
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        long days = mss / 60 / 60 / 24;
        long hours = mss / 60 / 60 % 24;
        long minutes = mss / 60 % 60;
        long seconds = mss % 60;
        return days + " 天 " + hours + " 时 " + minutes + " 分 "
                + seconds + " 秒";
    }

}
