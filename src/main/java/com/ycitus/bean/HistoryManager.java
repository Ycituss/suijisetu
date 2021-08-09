package com.ycitus.bean;


import com.ycitus.debug.LoggerManager;
import com.ycitus.utils.DateUtil;

import java.util.Calendar;
import java.util.HashMap;


/**
 * 用于记录调用历史的对象
 **/
public class HistoryManager<E> {
    /**
     * 要求每次调用至少需要间隔多少时间, 防止恶意调用
     **/
    private final HashMap<E, Calendar> call_history = new HashMap<E, Calendar>();

    /**
     * 该调用历史管理者的名称
     **/
    private String history_manager_name = null;

    /**
     * 表示当第一次调用时, 默认设置的last_call_date与当前时间的时间偏移(sec)
     **/
    private int firstCall_sec_increment = 0;

    public HistoryManager(String history_manager_name,
                          int firstCall_sec_increment) {
        super();
        this.history_manager_name = history_manager_name;
        this.firstCall_sec_increment = firstCall_sec_increment;
    }

    private long distance_last_call_seconds(E user) {
        return DateUtil.diffSeconds(Calendar.getInstance(),
                getCallHistory(user));
    }

    private Calendar getCallHistory(E user) {
        LoggerManager.logDebug(getLogDebugType(), "Get Call History: user = " + user);

        // 获取某个user号的调用历史，若获取的是Null，则说明这是该用户第一次调用，应返回一个很老的日期，让该用户可以正常发车。
        Calendar result = call_history.get(user);

        if (result == null) {

            Calendar default_calendar = Calendar.getInstance();
            default_calendar.set(Calendar.SECOND,
                    default_calendar.get(Calendar.SECOND)
                            + getFirstCall_sec_increment());

            result = default_calendar;

            // [!] 若不存在, 则手动设置一个
            call_history.put(user, result);
        }

        return result;
    }

    private int getFirstCall_sec_increment() {
        return this.firstCall_sec_increment;
    }

    /**
     * 手动设置调用的方法
     **/
    public void setFirstCall_sec_increment(int firstCall_sec_increment) {
        this.firstCall_sec_increment = firstCall_sec_increment;
    }

    public String getHistory_manager_name() {
        return history_manager_name;
    }

    public String getLogDebugType() {
        return "调用历史管理者 - " + this.getHistory_manager_name() + "";
    }

    /**
     * 输入时间间隔, 判断某个用户的两次调用间隔是否合法
     **/
    public boolean isCallSuccessIntervalLegal(E user, int limit_sec) {
        long diff = distance_last_call_seconds(user);
        long limit = limit_sec;

        LoggerManager.logDebug(getLogDebugType(), "判断当前时间的调用是否时间间隔合法: diff = "
                + diff + ", limit = " + limit);
        return diff >= limit;
    }

    public void setCall_History(E user, Calendar calendar) {
        LoggerManager.logDebug(getLogDebugType(), "设置调用历史: user = " + user
                + ", calendar = " + calendar);
        call_history.put(user, calendar);
    }

    public void updateCall_History(E user) {
        Calendar calendar = Calendar.getInstance();
        LoggerManager.logDebug(getLogDebugType(), "更新调用历史: user = " + user
                + ", calendar = " + calendar);
        call_history.put(user, calendar);
    }

}
