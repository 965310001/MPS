package com.mingpinmall.classz.db;

import android.content.Context;

import com.mingpinmall.classz.ui.vm.bean.DaoMaster;
import com.mingpinmall.classz.ui.vm.bean.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * @author GuoFeng
 * @date : 2019/1/24 20:42
 * @description:
 */
public class ShoppingDBManage {
    private static ShoppingDBManage dbManage;

    private Context context;

    public static ShoppingDBManage getInstance(Context context) {
        if (dbManage == null) {
            synchronized (ShoppingDBManage.class) {
                if (dbManage == null) {
                    dbManage = new ShoppingDBManage(context);
                }
            }
        }
        return dbManage;
    }

    private ShoppingDBManage(Context context) {
        this.context = context;
    }

    /**
     * 设置debug模式开启或关闭，默认关闭 * * @param flag
     */
    public void setDebug(boolean flag) {
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;
    }

    public DaoMaster getmDaoMaster() {
        return mDaoMaster;
    }

    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;


    /**
     * 获取DaoSession * * @return
     */
    public synchronized DaoSession getDaoSession() {
        if (null == mDaoSession) {
            mDaoSession = getDaoMaster().newSession();
        }
        return mDaoSession;
    }

    /**
     * 关闭数据库
     */
    public synchronized void closeDataBase() {
        closeHelper();
        closeDaoSession();
    }

    /**
     * 判断数据库是否存在，如果不存在则创建 * * @return
     */
    private DaoMaster getDaoMaster() {
        if (null == mDaoMaster) {
            mHelper = new DaoMaster.DevOpenHelper(context, DBConfig.DB_NAME, null);
            mDaoMaster = new DaoMaster(mHelper.getWritableDb());
        }
        return mDaoMaster;
    }

    private void closeDaoSession() {
        if (null != mDaoSession) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    private void closeHelper() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }
}
