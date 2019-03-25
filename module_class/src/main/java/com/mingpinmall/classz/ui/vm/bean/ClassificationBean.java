package com.mingpinmall.classz.ui.vm.bean;

import com.goldze.common.dmvvm.base.bean.BaseBean;

import java.util.List;

public class ClassificationBean extends BaseBean {

    /**
     * code : 200
     * datas : {"class_list":[{"gc_id":"1","gc_name":"服饰鞋帽","type_id":"49","type_name":"服饰鞋帽","gc_parent_id":"0","commis_rate":"1","gc_sort":"255","gc_virtual":"1","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05015381809285330.png","text":"女装/男装/内衣/运动/女鞋/男鞋/配饰/童装"},{"gc_id":"2","gc_name":"礼品箱包","type_id":"0","type_name":"","gc_parent_id":"0","commis_rate":"0","gc_sort":"255","gc_virtual":"0","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05015381349504237.png","text":"潮流女包/时尚男包/功能箱包/礼品/奢侈品"},{"gc_id":"3","gc_name":"家居家装","type_id":"0","type_name":"","gc_parent_id":"0","commis_rate":"0","gc_sort":"255","gc_virtual":"0","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05015381681266734.png","text":"家纺/灯具/生活日用/家装软饰/清洁日用/宠物生活"},{"gc_id":"256","gc_name":"数码办公","type_id":"0","type_name":"","gc_parent_id":"0","commis_rate":"0","gc_sort":"255","gc_virtual":"0","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05015381190111257.png","text":"手机配件/摄影摄像/数码配件/时尚影音/电脑整机/电脑配件/外设产品/网络产品/办公打印/办公文仪/手机通讯"},{"gc_id":"308","gc_name":"家用电器","type_id":"0","type_name":"","gc_parent_id":"0","commis_rate":"0","gc_sort":"255","gc_virtual":"0","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05015382063902705.png","text":"大家电/生活电器/厨房电器/个护健康/五金家装"},{"gc_id":"470","gc_name":"个护化妆","type_id":"0","type_name":"","gc_parent_id":"0","commis_rate":"0","gc_sort":"255","gc_virtual":"0","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05015382211209809.png","text":"面部护理/身体护理/口腔护理/女性护理/男士护理/魅力彩妆/香水SPA"},{"gc_id":"530","gc_name":"珠宝手表","type_id":"0","type_name":"","gc_parent_id":"0","commis_rate":"0","gc_sort":"255","gc_virtual":"0","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05015382869763762.png","text":"时尚饰品/纯金K金饰品/金银投资/银饰/钻石饰品/翡翠玉石/水晶玛瑙/宝石珍珠/婚庆/钟表手表"},{"gc_id":"593","gc_name":"食品饮料","type_id":"0","type_name":"","gc_parent_id":"0","commis_rate":"0","gc_sort":"255","gc_virtual":"0","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05015382413708016.png","text":"进口食品/地方特产/休闲食品/粮油调味/中外名酒/饮料冲调/营养健康/亚健康调理/健康礼品/生鲜食品"},{"gc_id":"662","gc_name":"运动健康","type_id":"0","type_name":"","gc_parent_id":"0","commis_rate":"0","gc_sort":"255","gc_virtual":"0","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05221777034633059.png","text":"户外鞋服/户外装备/运动器械/纤体瑜伽/体育娱乐/成人用品/保健器械/急救卫生"},{"gc_id":"730","gc_name":"汽车用品","type_id":"0","type_name":"","gc_parent_id":"0","commis_rate":"0","gc_sort":"255","gc_virtual":"0","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05015382972892418.png","text":"电子电器/系统养护/改装配件/汽车美容/座垫脚垫/内饰精品/安全自驾/整车"},{"gc_id":"825","gc_name":"玩具乐器","type_id":"0","type_name":"","gc_parent_id":"0","commis_rate":"0","gc_sort":"255","gc_virtual":"0","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05015383078446336.png","text":"适用年龄/遥控/电动/毛绒布艺/娃娃玩具/模型玩具/健身玩具/动漫玩具/益智玩具/积木拼插/DIY玩具/创意减压/乐器相关"},{"gc_id":"888","gc_name":"厨具","type_id":"0","type_name":"","gc_parent_id":"0","commis_rate":"0","gc_sort":"255","gc_virtual":"0","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05015382679643040.png","text":"烹饪锅具/刀剪菜板/收纳保鲜/水具酒具/餐具/茶具/咖啡具"},{"gc_id":"959","gc_name":"母婴用品","type_id":"0","type_name":"","gc_parent_id":"0","commis_rate":"0","gc_sort":"255","gc_virtual":"0","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05015383177958780.png","text":"奶粉/营养辅食/尿裤湿巾/喂养用品/洗护用品/童车童床/服饰寝居/妈妈专区"},{"gc_id":"1037","gc_name":"虚拟充值","type_id":"0","type_name":"","gc_parent_id":"0","commis_rate":"0","gc_sort":"255","gc_virtual":"1","gc_title":"{name} - {sitename}","gc_keywords":"{name},{sitename}","gc_description":"{name},{sitename}","show_type":"1","image":"http://39.108.254.185/data/upload/mobile/category/05015383410379550.png","text":"充值/游戏/票务"}]}
     */

