package com.mingpinmall.cart.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.goldze.common.dmvvm.widget.CouponDisplayView;
import com.mingpinmall.cart.R;
import com.mingpinmall.cart.ui.bean.AllVoucherBean;

import java.util.ArrayList;

/**
 * 功能描述：店铺代金券列表
 *
 * @author 小斌
 * @date 2019/4/20
 **/
public class VoucherListAdapter extends BaseQuickAdapter<AllVoucherBean.VoucherListBean, BaseViewHolder> {

    public VoucherListAdapter() {
        super(R.layout.item_voucher_list, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, AllVoucherBean.VoucherListBean item) {
        helper.setText(R.id.tv_storeName, item.getVoucher_t_title())
                .setText(R.id.tv_time, "有效期至:\n" + item.getVoucher_t_end_date_text())
                .setText(R.id.tv_money, "¥" + item.getVoucher_t_price())
                .setText(R.id.tv_use, "满" + item.getVoucher_t_limit() + "可用")
                .setText(R.id.tv_desc, item.getVoucher_t_desc())
                .setText(R.id.tv_type, item.getVoucher_t_gettype_text())
                .setGone(R.id.tv_type, !item.getVoucher_t_gettype_text().isEmpty())
                .setGone(R.id.tv_desc, item.isDisplay())
                .setGone(R.id.iv_stateImage, false)
                .addOnClickListener(R.id.btn_action);
        CouponDisplayView displayView = helper.getView(R.id.cdv_view);
        displayView.setState(true);
    }
}
