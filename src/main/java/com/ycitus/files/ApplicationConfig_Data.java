package com.ycitus.files;

import com.ycitus.PluginMain;

import java.util.ArrayList;

public class ApplicationConfig_Data {

	public String version = PluginMain.getVersion();

	public Debug Debug = new Debug();
	public class Debug {
		public boolean enable = false;
	}

	public Admin Admin = new Admin();
	public class Admin {
		public InvitationManager InvitationManager = new InvitationManager();
		public class InvitationManager {

			public QQFriendInvitation QQFriendInvitation = new QQFriendInvitation();
			public class QQFriendInvitation {
				public boolean autoAcceptAddQQFriend = false;
			}

			public QQGroupInvitation QQGroupInvitation = new QQGroupInvitation();
			public class QQGroupInvitation {
				public boolean autoAcceptAddQQGroup = false;
			}
		}

		public ArrayList<Long> botAdministrators = new ArrayList<Long>() {
			{
				this.add(2799282971L);
			}
		};
	}
	public Systems Systems = new Systems();
	public class Systems {

		public SendSystem SendSystem = new SendSystem();
		public class SendSystem {

			public SendDelay SendDelay = new SendDelay();
			public class SendDelay {

				public SendToFriends SendToFriends = new SendToFriends();
				public class SendToFriends {
					public boolean enable = false;
					public long delayTimeMS = 0;
				}

				public SendToGroups SendToGroups = new SendToGroups();
				public class SendToGroups {
					public boolean enable = true;
					public long delayTimeMS = 1000;
				}

			}

			public int sendMsgMaxLength = 4500;
		}

	}

	public RandomImages RandomImages = new RandomImages();
	public class RandomImages {
		public boolean setuAll = false;
		public boolean r18All = false;
		public ArrayList<String> setuCommands = new ArrayList<String>(){
			{
				this.add("gkd");
				this.add("来张图");
			}
		};
		public ArrayList<Long> groupSetu = new ArrayList<Long>(){

		};
		public ArrayList<Long> groupR18 = new ArrayList<Long>(){

		};
	}

}
