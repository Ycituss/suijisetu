package com.ycitus.framework;

import com.ycitus.PluginMain;
import com.ycitus.files.ConfigFile;
import net.mamoe.mirai.contact.*;

import java.io.File;

public class BotManager {

	public static ContactList<Friend> getAllQQFriends() {
		return PluginMain.getCurrentBot().getFriends();
	}

	public static ContactList<Group> getAllQQGroups() {
		return PluginMain.getCurrentBot().getGroups();
	}

	public static ContactList<Stranger> getAllStrangers() {
		return PluginMain.getCurrentBot().getStrangers();
	}

	public static Member getGroupMemberCard(long fromGroup, long fromQQ) {
		Group group = PluginMain.getCurrentBot().getGroup(fromGroup);
		Member groupMember = group.get(fromQQ);
		return groupMember;
	}

	public static String getGroupMemberName(Member groupMember) {

		String ret = groupMember.getNameCard();
		if (ret.isEmpty()) {
			ret = groupMember.getNick();
		}

		return ret;
	}

	public static String getVoicesPath() {
		return ConfigFile.getApplicationConfigPath() + "voice-files" + File.separator;

	}

}
