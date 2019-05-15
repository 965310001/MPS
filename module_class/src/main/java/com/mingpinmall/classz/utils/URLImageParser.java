package com.mingpinmall.classz.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.socks.library.KLog;

import java.net.URL;
import java.net.URLConnection;

public class URLImageParser {

    private Context mContext;
    private TextView mTextView;
    private int mImageSize, mImageSizeHeight;

    /**
     * @param textView  图文混排TextView
     * @param context
     * @param imageSize 图片显示高度
     */
    public URLImageParser(TextView textView, Context context, int imageSize) {
        mTextView = textView;
        mContext = context;
        mImageSize = imageSize;
    }

    public Drawable getDrawable(String url) {
        URLDrawable urlDrawable = new URLDrawable();
        new ImageGetterAsyncTask(mContext, url, urlDrawable).execute(mTextView);
        return urlDrawable;
    }

    class ImageGetterAsyncTask extends AsyncTask<TextView, Void, Drawable> {

        private URLDrawable urlDrawable;
        private Context context;
        private String source;
        private TextView textView;

        public ImageGetterAsyncTask(Context context, String source, URLDrawable urlDrawable) {
            this.context = context;
            this.source = source;
            this.urlDrawable = urlDrawable;
        }

        @Override
        protected Drawable doInBackground(TextView... params) {
            textView = params[0];
            try {
                //下载网络图片，以下是使用Picasso和Glide获取网络图片例子，也可以其他方式下载网络图片
                // 使用Picasso获取网络图片Bitmap
                URL url = new URL(source);
                URLConnection con = url.openConnection();
                con.setConnectTimeout(50000);
                Drawable d = Drawable.createFromStream(con.getInputStream(), "");
//                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//                return d;
                KLog.i(source + "网络图片" + d.getIntrinsicHeight());
//                return Picasso.with(context).load(source).get();
                // 使用Glide获取网络图片Bitmap(使用Glide获取图片bitmap还有待研究)
//                textView.setTag(source);
                mImageSizeHeight = mImageSize;
                if (d.getIntrinsicWidth() < 30) {
                    mImageSize = d.getIntrinsicWidth() * 3;
                    mImageSizeHeight = d.getIntrinsicHeight() * 3;
                }
                if (d.getIntrinsicHeight() < 30) {
                    mImageSize = d.getIntrinsicWidth() * 3;
                    mImageSizeHeight = d.getIntrinsicHeight() * 3;
                }
                textView.setTag(source);
//                return Glide.with(context).asDrawable().load(source)
//                        .into(mImageSize, mImageSizeHeight)
//                        .get();
                return Glide.with(context).asDrawable().load(source)
                        .into(d.getIntrinsicWidth(), d.getIntrinsicHeight())
                        .get();
            } catch (Exception e) {
                KLog.i(e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(final Drawable drawable) {
            try {
                //获取图片宽高比
                float ratio = drawable.getIntrinsicWidth() * 1.0f / drawable.getIntrinsicHeight();
                Drawable bitmapDrawable = drawable;//new BitmapDrawable(context.getResources(), bitmap);
                mImageSize = bitmapDrawable.getIntrinsicWidth();
                bitmapDrawable.setBounds(0, 0, (int) (mImageSize * ratio), mImageSize);
                //设置图片宽、高（这里传入的mImageSize为字体大小，所以，设置的高为字体大小，宽为按宽高比缩放）
                urlDrawable.setBounds(0, 0, (int) (mImageSize * ratio), mImageSize);
                urlDrawable.drawable = bitmapDrawable;
                //两次调用invalidate才会在异步加载完图片后，刷新图文混排TextView，显示出图片
                urlDrawable.invalidateSelf();
                textView.invalidate();
                textView.setText(textView.getText());
            } catch (Exception e) {
                /* Like a null bitmap, etc. */
            }
        }
    }
}