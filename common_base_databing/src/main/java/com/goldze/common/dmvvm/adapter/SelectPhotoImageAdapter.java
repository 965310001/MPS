package com.goldze.common.dmvvm.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.goldze.common.dmvvm.R;
import com.goldze.common.dmvvm.base.bean.BaseSelectPhotos;
import com.goldze.common.dmvvm.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：相册图片展示
 * *@author 小斌
 * @date 2019/5/6
 **/
public class SelectPhotoImageAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private OpenListener openListener;
    private List<BaseSelectPhotos> photoPath;
    private RemoveImgListener onRemoveListener;
    private ImageDataChangeListener onDataChangeListener;
    private int MAXSIZE = 5;

    public void setMaxSize(int MAXSIZE) {
        this.MAXSIZE = MAXSIZE;
    }

    public void setOpenListener(OpenListener openListener) {
        this.openListener = openListener;
    }

    public void setOnDataChangeListener(ImageDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }

    public List<BaseSelectPhotos> getPhotoPath() {
        return photoPath;
    }

    public void setOnRemoveListener(RemoveImgListener onRemoveListener) {
        this.onRemoveListener = onRemoveListener;
    }

    public void putPhotoPath(List<BaseSelectPhotos> photoUrl) {
        photoPath.addAll(photoUrl);
        notifyDataSetChanged();
        callDataChange();
    }

    public void addPhotoPath(BaseSelectPhotos path) {
        photoPath.add(path);
        notifyDataSetChanged();
        callDataChange();
    }

    public void addPhotoPath(List<BaseSelectPhotos> paths) {
        photoPath.addAll(paths);
        notifyDataSetChanged();
        callDataChange();
    }

    public void removePhotoPath(List<BaseSelectPhotos> oldPath) {
        photoPath.removeAll(oldPath);
    }

    private void callDataChange() {
        if (onDataChangeListener != null) {
            onDataChangeListener.onChange(photoPath);
        }
    }

    public SelectPhotoImageAdapter(RemoveImgListener onRemoveListener) {
        setOnRemoveListener(onRemoveListener);
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
            if (onRemoveListener != null) {
                onRemoveListener.removePos(helper.getLayoutPosition());
            }
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

    public interface OpenListener {
        void onOpen(View view, int position);
    }

    public interface RemoveImgListener {
        void removePos(int pos);
    }

    public interface ImageDataChangeListener {
        void onChange(List<BaseSelectPhotos> dataList);
    }

}
