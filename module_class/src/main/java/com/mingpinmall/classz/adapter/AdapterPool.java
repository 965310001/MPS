package com.mingpinmall.classz.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.mingpinmall.classz.BR;
import com.mingpinmall.classz.DataBindItemViewHolderManager;
import com.mingpinmall.classz.R;
import com.mingpinmall.classz.ui.vm.bean.BrandListInfo;
import com.mingpinmall.classz.ui.vm.bean.ChatEmojiInfo;
import com.mingpinmall.classz.ui.vm.bean.ChatMessageInfo;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.mingpinmall.classz.ui.vm.bean.GoodsComment;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.mingpinmall.classz.ui.vm.bean.GoodsListInfo;
import com.mingpinmall.classz.ui.vm.bean.InvoiceListInfo;
import com.mingpinmall.classz.ui.vm.bean.StorePromotionInfo;
import com.mingpinmall.classz.ui.vm.bean.TypeInfo;
import com.mingpinmall.classz.ui.vm.bean.VoucherInfo;
import com.mingpinmall.classz.widget.CustomPopWindow;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.footview.FootViewHolder;
import com.trecyclerview.headview.HeaderViewHolder;
import com.trecyclerview.pojo.FootVo;
import com.trecyclerview.pojo.HeaderVo;
import com.trecyclerview.progressindicator.ProgressStyle;

import java.util.ArrayList;
import java.util.List;


/**
 * @author GuoFeng
 * @date : 2019/1/20 15:06
 * @description: Adapter适配器
 */
public class AdapterPool {
    private static AdapterPool adapterPool;

    public static AdapterPool newInstance() {
        if (adapterPool == null) {
            synchronized (AdapterPool.class) {
                if (adapterPool == null) {
                    adapterPool = new AdapterPool();
                }
            }
        }
        return adapterPool;
    }

