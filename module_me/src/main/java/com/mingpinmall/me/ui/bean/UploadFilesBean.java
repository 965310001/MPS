package com.mingpinmall.me.ui.bean;

/**
 * 功能描述：文件上传
 * *@author 小斌
 * @date 2019/5/8
 **/
public class UploadFilesBean {

    private int file_id;
    private String file_name;
    private String origin_file_name;
    private String file_url;

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public String getFile_name() {
        return file_name == null ? "" : file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name == null ? "" : file_name;
    }

    public String getOrigin_file_name() {
        return origin_file_name == null ? "" : origin_file_name;
    }

    public void setOrigin_file_name(String origin_file_name) {
        this.origin_file_name = origin_file_name == null ? "" : origin_file_name;
    }

    public String getFile_url() {
        return file_url == null ? "" : file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url == null ? "" : file_url;
    }
}
