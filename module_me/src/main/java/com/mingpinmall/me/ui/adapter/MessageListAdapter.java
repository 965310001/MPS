package com.mingpinmall.me.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.MessageListBean;

import java.util.ArrayList;

/**
 * 功能描述：消息列表适配器
 * @author 小斌
 * @date 2019/4/28
 **/
public class MessageListAdapter extends BaseQuickAdapter<MessageListBean.ListBean, BaseViewHolder> {
    public MessageListAdapter() {
        super(R.layout.item_message, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean.ListBean item) {
        ImageUtils.loadImage(helper.getView(R.id.iv_image), item.getAvatar());
        helper.setText(R.id.tv_name, item.getU_name())
                .setText(R.id.tv_time, item.getTime())
                .setText(R.id.tv_desc, item.getT_msg())
                .addOnClickListener(R.id.iv_delete);
    }
}
