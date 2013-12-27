package cn.keeasy.mobilekeeasy.entity;

import java.util.List;

/**
 * 店铺bean
 * 
 * @author Evan
 * 
 */
public class ShopBean {
	public int shopId; // 商家id
	public List<SundayShopBean> dayType; // 星期天类型
	public String bAccount; // 商家账号
	public String shopImg; // 星期天图片
	public String shopName; // 商家名称
	public String shopTime; // 营业时间
	public String shopAddress; // 商家地址
	public double distace; // 距离
	public int item;
	public int by; // 排序

}