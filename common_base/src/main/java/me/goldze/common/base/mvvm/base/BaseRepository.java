package me.goldze.common.base.mvvm.base;

import me.goldze.common.base.event.LiveBus;
import me.goldze.common.base.mvvm.AbsRepository;

/**
 * @author GuoFeng
 * @date :2019/1/17 16:42
 * @description:
 */
public class BaseRepository extends AbsRepository {

    protected void sendData(Object eventKey, Object t) {
        sendData(eventKey, null, t);
    }

    protected void showPageState(Object eventKey, String state) {
        sendData(eventKey, state);
    }

    protected void showPageState(Object eventKey, String tag, String state) {
        sendData(eventKey, tag, state);
    }

    protected void sendData(Object eventKey, String tag, Object t) {
        LiveBus.getDefault().postEvent(eventKey, tag, t);
    }
}
