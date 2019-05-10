package com.mingpinmall.me.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：消息列表
 * @author 小斌
 * @date 2019/4/28
 **/
public class MessageListBean implements Serializable {

    private NodeInfoBean node_info;
    private MemberInfoBean member_info;
    private List<ListBean> list;

    public NodeInfoBean getNode_info() {
        return node_info;
    }

    public void setNode_info(NodeInfoBean node_info) {
        this.node_info = node_info;
    }

    public MemberInfoBean getMember_info() {
        return member_info;
    }

    public void setMember_info(MemberInfoBean member_info) {
        this.member_info = member_info;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class NodeInfoBean {

        private boolean node_chat;
        private String node_site_url;

        public boolean isNode_chat() {
            return node_chat;
        }

        public void setNode_chat(boolean node_chat) {
            this.node_chat = node_chat;
        }

        public String getNode_site_url() {
            return node_site_url == null ? "" : node_site_url;
        }

        public void setNode_site_url(String node_site_url) {
            this.node_site_url = node_site_url == null ? "" : node_site_url;
        }
    }

    public static class MemberInfoBean {

        private String member_id;
        private String member_name;
        private String member_avatar;
        private String store_id;
        private String store_name;
        private String store_avatar;
        private String grade_id;

        public String getMember_id() {
            return member_id == null ? "" : member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id == null ? "" : member_id;
        }

        public String getMember_name() {
            return member_name == null ? "" : member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name == null ? "" : member_name;
        }

        public String getMember_avatar() {
            return member_avatar == null ? "" : member_avatar;
        }

        public void setMember_avatar(String member_avatar) {
            this.member_avatar = member_avatar == null ? "" : member_avatar;
        }

        public String getStore_id() {
            return store_id == null ? "" : store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id == null ? "" : store_id;
        }

        public String getStore_name() {
            return store_name == null ? "" : store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name == null ? "" : store_name;
        }

        public String getStore_avatar() {
            return store_avatar == null ? "" : store_avatar;
        }

        public void setStore_avatar(String store_avatar) {
            this.store_avatar = store_avatar == null ? "" : store_avatar;
        }

        public String getGrade_id() {
            return grade_id == null ? "" : grade_id;
        }

        public void setGrade_id(String grade_id) {
            this.grade_id = grade_id == null ? "" : grade_id;
        }
    }

    public static class ListBean {

        private String u_id;
        private String u_name;
        private String avatar;
        private int recent;
        private String m_id;
        private String time;
        private int r_state;
        private String t_msg;

        public String getU_id() {
            return u_id == null ? "" : u_id;
        }

        public void setU_id(String u_id) {
            this.u_id = u_id == null ? "" : u_id;
        }

        public String getU_name() {
            return u_name == null ? "" : u_name;
        }

        public void setU_name(String u_name) {
            this.u_name = u_name == null ? "" : u_name;
        }

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar == null ? "" : avatar;
        }

        public int getRecent() {
            return recent;
        }

        public void setRecent(int recent) {
            this.recent = recent;
        }

        public String getM_id() {
            return m_id == null ? "" : m_id;
        }

        public void setM_id(String m_id) {
            this.m_id = m_id == null ? "" : m_id;
        }

        public String getTime() {
            return time == null ? "" : time;
        }

        public void setTime(String time) {
            this.time = time == null ? "" : time;
        }

        public int getR_state() {
            return r_state;
        }

        public void setR_state(int r_state) {
            this.r_state = r_state;
        }

        public String getT_msg() {
            return t_msg == null ? "" : t_msg;
        }

        public void setT_msg(String t_msg) {
            this.t_msg = t_msg == null ? "" : t_msg;
        }
    }
}

