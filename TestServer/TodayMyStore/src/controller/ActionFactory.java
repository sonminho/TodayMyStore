package controller;

import action.Action;
import action.AndroidAddItem;
import action.AndroidIdCheck;
import action.AndroidInfoUpdate;
import action.AndroidItemList;
import action.AndroidJoin;
import action.AndroidLogin;
import action.AndroidRemoveItem;
import action.AndroidSummary;
import action.AndroidTxnCheck;
import action.AndroidTxnInsert;
import action.AndroidTxnTrend;
import action.AndroidTxnUpdate;
import action.AndroidUserInfo;

public class ActionFactory { // �̱��� ������

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
		} else if(command.equals("android_remove_item")) {
			action = new AndroidRemoveItem();
		} else if(command.equals("android_txn_check")) {
			action = new AndroidTxnCheck();
		} else if(command.equals("android_txn_insert")) {
			action = new AndroidTxnInsert();
		} else if(command.equals("android_txn_update")) {
			action = new AndroidTxnUpdate();
		} else if(command.equals("android_txn_trend")) {
			action = new AndroidTxnTrend();
		} else if(command.equals("android_summary")) {
			action = new AndroidSummary();
		}
		
		return action;
	}
}