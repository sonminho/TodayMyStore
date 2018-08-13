package controller;

import action.Action;
import action.AndroidAddItem;
import action.AndroidIdCheck;
import action.AndroidInfoUpdate;
import action.AndroidItemList;
import action.AndroidJoin;
import action.AndroidLogin;
import action.AndroidUserInfo;

public class ActionFactory {
	// 싱글턴 디자인
	private static ActionFactory instance = new ActionFactory();
	
	ActionFactory() {
		super();
	}
	
	public static ActionFactory getInstance() {
		return instance;
	}
	
	public Action getAction(String command) {
		Action action = null;
		
		System.out.println("ActionFactory : " + command);
		
		if(command.equals("android_login")) {
			action = new AndroidLogin();
		} else if(command.equals("android_IdCheck")) {
			action = new AndroidIdCheck();
		} else if(command.equals("android_join")) {
			action = new AndroidJoin();
		} else if(command.equals("android_info_update")) {
			action = new AndroidInfoUpdate();
		} else if(command.equals("android_user_info")) {
			action = new AndroidUserInfo();
		} else if(command.equals("android_item_list")) {
			action = new AndroidItemList();
		} else if(command.equals("android_add_item")) {
			action = new AndroidAddItem();
		}
		
		return action;
	}
}