    /*获取左边数据*/
    public DelegateAdapter.Builder getLeftAdapter(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(ClassificationBean.ClassListBean.class, new DataBindItemViewHolderManager(context, R.layout.classify_item_of_list, BR.data)),
                context, ProgressStyle.Pacman);
    }

    /*获取右边的数据*/
    public DelegateAdapter.Builder getRightAdapter(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(String.class, new DataBindItemViewHolderManager(context, R.layout.item_text2, BR.data))
                        .bind(ClassificationRighitBean.DatasBean.ClassListBean.class, new DataBindItemViewHolderManager(context, R.layout.item_classify_classic, BR.data))
                        .bind(BrandListInfo.DatasBean.BrandListBean.class, new DataBindItemViewHolderManager(context, R.layout.item_classify_brand, BR.data)),
                context, ProgressStyle.Pacman);
    }

    /*分类列表里面的list*/
    public DelegateAdapter.Builder getProductsAdapter(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(String.class, new DataBindItemViewHolderManager(context, R.layout.item_image, BR.data))
                        .bind(GoodsInfo.class, new DataBindItemViewHolderManager(context, R.layout.item_products, BR.data)),
                context, ProgressStyle.Pacman);
    }

    public DelegateAdapter.Builder getProductsGridAdapter(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(String.class, new DataBindItemViewHolderManager(context, R.layout.item_image, BR.data))
                        .bind(GoodsInfo.class, new DataBindItemViewHolderManager(context, R.layout.item_products_grid, BR.data)),
                context, ProgressStyle.Pacman);
    }

    /*评价列表*/
    public DelegateAdapter.Builder getEvaluate(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(ArrayList.class, new DataBindItemViewHolderManager(context, R.layout.item_of_goods_comment_list_flowtaglayout, BR.data))
                        .bind(GoodsComment.class, new DataBindItemViewHolderManager(context, R.layout.item_of_goods_comment_list, BR.data)),
                context, ProgressStyle.Pacman);
    }

    /*店铺推荐*/
    public DelegateAdapter.Builder getRecommend(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(GoodsInfo.class, new DataBindItemViewHolderManager(context, R.layout.market_item_of_goods_recommend_list, BR.data)),
                context, ProgressStyle.Pacman);
    }

    /*确认订单*/
    public DelegateAdapter.Builder getConfirmOrder(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(GoodsInfo.class, new DataBindItemViewHolderManager(context, R.layout.item_products_confirm, BR.data)),
                context, ProgressStyle.Pacman);
    }

    /*获取发票列表*/
    public DelegateAdapter.Builder getInvoiceList(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(InvoiceListInfo.InvoiceListBean.class, new DataBindItemViewHolderManager(context, R.layout.item_invoice, BR.data)),
                context, ProgressStyle.Pacman);
    }

    /*店铺首页*/
    public DelegateAdapter.Builder getStoreHomeAdapter(Context context) {
        return new DelegateAdapter.Builder<>()
                .bind(TypeInfo.class, new DataBindItemViewHolderManager(context, R.layout.item_type, BR.data))
                .bind(GoodsListInfo.class, new DataBindItemViewHolderManager(context, R.layout.item_list_info, BR.data))
                .bind(GoodsInfo.class, new DataBindItemViewHolderManager(context, R.layout.item_store, BR.data));
    }

    /*商品上新*/
    public DelegateAdapter.Builder getStoreNewGoodsAdapter(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(String.class, new DataBindItemViewHolderManager(context, R.layout.item_text, BR.data))
                        .bind(GoodsInfo.class, new DataBindItemViewHolderManager(context, R.layout.item_store, BR.data)),
                context, ProgressStyle.Pacman);
    }

    /*店铺活动*/
    public DelegateAdapter.Builder getStorePromotionAdapter(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(StorePromotionInfo.DatasBean.PromotionBean.MansongBean.class,
                                new DataBindItemViewHolderManager(context, R.layout.item_manson, BR.data))
                        .bind(StorePromotionInfo.DatasBean.PromotionBean.XianshiBean.class,
                                new DataBindItemViewHolderManager(context, R.layout.item_xianshi, BR.data)),
                context, ProgressStyle.Pacman);
    }

    /*代金券*/
    public DelegateAdapter.Builder getVoucherInfoAdapter(Context context) {
        return new DelegateAdapter.Builder<>()
                .bind(VoucherInfo.VoucherListBean.class, new DataBindItemViewHolderManager(context, R.layout.item_voucherinfo, BR.data));
    }

    /*聊天*/
    public DelegateAdapter.Builder getChatAdapter(Context context) {
        return new DelegateAdapter.Builder<>()
                .bind(ChatMessageInfo.class, new DataBindItemViewHolderManager(context, R.layout.item_chat_message_list, BR.data))
                .bind(ChatEmojiInfo.class, new DataBindItemViewHolderManager(context, R.layout.item_emoji_grid, BR.data))
                .bind(GoodsInfo.class, new DataBindItemViewHolderManager(context, R.layout.item_goods_chat, BR.data));
    }

    /*Emoji表情包*/
    public DelegateAdapter.Builder getEmojiAdapter(Context context) {
        return new DelegateAdapter.Builder<>()
                .bind(ChatEmojiInfo.class, new DataBindItemViewHolderManager(context, R.layout.item_emoji_grid, BR.data));
    }


    /*-------------------------------------------------------------------首页---------------------------------------------------*/
//    public DelegateAdapter.Builder getRightAdapter1(Context context) {
//        return getAdapter(new DelegateAdapter.Builder<>()
//                        .bind(ClassificationRighitBean.DatasBean.ClassListBean.ChildBean.class, new DataBindItemViewHolderManager(context, R.layout.item_classify, BR.data))
//                        .bind(BrandListInfo.DatasBean.BrandListBean.class, new DataBindItemViewHolderManager(context, R.layout.item_classify_brand, BR.data)),
//                context, ProgressStyle.Pacman);
//    }
//    public DelegateAdapter.Builder getHomeAdapter(Context context) {
//        return getNoFootAdapter(new DelegateAdapter.Builder<>()
//                        .bind(Banner.class, new BannerItemView(context))
//                        .bind(CatagoryInfo.class, new CategoryItemView(context))
//                        .bind(TypeInfo.class, new TypeItemView(context))
//                        .bind(GoodsInfo.class, new VendorItemView(context)),
//                context, ProgressStyle.Pacman);
////                .bind(TypeVo.class, new TypeItemView(context))
////                .bind(BookList.class, new BookItemHolder(context))
////                .bind(CourseInfoVo.class, new CourseItemHolder(context))
////                .bind(LiveRecommendVo.class, new HomeLiveItemView(context))
////                .bind(MatreialSubjectVo.class, new HomeMaterialItemView(context)),
//    }

    /*订单*/
