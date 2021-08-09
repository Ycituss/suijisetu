package com.ycitus.function;


import com.ycitus.bean.HistoryManager;
import com.ycitus.utils.NumberUtil;

/** 用于描述某个Function的Manager **/
public abstract class FunctionManager {

	/** 功能使用的历史记录者 **/
	private final HistoryManager<Long> functionUseHistoryManager = new HistoryManager<Long>(
			"功能使用历史记录者", 0 - NumberUtil.getBigEnoughNumber());

	/** 判断发送间隔是否合法 **/
	public abstract boolean canUse(long QQGroup);


	public HistoryManager<Long> getFunctionUseHistoryManager() {
		return this.functionUseHistoryManager;
	}

	public void updateUseTime(long QQGroup) {
		this.getFunctionUseHistoryManager().updateCall_History(QQGroup);
	}
}
