//package com.mingpinmall.classz.adapter;
//
//import android.support.v7.widget.AppCompatImageView;
//
//import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.goldze.common.dmvvm.utils.ImageUtils;
//import com.mingpinmall.classz.R;
//import com.mingpinmall.classz.ui.vm.bean.StoreHomeInfo;
//import com.mingpinmall.classz.ui.vm.bean.StoreInfo;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 店铺首页
// */
//public class StoreHomeListAdapter extends BaseMultiItemQuickAdapter<StoreHomeInfo.DatasBean, BaseViewHolder> {
//    public StoreHomeListAdapter() {
//        this(new ArrayList<StoreHomeInfo.DatasBean>());
//    }
//
//    public StoreHomeListAdapter(List<StoreHomeInfo.DatasBean> data) {
//        super(data);
//        addItemType(0, R.layout.item_text);
//        /*收藏排行*/
//        addItemType(R.layout.view_home_goods);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, StoreHomeInfo.DatasBean item) {
//        switch (helper.getItemViewType()) {
//            case 0:
//                helper.setText(R.id.text, item.getTitle());
//                break;
//            default:
//                List<StoreInfo.RecGoodsListBean> recGoodsList = item.getRec_goods_list();
//                for (StoreInfo.RecGoodsListBean recGoodsListBean : recGoodsList) {
//                    helper.setText(R.id.tv_label, recGoodsListBean.getGoods_name())
//                            .setText(R.id.tv_money, recGoodsListBean.getGoods_promotion_price())
//                            .setVisible(R.id.tv_tips, false);
//                    ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_image), recGoodsListBean.getGoods_image());
//                }
////                HomeItemBean.DatasBean.GoodsBean.ItemBean goodsBean = item.getGoodsItemBean();
////                viewHolder.setText(R.id.tv_label, goodsBean.getGoods_name())
////                        .setText(R.id.tv_money, goodsBean.getGoods_promotion_price())
////                        .setVisible(R.id.tv_tips, false);
////                ImageUtils.loadImage((AppCompatImageView) helper.getView(R.id.iv_image), goodsBean.getGoods_image());
//                break;
//        }
//    }
//}
