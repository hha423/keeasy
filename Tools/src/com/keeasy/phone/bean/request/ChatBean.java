package com.keeasy.phone.bean.request;

import java.io.Serializable;

public class ChatBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 用户联系人分组主键, 联系人分组 */
	private Integer contactGroup_IdforUser, contactGroup_IdforContact;

	/* 联系人分组类型 */
	private String groupClassified;

	/* 聊天信息主键, 用户账号主键, 联系人账号主键, 消息状态 */
	private Integer chatId, userAccount_Id, contactAccount_Id, messageSate;

	/* 用户账号, 联系人账号, 聊天内容信息 */
	private String userAccount, contactAccount, chatContent;

	/* 信息发送时间 */
	private String chatTime;

	private byte[] data; // for to image or audio

	private String isFlag; // 信息判断 文字 text 图片img 音频 audio

	public String getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(String isFlag) {
		this.isFlag = isFlag;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Integer getContactGroup_IdforUser() {
		return contactGroup_IdforUser;
	}

	public void setContactGroup_IdforUser(Integer contactGroup_IdforUser) {
		this.contactGroup_IdforUser = contactGroup_IdforUser;
	}

	public Integer getContactGroup_IdforContact() {
		return contactGroup_IdforContact;
	}

	public void setContactGroup_IdforContact(Integer contactGroup_IdforContact) {
		this.contactGroup_IdforContact = contactGroup_IdforContact;
	}

	public String getGroupClassified() {
		return groupClassified;
	}

	public void setGroupClassified(String groupClassified) {
		this.groupClassified = groupClassified;
	}

	public Integer getChatId() {
		return chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}

	public Integer getContactAccount_Id() {
		return contactAccount_Id;
	}

	public void setContactAccount_Id(Integer contactAccount_Id) {
		this.contactAccount_Id = contactAccount_Id;
	}

	public Integer getUserAccount_Id() {
		return userAccount_Id;
	}

	public void setUserAccount_Id(Integer userAccount_Id) {
		this.userAccount_Id = userAccount_Id;
	}

	public Integer getMessageSate() {
		return messageSate;
	}

	public void setMessageSate(Integer messageSate) {
		this.messageSate = messageSate;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getContactAccount() {
		return contactAccount;
	}

	public void setContactAccount(String contactAccount) {
		this.contactAccount = contactAccount;
	}

	public String getChatContent() {
		return chatContent;
	}

	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}

	public String getChatTime() {
		return chatTime;
	}

	public void setChatTime(String timestamp) {
		this.chatTime = timestamp;
	}
}