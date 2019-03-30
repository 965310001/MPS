package com.mingpinmall.classz.db;

import com.goldze.common.dmvvm.utils.Utils;
import com.mingpinmall.classz.R;

/**
 * @author GuoFeng
 * @date : 2019/1/24 21:16
 * @description:
 */
public interface DBConfig {
    String DB_NAME = Utils.getApplication().getString(R.string.app_name) + ".db";

}
