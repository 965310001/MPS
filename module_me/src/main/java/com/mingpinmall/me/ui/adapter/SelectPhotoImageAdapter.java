package com.mingpinmall.me.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.utils.ImageUtils;
import com.mingpinmall.me.R;
import com.mingpinmall.me.ui.bean.BaseSelectPhotos;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：相册图片展示
 * 创建人：小斌
 * 创建时间: 2019/5/6
 **/
public class SelectPhotoImageAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private onOpenListener openListener;
    private List<BaseSelectPhotos> photoPath;
    private RemoveImgListener removeImg;
    private int MAXSIZE = 5;

    public void setMaxSize(int MAXSIZE) {
        this.MAXSIZE = MAXSIZE;
    }

    public void setOpenListener(onOpenListener openListener) {
        this.openListener = openListener;
    }

    public void setPhotoPath(List<BaseSelectPhotos> photoPath) {
        this.photoPath = photoPath;
        notifyDataSetChanged();
    }

    public List<BaseSelectPhotos> getPhotoPath() {
        return photoPath;
    }

    public void setRemoveImg(RemoveImgListener removeImg) {
        this.removeImg = removeImg;
    }

    public void putPhotoPath(List<BaseSelectPhotos> photoUrl) {
        photoPath.addAll(photoUrl);
        notifyDataSetChanged();
    }

    public void addPhotoPath(BaseSelectPhotos path) {
        photoPath.add(path);
        notifyDataSetChanged();
    }

    public void addPhotoPath(List<BaseSelectPhotos> paths) {
        photoPath.addAll(paths);
        notifyDataSetChanged();
    }

    public void replacePhotoPath(List<BaseSelectPhotos> oldPath, List<BaseSelectPhotos> newPath) {
        photoPath.removeAll(oldPath);
        photoPath.addAll(newPath);
        notifyDataSetChanged();
    }

    public void removePhotoPath(List<BaseSelectPhotos> oldPath) {
        photoPath.removeAll(oldPath);
    }

    public SelectPhotoImageAdapter(RemoveImgListener removeImg) {
        this.removeImg = removeImg;
        photoPath = new ArrayList<>();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BaseViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_photoselect, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder helper, int i) {
        if (photoPath.size() < MAXSIZE && helper.getLayoutPosition() + 1 == getItemCount()) {
            helper.setImageResource(R.id.iv_image, R.drawable.ic_addphoto)
                    .setGone(R.id.iv_delete, false);
        } else {
            helper.setGone(R.id.iv_delete, true);
            ImageUtils.loadImage(helper.getView(R.id.iv_image), photoPath.get(helper.getLayoutPosition()).getOriginalurl());
            Log.i("PICURL", "图片地址：" + photoPath.get(helper.getLayoutPosition()).getOriginalurl());
        }
        helper.getView(R.id.iv_delete).setOnClickListener(v -> {
            notifyItemRemoved(helper.getLayoutPosition());
            photoPath.remove(helper.getLayoutPosition());
            if (removeImg != null)
                removeImg.removePos(helper.getLayoutPosition());
        });
        helper.itemView.setOnClickListener(v -> {
            if (openListener != null) {
                openListener.onOpen(v, helper.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (photoPath.size() == MAXSIZE) {
            return MAXSIZE;
        }
        return photoPath.size() + 1;
    }

    public interface onOpenListener {
        void onOpen(View view, int position);
    }

    public interface RemoveImgListener {
        void removePos(int pos);
    }
}
