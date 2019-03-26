package com.mingpinmall.classz.adapter;

import android.content.Context;

import com.mingpinmall.classz.ui.vm.ClassificationItemHolder;
import com.mingpinmall.classz.ui.vm.ClassificationRightItemHolder;
import com.mingpinmall.classz.ui.vm.bean.ClassificationBean;
import com.mingpinmall.classz.ui.vm.bean.ClassificationRighitBean;
import com.mingpinmall.classz.ui.vm.bean.GoodsInfo;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.footview.FootViewHolder;
import com.trecyclerview.headview.HeaderViewHolder;
import com.trecyclerview.pojo.FootVo;
import com.trecyclerview.pojo.HeaderVo;
import com.trecyclerview.progressindicator.ProgressStyle;


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

    /*-------------------------------------------------------------------首页---------------------------------------------------*/
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
    public DelegateAdapter.Builder getLeftAdapter(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(ClassificationBean.DatasBean.ClassListBean.class, new ClassificationItemHolder(context)),
                context, ProgressStyle.BallRotate);
    }

    /*三级listview*/
    public DelegateAdapter.Builder getRightAdapter(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(ClassificationRighitBean.DatasBean.ClassListBean.class,
                                new ClassificationRightItemHolder(context)),
                context, ProgressStyle.BallRotate);
    }

    public DelegateAdapter.Builder getRightAdapter1(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(ClassificationRighitBean.DatasBean.ClassListBean.ChildBean.class,
                                new ClassificationRightAdapter(context)),
                context, ProgressStyle.BallRotate);
    }


    /*分类列表里面的list*/
    public DelegateAdapter.Builder getProductsAdapter(Context context) {
        return getAdapter(new DelegateAdapter.Builder<>()
                        .bind(GoodsInfo.class, new ProductsItemHolder(context)),
                context, ProgressStyle.Pacman);
    }
//
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