//    public DelegateAdapter.Builder getOrderAdapter(Context context) {
//        return getNoFootAdapter(new DelegateAdapter.Builder().bind(OrderInfo.OrdersBean.class,
//                new OrderItemView(context)),
//                context, ProgressStyle.BallPulse);
//    }

    /*红包*/
//    public DelegateAdapter.Builder getRedAdapter(Context context) {
//        return getAdapter(new DelegateAdapter.Builder().bind(RedItemInfo.CashgiftsBean.class,
//                new RedItemView(context)),
//                context, ProgressStyle.Pacman);
//    }

    /*搜索list*/
//    public DelegateAdapter.Builder getSearchListAdapter(Context context) {
//        return getNoFootAdapter(new DelegateAdapter.Builder().bind(SearchInfo.DataBean.DatasBean.class,
//                new SearchItemView(context)),
//                context, ProgressStyle.Pacman);
//    }

    /*public DelegateAdapter.Builder getAdapter(Context context, Class clazz,
                                              AbsItemHolder absItemHolder, int style) {
        return getNoFootAdapter(new DelegateAdapter.Builder().bind(clazz, absItemHolder), context, style);
    }*/

    /*------------------------------------------------------------------分类---------------------------------------------------*/
//    public DelegateAdapter.Builder getLeftAdapter(Context context) {
//        return getAdapter(new DelegateAdapter.Builder<>()
//                        .bind(ClassificationBean.DatasBean.ClassListBean.class, new ClassificationItemHolder(context)),
//                context, ProgressStyle.BallRotate);
//    }
    /*三级listview*/
//    public DelegateAdapter.Builder getRightAdapter(Context context) {
//        return getAdapter(new DelegateAdapter.Builder<>()
//                        .bind(ClassificationRighitBean.DatasBean.ClassListBean.class,
//                                new ClassificationRightItemHolder(context))
//                        .bind(BrandListInfo.DatasBean.BrandListBean.class,
//                                new ClassificationRightBrandAdapter(context)),
//                context, ProgressStyle.BallRotate);
//    }
//        public DelegateAdapter.Builder getRightAdapter1(Context context) {
//        return getAdapter(new DelegateAdapter.Builder<>()
//                        .bind(ClassificationRighitBean.DatasBean.ClassListBean.ChildBean.class,
//                                new ClassificationRightAdapter(context))
//                        .bind(BrandListInfo.DatasBean.BrandListBean.class,
//                                new ClassificationRightBrandAdapter(context)),
//                context, ProgressStyle.BallRotate);
//    }
//    public DelegateAdapter.Builder getProductsAdapter(Context context) {
//        return getAdapter(new DelegateAdapter.Builder<>()
//                        .bind(GoodsInfo.class, new ProductsItemHolder(context)),
//                context, ProgressStyle.Pacman);
//    }

//    /*配送方式*/
//    public DelegateAdapter.Builder getDistributionAdapter(Context context) {
//        return getNoFootAdapter(new DelegateAdapter.Builder<>()
//                        .bind(DistributionInfo.VendorsBean.class,
//                                new DistributionInfoItemHolder(context)),
//                context, ProgressStyle.Pacman);
//    }
//
//    /*分类列表里面的list*/
//    public DelegateAdapter.Builder getProductsAdapter(Context context) {
//        return getAdapter(new DelegateAdapter.Builder<>()
//                        .bind(GoodsInfo.class, new ProductsItemHolder(context)),
//                context, ProgressStyle.Pacman);
//    }

    /*-------------------------------------------------------------------购物车-------------------------------------------------*/

    /*物流*/
//    public DelegateAdapter.Builder getDeliverInfoAdapter(Context context) {
//        return getNoFootAdapter(new DelegateAdapter.Builder<>()
//                        .bind(DeliverInfo.TracesBean.class,
//                                new DeliverInfoItemHolder(context)),
//                context, ProgressStyle.Pacman);
//    }

    /*订单详情*/
