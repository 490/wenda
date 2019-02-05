package com.zhaole.service;

import com.zhaole.model.Message;

import java.util.List;

public interface MessageService
{
    public int addMessage(Message message);
    public List<Message> getConversationDetail(String conversationId, int offset, int limit);
    public List<Message> getConversationList(int userId, int offset, int limit);
    public int getConversationUnreadCount(int userId, String conversationId);
}
