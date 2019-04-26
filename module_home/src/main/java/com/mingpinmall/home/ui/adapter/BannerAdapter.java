package com.mingpinmall.home.ui.adapter;

import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.ui.bean.HomeItemBean;
import com.xuexiang.xui.utils.CountDownButtonHelper;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author 创建人：小斌
 * @date 创建时间： 2019/4/9
 * @Description 描述：限购产品轮播
 * @Version
 **/
public class BannerAdapter implements CBViewHolderCreator {

    @Override
    public Holder createHolder(View itemView) {

        return new Holder<HomeItemBean.DatasBean.Goods1Bean.ItemBeanXX>(itemView) {

            AppCompatImageView imageView;
            AppCompatTextView tvLabel;
            AppCompatTextView tvNewPay;
            AppCompatTextView tvOldPay;
            AppCompatTextView tvTime;
            CountDownTimer countDownTimer;

            @Override
            protected void initView(View itemView) {
                imageView = itemView.findViewById(R.id.iv_image);
                tvLabel = itemView.findViewById(R.id.tv_label);
                tvNewPay = itemView.findViewById(R.id.tv_newPay);
                tvOldPay = itemView.findViewById(R.id.tv_oldPay);
                tvTime = itemView.findViewById(R.id.tv_time);
            }

            @Override
            public void updateUI(HomeItemBean.DatasBean.Goods1Bean.ItemBeanXX data) {
                ImageUtils.loadImage(imageView, data.getGoods_image());
                tvLabel.setText(data.getGoods_name());
                tvNewPay.setText(data.getXianshi_price());
                tvOldPay.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvOldPay.setText(data.getGoods_price());

                long currt = System.currentTimeMillis();
                Log.i("时间戳", "updateUI: " + System.currentTimeMillis());

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                countDownTimer =
                        new CountDownTimer(Long.parseLong(data.getEnd_time()) * 1000 - currt, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                Log.i("剩余多少秒", "onTick: " + millisUntilFinished);
                                tvTime.setText("剩余时间:" + formatDuring(millisUntilFinished / 1000));
                            }

                            @Override
                            public void onFinish() {
                                tvTime.setText("限时抢购结束");
                            }
                        }.start();
            }
        };
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_banner_home;
    }


    /**
     * @param
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        long days = mss / 60 / 60 / 24;
        long hours = mss /60 / 60 % 24;
        long minutes = mss / 60 % 60;
        long seconds = mss % 60;
        return days + " 天 " + hours + " 时 " + minutes + " 分 "
                + seconds + " 秒";
    }

    /**
     * @param begin 时间段的开始
     * @param end   时间段的结束
     * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
     * @author fy.zhang
     */
    public static String formatDuring(Date begin, Date end) {

        return formatDuring(end.getTime() - begin.getTime());

    }


}

