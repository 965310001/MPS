package com.mingpinmall.me.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：xiaobin on 2019/1/9
 * 邮箱：1226248203@qq.com
 **/
public class BaseSelectPhotos implements Parcelable {

    private String originalurl;
    private String thumburl;
    private String imageGuid;

    public String getOriginalurl() {
        return originalurl;
    }

    public void setOriginalurl(String originalurl) {
        this.originalurl = originalurl;
    }

    public String getThumburl() {
        return thumburl;
    }

    public void setThumburl(String thumburl) {
        this.thumburl = thumburl;
    }

    public String getImageGuid() {
        return imageGuid;
    }

    public void setImageGuid(String imageGuid) {
        this.imageGuid = imageGuid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalurl);
        dest.writeString(this.thumburl);
        dest.writeString(this.imageGuid);
    }

    public BaseSelectPhotos() {
    }

    protected BaseSelectPhotos(Parcel in) {
        this.originalurl = in.readString();
        this.thumburl = in.readString();
        this.imageGuid = in.readString();
    }

    public static final Creator<BaseSelectPhotos> CREATOR = new Creator<BaseSelectPhotos>() {
        @Override
        public BaseSelectPhotos createFromParcel(Parcel source) {
            return new BaseSelectPhotos(source);
        }

        @Override
        public BaseSelectPhotos[] newArray(int size) {
            return new BaseSelectPhotos[size];
        }
    };
}
