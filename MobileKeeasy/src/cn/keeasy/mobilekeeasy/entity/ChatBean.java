package cn.keeasy.mobilekeeasy.entity;

public class ChatBean {

	/* 聊天信息主键, 用户账号主键, 联系人账号主键, 消息状态 */
	public Integer chatId, userAccount_Id, contactAccount_Id, messageSate;

	/* 用户账号, 联系人账号, 聊天内容信息 */
	public String userAccount, contactAccount, chatContent;

	/* 信息发送时间 */
	public String chatTime;

	public byte[] data; // for to image or audio

	public String type; // 信息判断 文字 text 图片img 音频 audio

}