    private int code;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
//        this.datas = datas;
    }

    public static class DatasBean {
        private List<ClassListBean> class_list;

        public List<ClassListBean> getClass_list() {
            return class_list;
        }

        public void setClass_list(List<ClassListBean> class_list) {
            this.class_list = class_list;
        }

        public static class ClassListBean {
            /**
             * gc_id : 1
             * gc_name : 服饰鞋帽
             * type_id : 49
             * type_name : 服饰鞋帽
             * gc_parent_id : 0
             * commis_rate : 1
             * gc_sort : 255
             * gc_virtual : 1
             * gc_title : {name} - {sitename}
             * gc_keywords : {name},{sitename}
             * gc_description : {name},{sitename}
             * show_type : 1
             * image : http://39.108.254.185/data/upload/mobile/category/05015381809285330.png
             * text : 女装/男装/内衣/运动/女鞋/男鞋/配饰/童装
             */

            private String gc_id;
            private String gc_name;
            private String type_id;
            private String type_name;
            private String gc_parent_id;
            private String commis_rate;
            private String gc_sort;
            private String gc_virtual;
            private String gc_title;
            private String gc_keywords;
            private String gc_description;
            private String show_type;
            private String image;
            private String text;

            public String getGc_id() {
                return gc_id;
            }

            public void setGc_id(String gc_id) {
                this.gc_id = gc_id;
            }

            public String getGc_name() {
                return gc_name;
            }

            public void setGc_name(String gc_name) {
                this.gc_name = gc_name;
            }

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getGc_parent_id() {
                return gc_parent_id;
            }

            public void setGc_parent_id(String gc_parent_id) {
                this.gc_parent_id = gc_parent_id;
            }

            public String getCommis_rate() {
                return commis_rate;
            }

            public void setCommis_rate(String commis_rate) {
                this.commis_rate = commis_rate;
            }

            public String getGc_sort() {
                return gc_sort;
            }

            public void setGc_sort(String gc_sort) {
                this.gc_sort = gc_sort;
            }

            public String getGc_virtual() {
                return gc_virtual;
            }

            public void setGc_virtual(String gc_virtual) {
                this.gc_virtual = gc_virtual;
            }

            public String getGc_title() {
                return gc_title;
            }

            public void setGc_title(String gc_title) {
                this.gc_title = gc_title;
            }

            public String getGc_keywords() {
                return gc_keywords;
            }

            public void setGc_keywords(String gc_keywords) {
                this.gc_keywords = gc_keywords;
            }

            public String getGc_description() {
                return gc_description;
            }

            public void setGc_description(String gc_description) {
                this.gc_description = gc_description;
            }

            public String getShow_type() {
                return show_type;
            }

            public void setShow_type(String show_type) {
                this.show_type = show_type;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
