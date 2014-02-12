package cn.keeasy.business.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import cn.keeasy.business.R;
import cn.keeasy.business.base.ChatBean;
import cn.keeasy.business.bean.ContactBean;
import cn.keeasy.business.bean.OrderBean;
import cn.keeasy.business.bean.UserBean;
import cn.keeasy.business.bean.ZheBean;

public class Sources {

	/**
	 * 登录状态
	 */
	public static boolean ISLOGIN;
	/**
	 * 跳转要操作的页面
	 */
	public static String PAGE = "";
	/**
	 * APP名称
	 */
	public static String APPNAME = "Business.apk";
	/**
	 * APP下载路径
	 */
	public static String DOWNPATH = "http://112.90.72.154:8082/resources/download/app/";
	/**
	 * APP Json
	 */
	public static String JSONNAME = "business.json";
	/**
	 * 服务器连接地址
	 */
	// public static String IP = "http://112.90.72.154:8888/zbbs-phone-app/";
	public static String IP = "http://112.90.72.154:8889/zbbs-phone-app/";
	// public static String IP = "http://192.168.2.227:8096/zbbs-phone-app/";

	/**
	 * 订单列表
	 */
	public static List<OrderBean> ORDERBEAN = new ArrayList<OrderBean>();

	/**
	 * 当前订单
	 */
	public static OrderBean CURROBEAN = new OrderBean();

	/**
	 * 用户信息
	 */
	public static UserBean USERBEAN = new UserBean();

	/**
	 * 当前对话帐号
	 */
	public static ContactBean CURRENTIC;

	/**
	 * 设置当前对话帐号
	 */
	public static void setCurrentIC(int position) {
		CURRENTIC = CONTACTLIST.get(position);
	}

	/**
	 * 好友列表
	 */
	public static List<ContactBean> CONTACTLIST = new ArrayList<ContactBean>();

	/**
	 * 缓存聊天信息
	 */
	public static HashMap<String, ArrayList<ChatBean>> CHATINFOMAP = new HashMap<String, ArrayList<ChatBean>>();

	/**
	 * 聊天表情
	 */
	public static HashMap<String, Integer> FACESMAP = new HashMap<String, Integer>();

	public static void mInitFace(Context context) {
		if (FACESMAP.size() <= 0) {
			for (int i = 1; i <= 66; i++) {
				String faceName = "ic_img_" + i;
				try {
					int id = R.drawable.class.getDeclaredField(faceName)
							.getInt(context);
					FACESMAP.put(faceName, id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取聊天信息图片
	 */
	public final static String IMAGEURL = "http://112.90.72.154:8082/resources/chats/";

	/**
	 * 未读消息
	 */
	public static Map<String, String> NOTREADMSG = new HashMap<String, String>();

	/**
	 * 子帐号
	 */
	public static String ACCOUNT = "";

	/**
	 * 折扣信息
	 */
	public static ZheBean ZHEBEAN;

	/**
	 * 获取聊天声音
	 */
	public final static String RECORDURL = "http://112.90.72.154:8082/resources/chats/audio/";

}
