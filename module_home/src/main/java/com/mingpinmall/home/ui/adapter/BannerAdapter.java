package com.mingpinmall.home.ui.adapter;

import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.home.R;
import com.mingpinmall.home.ui.bean.HomeItemBean;


/**
* @date 创建时间： 2019/4/9
* @author 创建人：小斌
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
                tvTime.setText("剩余时间:" + 0 + "天" + 23 + ":" + 59 + ":" + 59);
            }
        };
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_banner_home;
    }

}

