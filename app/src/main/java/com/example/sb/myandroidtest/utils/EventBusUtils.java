package com.example.sb.myandroidtest.utils;

import com.example.sb.myandroidtest.eventbus.EventMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by shishaobin on 2019/8/29
 */
public class EventBusUtils {
    private EventBusUtils() {
    }

    /**
     * 注册 EventBus
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(subscriber)) {
            eventBus.register(subscriber);
        }
    }

    /**
     * 解除注册 EventBus
     *
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        EventBus eventBus = EventBus.getDefault();
        if (eventBus.isRegistered(subscriber)) {
            eventBus.unregister(subscriber);
        }
    }

    /**
     * 发送事件消息
     *
     * @param event
     */
    public static void post(EventMessage event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发送粘性事件消息
     *
     * @param event
     */
    public static void postSticky(EventMessage event) {
        EventBus.getDefault().postSticky(event);
    }
}
