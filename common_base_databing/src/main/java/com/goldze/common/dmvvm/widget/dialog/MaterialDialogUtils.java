package com.goldze.common.dmvvm.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.goldze.common.dmvvm.R;

import java.util.List;

/**
 * @author GuoFeng
 * @date :2019/1/15 12:08
 * @description: MaterialDialog工具类
 */
public class MaterialDialogUtils {
    public void showThemed(Context context, String
            title, String content) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText("agree")
                .negativeText("disagree")
                .positiveColorRes(R.color.white)
                .negativeColorRes(R.color.white)
                .titleGravity(GravityEnum.CENTER)
                .titleColorRes(R.color.white)
                .contentColorRes(android.R.color.white)
                .backgroundColorRes(R.color.material_blue_grey_800)
                .dividerColorRes(R.color.white)
                .btnSelector(R.drawable.md_selector, DialogAction.POSITIVE)
                .positiveColor(Color.WHITE)
                .negativeColorAttr(android.R.attr.textColorSecondaryInverse)
                .theme(Theme.DARK)
                .autoDismiss(true)  //点击是否关闭对话框
                .showListener(dialog -> {
                    //dialog 出现
                })
                .cancelListener(dialog -> {
                    //dialog 消失（返回键）
                })
                .dismissListener(dialog -> {
                    //dialog 消失
                })
                .show();

        //获取按钮并监听
//        MDButton btn = materialDialog.getActionButton(DialogAction.NEGATIVE);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    /*public static MaterialDialog.Builder showBottomDialog(Context context, String content){
        MaterialDialog.Builder builder=new MaterialDialog.Builder(context)
                .contentGravity(GravityEnum.END)
    }*/

    /***
     * 获取一个耗时等待对话框
     *
     * @param horizontal
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder showIndeterminateProgressDialog(Context context, String content, boolean horizontal) {
//        MaterialDialog.Builder  builder = new MaterialDialog.Builder(context)
//                .title(content)
//                .progress(true, 0)
//                .progressIndeterminateStyle(horizontal)
//                .canceledOnTouchOutside(false)
//                .backgroundColorRes(R.color.white)
//                .keyListener(new DialogInterface.OnKeyListener() {
//                    @Override
//                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                        if (event.getAction() == KeyEvent.ACTION_DOWN) {//如果是按下，则响应，否则，一次按下会响应两次
//                            if (keyCode == KeyEvent.KEYCODE_BACK) {
//                                //activity.onBackPressed();
//
//                            }
//                        }
//                        return false;//false允许按返回键取消对话框，true除了调用取消，其他情况下不会取消
//                    }
//                });
//        return builder;
        return new MaterialDialog.Builder(context)
                .title(content)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .canceledOnTouchOutside(false)
                .backgroundColorRes(R.color.white)
                .keyListener((dialog, keyCode, event) -> {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {//如果是按下，则响应，否则，一次按下会响应两次
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            //activity.onBackPressed();

                        }
                    }
                    return false;//false允许按返回键取消对话框，true除了调用取消，其他情况下不会取消
                });
    }


    /***
     * 获取基本对话框
     *
     * @param
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder showBasicDialog(final Context context, String
            content) {
        return new MaterialDialog.Builder(context)
                .title(content)
                .positiveText("确定")
                .negativeText("取消");
//                .btnStackedGravity(GravityEnum.END)         //按钮排列位置
//                .stackingBehavior(StackingBehavior.ALWAYS)  //按钮排列方式
//                .iconRes(R.mipmap.ic_launcher)
//                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
//                .onAny(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction
//                            which) {
//                    }
//                })
//                .checkBoxPromptRes(R.string.app_name, false, null);
    }

    /***
     * 显示一个基础的对话框  只有内容没有标题
     *
     * @param
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder showBasicDialogNoTitle(final Context context, String content) {
        return new MaterialDialog.Builder(context)
                .content(content)
                .positiveText("确定")
                .negativeText("取消");
    }


    /***
     * 显示一个基础的对话框  带标题 带内容
     * 没有取消按钮
     *
     * @param
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder showBasicDialogNoCancel(final Context context, String
            title, String content) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText("确定");
    }

    /***
     * 显示一个基础的对话框  带标题 带内容
     *
     * @param
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder showBasicDialog(final Context context, String
            title, String content) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText("确定")
                .negativeText("取消");
    }

    /***
     * 显示一个基础的对话框  带标题 带内容
     *
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder showBasicDialogPositive(final Context context, String
            title, String content) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText("复制")
                .negativeText("取消");
    }

    /***
     * 选择图片等Item的对话框  带标题
     *
     * @param
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder getSelectDialog(Context context, String title, String[] arrays) {
        return new MaterialDialog.Builder(context)
                .items(arrays)
                .itemsColor(0XFF456ea6)
                .title(!TextUtils.isEmpty(title) ? title : "")
                .negativeText("取消");
    }

    /***
     * 获取LIST对话框
     *
     * @param
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder showBasicListDialog(final Context context, String title, List
            content) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .items(content)
                .itemsCallback((dialog, itemView, position, text) -> {

                })
                .negativeText("取消")
//                .checkBoxPromptRes(R.string.app_name, false, null)
                ;
    }

    /***
     * 获取单选LIST对话框
     *
     * @param
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder showSingleListDialog(final Context context, String title, List
            content, MaterialDialog.ListCallbackSingleChoice callback) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .items(content)
                .itemsCallbackSingleChoice(0, callback)
                .positiveText("选择");
    }


    /***
     * 获取多选LIST对话框
     *
     * @param
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder showMultiListDialog(final Context context, String title, List
            content) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .items(content)
                .itemsCallbackMultiChoice(new Integer[]{1, 3}, (dialog, which, text) -> {
                    return true; // allow selection
                })
                .onNeutral((dialog, which) -> dialog.clearSelectedIndices())
                .alwaysCallMultiChoiceCallback()
                .positiveText(R.string.md_choose_label)
                .autoDismiss(false)
                .neutralText("clear")
                .itemsDisabledIndices(0, 1);
    }


    /***
     * 获取自定义对话框
     *
     * @param
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder showCustomDialog(final Context context, String title, int
            content) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .customView(content, true)
                .positiveText("确定")
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {

                });
    }

    public static MaterialDialog.Builder showCustomDialog(final Context context, String title, View
            view, MaterialDialog.SingleButtonCallback callback) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .customView(view, true)
                .cancelable(false)
                .autoDismiss(false)
                .onNegative((dialog, which) -> dialog.dismiss())
                .positiveText(android.R.string.yes)
                .negativeText(android.R.string.cancel)
                .onPositive(callback);
    }


    /***
     * 获取输入对话框
     *
     * @param
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder showInputDialog(final Context context, String title, String
            content, MaterialDialog.InputCallback inputCallback) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .positiveText("确定")
                .negativeText("取消")
                .input("hint", "", true, inputCallback);
    }
}