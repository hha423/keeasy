package cn.keeasy.mobilekeeasy.entity;

import java.util.List;

public class SunOrderBean {

	public List<ProductInfoBean> productList;
	public double totalPrice; // 原总价
	public double serviceTotalPrice; // 星期天总价
	public double serviceSubscription; // 支付金额
	public String serviceCode; // 消费码
	public String serviceBalance; // 帐户余额

}
