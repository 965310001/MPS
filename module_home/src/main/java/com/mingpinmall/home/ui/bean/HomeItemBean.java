package com.mingpinmall.home.ui.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：
 * 创建人：小斌
 * 创建时间: 2019/4/3
 **/
public class HomeItemBean implements Serializable {

    private int code;
    private String error;
    private List<DatasBean> datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean implements MultiItemEntity, Serializable {

        private int itemType;

        /**
         * 导航ITEM, 布局C，
         * @return
         */
        private String image;
        private String type;
        private String data;

        private Goods1Bean.ItemBeanXX goods1ItemBean;

        public Goods1Bean.ItemBeanXX getGoods1ItemBean() {
            return goods1ItemBean;
        }

        public void setGoods1ItemBean(Goods1Bean.ItemBeanXX goods1ItemBean) {
            this.goods1ItemBean = goods1ItemBean;
        }

        public String getImage() {
            return image == null ? "" : image;
        }

        public void setImage(String image) {
            this.image = image == null ? "" : image;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type == null ? "" : type;
        }

        public String getData() {
            return data == null ? "" : data;
        }

        public void setData(String data) {
            this.data = data == null ? "" : data;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        private AdvListBean adv_list;
        private Home6Bean home6;
        private Home2Bean home2;
        private Home4Bean home4;
        private Home5Bean home5;
        private Goods1Bean goods1;
        private Goods2Bean goods2;
        private Home1Bean home1;
        private Home3Bean home3;
        private GoodsBean goods;

        public AdvListBean getAdv_list() {
            return adv_list;
        }

        public void setAdv_list(AdvListBean adv_list) {
            this.adv_list = adv_list;
        }

        public Home6Bean getHome6() {
            return home6;
        }

        public void setHome6(Home6Bean home6) {
            this.home6 = home6;
        }

        public Home2Bean getHome2() {
            return home2;
        }

        public void setHome2(Home2Bean home2) {
            this.home2 = home2;
        }

        public Home4Bean getHome4() {
            return home4;
        }

        public void setHome4(Home4Bean home4) {
            this.home4 = home4;
        }

        public Home5Bean getHome5() {
            return home5;
        }

        public void setHome5(Home5Bean home5) {
            this.home5 = home5;
        }

        public Goods1Bean getGoods1() {
            return goods1;
        }

        public void setGoods1(Goods1Bean goods1) {
            this.goods1 = goods1;
        }

        public Goods2Bean getGoods2() {
            return goods2;
        }

        public void setGoods2(Goods2Bean goods2) {
            this.goods2 = goods2;
        }

        public Home1Bean getHome1() {
            return home1;
        }

        public void setHome1(Home1Bean home1) {
            this.home1 = home1;
        }

        public Home3Bean getHome3() {
            return home3;
        }

        public void setHome3(Home3Bean home3) {
            this.home3 = home3;
        }

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public static class AdvListBean implements Serializable {
            private List<ItemBean> item;

            public List<ItemBean> getItem() {
                return item;
            }

            public void setItem(List<ItemBean> item) {
                this.item = item;
            }

            public static class ItemBean {

                private String image;
                private String type;
                private String data;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getData() {
                    return data;
                }

                public void setData(String data) {
                    this.data = data;
                }
            }
        }

        public static class Home6Bean implements Serializable {

            private String title;
            private List<ItemBeanX> item;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ItemBeanX> getItem() {
                return item;
            }

            public void setItem(List<ItemBeanX> item) {
                this.item = item;
            }

            public static class ItemBeanX {

                private String image;
                private String type;
                private String data;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getData() {
                    return data;
                }

                public void setData(String data) {
                    this.data = data;
                }
            }
        }

        public static class Home2Bean implements Serializable {

            private String title;
            private String square_image;
            private String square_type;
            private String square_data;
            private String rectangle1_image;
            private String rectangle1_type;
            private String rectangle1_data;
            private String rectangle2_image;
            private String rectangle2_type;
            private String rectangle2_data;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSquare_image() {
                return square_image;
            }

            public void setSquare_image(String square_image) {
                this.square_image = square_image;
            }

            public String getSquare_type() {
                return square_type;
            }

            public void setSquare_type(String square_type) {
                this.square_type = square_type;
            }

            public String getSquare_data() {
                return square_data;
            }

            public void setSquare_data(String square_data) {
                this.square_data = square_data;
            }

            public String getRectangle1_image() {
                return rectangle1_image;
            }

            public void setRectangle1_image(String rectangle1_image) {
                this.rectangle1_image = rectangle1_image;
            }

            public String getRectangle1_type() {
                return rectangle1_type;
            }

            public void setRectangle1_type(String rectangle1_type) {
                this.rectangle1_type = rectangle1_type;
            }

            public String getRectangle1_data() {
                return rectangle1_data;
            }

            public void setRectangle1_data(String rectangle1_data) {
                this.rectangle1_data = rectangle1_data;
            }

            public String getRectangle2_image() {
                return rectangle2_image;
            }

            public void setRectangle2_image(String rectangle2_image) {
                this.rectangle2_image = rectangle2_image;
            }

            public String getRectangle2_type() {
                return rectangle2_type;
            }

            public void setRectangle2_type(String rectangle2_type) {
                this.rectangle2_type = rectangle2_type;
            }

            public String getRectangle2_data() {
                return rectangle2_data;
            }

            public void setRectangle2_data(String rectangle2_data) {
                this.rectangle2_data = rectangle2_data;
            }
        }

        public static class Home4Bean implements Serializable {

            private String title;
            private String rectangle1_image;
            private String rectangle1_type;
            private String rectangle1_data;
            private String rectangle2_image;
            private String rectangle2_type;
            private String rectangle2_data;
            private String square_image;
            private String square_type;
            private String square_data;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getRectangle1_image() {
                return rectangle1_image;
            }

            public void setRectangle1_image(String rectangle1_image) {
                this.rectangle1_image = rectangle1_image;
            }

            public String getRectangle1_type() {
                return rectangle1_type;
            }

            public void setRectangle1_type(String rectangle1_type) {
                this.rectangle1_type = rectangle1_type;
            }

            public String getRectangle1_data() {
                return rectangle1_data;
            }

            public void setRectangle1_data(String rectangle1_data) {
                this.rectangle1_data = rectangle1_data;
            }

            public String getRectangle2_image() {
                return rectangle2_image;
            }

            public void setRectangle2_image(String rectangle2_image) {
                this.rectangle2_image = rectangle2_image;
            }

            public String getRectangle2_type() {
                return rectangle2_type;
            }

            public void setRectangle2_type(String rectangle2_type) {
                this.rectangle2_type = rectangle2_type;
            }

            public String getRectangle2_data() {
                return rectangle2_data;
            }

            public void setRectangle2_data(String rectangle2_data) {
                this.rectangle2_data = rectangle2_data;
            }

            public String getSquare_image() {
                return square_image;
            }

            public void setSquare_image(String square_image) {
                this.square_image = square_image;
            }

            public String getSquare_type() {
                return square_type;
            }

            public void setSquare_type(String square_type) {
                this.square_type = square_type;
            }

            public String getSquare_data() {
                return square_data;
            }

            public void setSquare_data(String square_data) {
                this.square_data = square_data;
            }
        }

        public static class Home5Bean implements Serializable {

            private String title;
            private String stitle;
            private String lcurl;
            private String square_image;
            private String square_type;
            private String square_data;
            private String rectangle1_image;
            private String rectangle1_type;
            private String rectangle1_data;
            private String rectangle2_image;
            private String rectangle2_type;
            private String rectangle2_data;
            private String rectangle3_image;
            private String rectangle3_type;
            private String rectangle3_data;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getStitle() {
                return stitle;
            }

            public void setStitle(String stitle) {
                this.stitle = stitle;
            }

            public String getLcurl() {
                return lcurl;
            }

            public void setLcurl(String lcurl) {
                this.lcurl = lcurl;
            }

            public String getSquare_image() {
                return square_image;
            }

            public void setSquare_image(String square_image) {
                this.square_image = square_image;
            }

            public String getSquare_type() {
                return square_type;
            }

            public void setSquare_type(String square_type) {
                this.square_type = square_type;
            }

            public String getSquare_data() {
                return square_data;
            }

            public void setSquare_data(String square_data) {
                this.square_data = square_data;
            }

            public String getRectangle1_image() {
                return rectangle1_image;
            }

            public void setRectangle1_image(String rectangle1_image) {
                this.rectangle1_image = rectangle1_image;
            }

            public String getRectangle1_type() {
                return rectangle1_type;
            }

            public void setRectangle1_type(String rectangle1_type) {
                this.rectangle1_type = rectangle1_type;
            }

            public String getRectangle1_data() {
                return rectangle1_data;
            }

            public void setRectangle1_data(String rectangle1_data) {
                this.rectangle1_data = rectangle1_data;
            }

            public String getRectangle2_image() {
                return rectangle2_image;
            }

            public void setRectangle2_image(String rectangle2_image) {
                this.rectangle2_image = rectangle2_image;
            }

            public String getRectangle2_type() {
                return rectangle2_type;
            }

            public void setRectangle2_type(String rectangle2_type) {
                this.rectangle2_type = rectangle2_type;
            }

            public String getRectangle2_data() {
                return rectangle2_data;
            }

            public void setRectangle2_data(String rectangle2_data) {
                this.rectangle2_data = rectangle2_data;
            }

            public String getRectangle3_image() {
                return rectangle3_image;
            }

            public void setRectangle3_image(String rectangle3_image) {
                this.rectangle3_image = rectangle3_image;
            }

            public String getRectangle3_type() {
                return rectangle3_type;
            }

            public void setRectangle3_type(String rectangle3_type) {
                this.rectangle3_type = rectangle3_type;
            }

            public String getRectangle3_data() {
                return rectangle3_data;
            }

            public void setRectangle3_data(String rectangle3_data) {
                this.rectangle3_data = rectangle3_data;
            }
        }

        public static class Goods1Bean implements Serializable {

            private String title;
            private List<ItemBeanXX> item;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ItemBeanXX> getItem() {
                return item;
            }

            public void setItem(List<ItemBeanXX> item) {
                this.item = item;
            }

            public static class ItemBeanXX {

                private int time;
                private String goods_image;

                public int getTime() {
                    return time;
                }

                public void setTime(int time) {
                    this.time = time;
                }

                public String getGoods_image() {
                    return goods_image;
                }

                public void setGoods_image(String goods_image) {
                    this.goods_image = goods_image;
                }
            }
        }

        public static class Goods2Bean implements Serializable {

            private String title;
            private List<Goods2BeanItem> item;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<Goods2BeanItem> getItem() {
                return item;
            }

            public void setItem(List<Goods2BeanItem> item) {
                this.item = item;
            }

            public static class Goods2BeanItem {

                private String image;
                private String type;
                private String data;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getData() {
                    return data;
                }

                public void setData(String data) {
                    this.data = data;
                }
            }
        }

        public static class Home1Bean implements Serializable {

            private String title;
            private String image;
            private String type;
            private String data;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }
        }

        public static class Home3Bean implements Serializable {

            private String title;
            private List<ItemBean> item;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ItemBean> getItem() {
                return item;
            }

            public void setItem(List<ItemBean> item) {
                this.item = item;
            }

            public static class ItemBean {

                private String image;
                private String type;
                private String data;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getData() {
                    return data;
                }

                public void setData(String data) {
                    this.data = data;
                }
            }
        }

        public static class GoodsBean implements Serializable {

            private String title;
            private List<?> item;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<?> getItem() {
                return item;
            }

            public void setItem(List<?> item) {
                this.item = item;
            }
        }
    }
}