//    public DelegateAdapter.Builder getOrderDetailsAdapter(Context context) {
//        class ViewHolder extends AbsHolder {
//            TextView tvName;
//            TextView tvNum;
//            TextView tvPrice;
//            TextView tvGetAll;
//
//            private ViewHolder(View view) {
//                super(view);
//                tvName = getViewById(R.id.tv_name);
//                tvNum = getViewById(R.id.tv_num);
//                tvPrice = getViewById(R.id.tv_price);
//                tvGetAll = getViewById(R.id.tv_get_all);
//            }
//        }
//        return new DelegateAdapter.Builder<>()
//                .bind(AddressInfo.class, new OrderAddressItemHolder(context))
//                .bind(GoodsInfo.class, new OrderDetailsInfoItemHolder(context))
//                .bind(Boolean.class, new AbsItemHolder<Boolean, ViewHolder>(context) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final Boolean b) {
//                        holder.tvName.setVisibility(View.GONE);
//                        holder.tvPrice.setVisibility(View.GONE);
//                        holder.tvNum.setVisibility(View.GONE);
//                        holder.tvGetAll.setVisibility(View.VISIBLE);
//                        holder.tvGetAll.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                holder.tvGetAll.setText(b ? "收收" : "加载全部");
//                                LiveBus.getDefault().postEvent("OrderDetailsActivity", "OrderDetailsActivity", !b);
//                            }
//                        });
//                    }
//
//                    @Override
//                    public int getLayoutResId() {
//                        return R.layout.item_order_details;
//                    }
//
//                    @Override
//                    public ViewHolder createViewHolder(View view) {
//                        return new ViewHolder(view);
//                    }
//
//                })
//                .bind(OrderDetailsInfo.OrderInfoBean.class, new OrderDetailsInfoFootItemHolder(context));
//    }
//
//    /*-------------------------------------------------------------------我的---------------------------------------------------*/
//    /*地址列表*/
//    public DelegateAdapter.Builder getAddressAdapter(Context context) {
//        return getNoFootAdapter(new DelegateAdapter.Builder<>()
//                        .bind(AddressInfo.class,
//                                new AddressInfoItemHolder(context)),
//                context, ProgressStyle.Pacman);
//    }
//
//    /*提现记录*/
//    public DelegateAdapter.Builder getExtractListAdapter(Context context) {
//        return getNoFootAdapter(new DelegateAdapter.Builder<>()
//                        .bind(ExtractInfo.DataBean.class, new ExtractInfoInfoItemHolder(context)),
//                context, ProgressStyle.Pacman);
//    }
//
//    /*资金明细*/
//    public DelegateAdapter.Builder getDefiniteAdapter(Context context) {
//        return getAdapter(new DelegateAdapter.Builder<>()
//                        .bind(DefiniteInfo.BalancesBean.class,
//                                new DefiniteInfoItemHolder(context)),
//                context, ProgressStyle.Pacman);
//    }
//
//    /*售后列表*/
//    public DelegateAdapter.Builder getAfterSalesListAdapter(Context context) {
//        return getAdapter(new DelegateAdapter.Builder<>()
//                        .bind(AfterSalesInfo.class, new AfterSalesInfoItemHolder(context)),
//                context, ProgressStyle.Pacman);
//    }
//
//    /*可售后商品*/
//    public DelegateAdapter.Builder getAfterSalesAdapter(Context context, boolean is) {
//        return getAdapter(new DelegateAdapter.Builder<>()
//                        .bind(AfterSalesInfo.class, new AfterSalesInfoItemHolder(context, is)),
//                context, ProgressStyle.Pacman);
//    }
//    /*-------------------------------------------------------------------end---------------------------------------------------*/
//
    private DelegateAdapter.Builder getAdapter(DelegateAdapter.Builder builder,
                                               Context context,
                                               int mProgressStyle) {
        return builder.bind(HeaderVo.class, new HeaderViewHolder(context, mProgressStyle)).
                bind(FootVo.class, new FootViewHolder(context, mProgressStyle));
    }

    private DelegateAdapter.Builder getNoFootAdapter(DelegateAdapter.Builder builder,
                                                     Context context,
                                                     int mProgressStyle) {
        return builder.bind(HeaderVo.class,
                new HeaderViewHolder(context, mProgressStyle));
    }
}
