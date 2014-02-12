package cn.keeasy.mobilekeeasy.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.entity.AreaBean;
import cn.keeasy.mobilekeeasy.entity.BusinessBean;
import cn.keeasy.mobilekeeasy.entity.ChatBean;
import cn.keeasy.mobilekeeasy.entity.ClassBean;
import cn.keeasy.mobilekeeasy.entity.ClassProductBean;
import cn.keeasy.mobilekeeasy.entity.ContactBean;
import cn.keeasy.mobilekeeasy.entity.OrderBean;
import cn.keeasy.mobilekeeasy.entity.OrderListBean;
import cn.keeasy.mobilekeeasy.entity.ProductInfoBean;
import cn.keeasy.mobilekeeasy.entity.ShopBean;
import cn.keeasy.mobilekeeasy.entity.ShopTcBean;
import cn.keeasy.mobilekeeasy.entity.SunOrderBean;
import cn.keeasy.mobilekeeasy.entity.WeiboUserBean;
import cn.keeasy.mobilekeeasy.entity.ZhekeBean;

public class Sources {

	/**
	 * 登陆后跳转的页面
	 */
	public static String TARGET = "";
	/**
	 * 跳转要操作的页面
	 */
	public static String PAGE = "";
	/**
	 * 搜索页面
	 */
	public static String SPAGE = "";
	/**
	 * 扫描结果
	 */
	public static String SCANTEXT = "";
	/**
	 * 地图搜索
	 */
	public static String MAPNAME = "";
	/**
	 * 星期天店铺介绍
	 */
	public static String JIANSHAO = "";
	/**
	 * 星期天店铺需知
	 */
	public static String XIUZHI = "";
	/**
	 * 折扣店铺图片
	 */
	public static String PHOTO = "";
	/**
	 * 星期天店铺收取提成
	 */
	public static double TICHENG = 0.0;

	/**
	 * APP名称
	 */
	public static String APPNAME = "MobileKeeasy.apk";
	/**
	 * APP下载路径
	 */
	public static String DOWNPATH = "http://112.90.72.154:8082/resources/download/app/";
	/**
	 * APP Json
	 */
	public static String JSONNAME = "mobilekeeasy.json";

	/**
	 * 区域地址
	 */
	public static List<AreaBean> AREA = new ArrayList<AreaBean>();
	/**
	 * 分类列表
	 */
	public static List<ClassBean> DCLASS = new ArrayList<ClassBean>();

	/**
	 * 订单接收Bean
	 */
	public static OrderBean ORDERBEAN = new OrderBean();

	/**
	 * 星期天店铺信息
	 */
	public static List<ShopBean> SUNDAYINFO = new ArrayList<ShopBean>();
	/**
	 * 搜索店铺信息
	 */
	public static List<ShopBean> SREACHINFO = new ArrayList<ShopBean>();
	/**
	 * 附近店铺信息
	 */
	public static List<ShopBean> RANGE = new ArrayList<ShopBean>();
	/**
	 * 限时折扣店铺信息
	 */
	public static List<ZhekeBean> TIMZHEKE = new ArrayList<ZhekeBean>();
	/**
	 * 星期天商品信息
	 */
	public static List<ProductInfoBean> PROINFOLIST = new ArrayList<ProductInfoBean>();
	/**
	 * 购物车列表
	 */
	public static List<ProductInfoBean> CARTLIST = new ArrayList<ProductInfoBean>();
	/**
	 * 店铺产品分类
	 */
	public static List<ClassProductBean> CLASSLIST = new ArrayList<ClassProductBean>();
	/**
	 * 好友列表
	 */
	public static List<ContactBean> CONTACTLIST = new ArrayList<ContactBean>();
	/**
	 * 订单列表
	 */
	public static List<OrderListBean> ORDERLIST = new ArrayList<OrderListBean>();
	/**
	 * 订单详情Bean
	 */
	public static SunOrderBean ORDERDETABEAN = new SunOrderBean();
	/**
	 * 套餐详情Bean
	 */
	public static ShopTcBean SHOPTCBEAN = new ShopTcBean();
	/**
	 * 地图店铺List
	 */
	public static List<BusinessBean> MAPBUESLIST = new ArrayList<BusinessBean>();

	/**
	 * 当前店铺信息
	 */
	public static ShopBean CURRENTSHOP;
	/**
	 * 当前对话帐号
	 */
	public static ContactBean CURRENTIC;
	/**
	 * 当前商品信息详情
	 */
	public static ProductInfoBean PRODUCTINFO;
	/**
	 * 当前订单Bean
	 */
	public static ProductInfoBean CURRENTORDER;
	/**
	 * 微博个人信息
	 */
	public static WeiboUserBean WEIBOUSER = new WeiboUserBean();
	/**
	 * 当前折扣店铺
	 */
	public static ZhekeBean ZHEBEAN;

	/**
	 * 设置当前折扣店铺
	 */
	public static void setCurrentZhe(int position) {
		ZHEBEAN = TIMZHEKE.get(position);
	}

	/**
	 * 设置当前店铺
	 */
	public static void setCurrentShop(int position) {
		CURRENTSHOP = SUNDAYINFO.get(position);
	}

	/**
	 * 设置当前对话帐号
	 */
	public static void setCurrentIC(int position) {
		CURRENTIC = CONTACTLIST.get(position);
	}

	/**
	 * 设置当前商品详情
	 */
	public static void setCurrentProduct(int position) {
		PRODUCTINFO = PROINFOLIST.get(position);
	}

	/**
	 * 服务器连接地址
	 */
	// public static String IP = "http://112.90.72.154:8888/zbbs-phone-app/";
	// public static String IP = "http://192.168.2.227:8096/zbbs-phone-app/";
	// public static String IP = "http://192.168.2.126:8096/zbbs-phone-app/";
	public static String IP = "http://112.90.72.154:8889/zbbs-phone-app/";
	/**
	 * 消费类支付宝回调地址
	 */
	public static String SUNPAIDSER = IP + "zhifubaoOrderBack";

	/**
	 * 未读消息
	 */
	public static Map<String, String> NOTREADMSG = new HashMap<String, String>();

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
	 * 修改的昵称
	 */
	public static String NICK = "";
	/**
	 * 消费码
	 */
	public static String QRCODE = "";
	/**
	 * 获取聊天信息图片
	 */
	public final static String IMAGEURL = "http://112.90.72.154:8082/resources/chats/";
	/**
	 * 获取聊天声音
	 */
	public final static String RECORDURL = "http://112.90.72.154:8082/resources/chats/audio/";

	/**
	 * 优惠券额
	 */
	public static double UnusedMoney = 0.0; // 未用
	public static double UsedMoney = 0.0; // 已用

	public static double longitude = 0.0; // 经度
	public static double latitude = 0.0; // 纬度
	public static int nshopOneType = -1; // 一级分类
	public static String nshopTwoType = ""; // 二级分类
	public static String ncolor = "all"; // 颜色

	public static String area = ""; // 一级区域
	public static String region = ""; // 二级区域
	public static int shopOneType = -1; // 一级分类
	public static String shopTwoType = ""; // 二级分类
	public static String shopTwoName = ""; // 二级分类名称
	public static String color = "all"; // 颜色

	public static List<String> TCPIC = new ArrayList<String>(); // 套餐图片

}
