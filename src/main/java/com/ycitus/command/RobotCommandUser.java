package com.ycitus.command;

import com.ycitus.PluginMain;
import com.ycitus.debug.LoggerManager;
import com.ycitus.files.FileManager;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.MemberPermission;


public enum RobotCommandUser {
	NORMAL_USER(1), GROUP_ADMINISTRATOR(2), GROUP_OWNER(3), BOT_ADMINISTRATOR(4);

	/** 判断用户的权限. **/
	public static int getAuthority(long fromGroup, long fromQQ) {

		int authority;

		// 先判断是否为群消息
		if (fromGroup != -1) {
			authority = RobotCommandUser.getAuthorityByQQ(fromGroup, fromQQ);
		} else {
			// 再判断是否是私聊消息
			authority = RobotCommandUser.getAuthorityByQQ(fromQQ);
		}

		LoggerManager.logDebug("Permission", "fromGroup = " + fromGroup + ", fromQQ = " + fromQQ + ", authority："
				+ authority);

		return authority;
	}

	/** 单纯通过QQ，判断对方是不是超级管理 **/
	public static int getAuthorityByQQ(long QQ) {

		for (Long botAdministrator : FileManager.applicationConfig_File.getSpecificDataInstance().Admin.botAdministrators) {
			if (botAdministrator.equals(QQ)) {
				return BOT_ADMINISTRATOR.getUserPermission();
			}
		}

		return NORMAL_USER.getUserPermission();
	}

	/** 判断群聊中，对方是不是管理（管理员或群主） **/
	public static int getAuthorityByQQ(long fromGroup, long fromQQ) {

		Member m = PluginMain.getCurrentBot().getGroup(fromGroup)
				.get(fromQQ);

		// [!] 首先判断是不是管理员
		// 防止自己是超级管理员，但又是普通群员
		if (getAuthorityByQQ(fromQQ) == RobotCommandUser.BOT_ADMINISTRATOR
				.getUserPermission()) {
			return RobotCommandUser.BOT_ADMINISTRATOR.getUserPermission();
		}

		// 首先判断是不是普通群员，以提高性能
		if (m.getPermission() == MemberPermission.MEMBER) {
			return RobotCommandUser.NORMAL_USER.getUserPermission();
		}

		if (m.getPermission() == MemberPermission.OWNER) {
			return RobotCommandUser.GROUP_OWNER.getUserPermission();
		}

		if (m.getPermission() == MemberPermission.ADMINISTRATOR) {
			return RobotCommandUser.GROUP_ADMINISTRATOR.getUserPermission();
		}

		return RobotCommandUser.NORMAL_USER.getUserPermission();
	}

	int userPermission;

	RobotCommandUser(int user) {
		this.userPermission = user;
	}

	public int getUserPermission() {
		return userPermission;
	}

}
