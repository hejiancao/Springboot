package com.example.demo;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author created by shaos on 2019/11/7
 * 参考链接：https://www.cnblogs.com/freud/p/8397934.html
 *          https://www.jianshu.com/p/1d05d3692c19
 */
@ServerEndpoint("/websocket/{username}")
public class WebSocket {

    private static AtomicInteger count = new AtomicInteger(0);
    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
    private Session session;
    private String username;

    private static Map<String, WebSocket> getClient() {
        return clients;
    }

    @OnOpen
    public void onOpen(@PathParam("username")String username, Session session) {
        this.username = username;
        this.session = session;
        addOnlineCount();
        clients.put(username, this);
        System.out.println("已连接");
    }

    @OnClose
    public void onClose() {
        clients.remove(username);
        subOnlineCount();
        System.out.println("已关闭连接");
    }


    @OnMessage
    public void onMessage(String message) throws IOException {
        JSONObject json = JSONObject.parseObject(message);
        String msg = json.getString("message");
        if (!json.get("To").equals("All")) {
            sendMessageTo(msg, json.get("To").toString());
        } else {
            sendMessageAll("给所有人");
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    private void sendMessageAll(String message) {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }


    public void sendMessageTo(String message, String To) throws IOException {
        // session.getBasicRemote().sendText(message);
        // session.getAsyncRemote().sendText(message);
        clients.get(To).session.getAsyncRemote().sendText(message);
    }

    private void subOnlineCount() {
        count.decrementAndGet();
    }


    private void addOnlineCount() {
        count.incrementAndGet();
    }


}
