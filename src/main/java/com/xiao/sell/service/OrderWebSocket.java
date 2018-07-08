package com.xiao.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/webSocket")
public class OrderWebSocket {

    private Session session;

    private static CopyOnWriteArraySet<OrderWebSocket> orderWebSockets=new CopyOnWriteArraySet<>();

    @OnOpen
    public  void OnOpen(Session session){
        this.session=session;
        orderWebSockets.add(this);
        log.info("【websocket消息】有新的连接, 总数:{}", orderWebSockets.size());

    }

    @OnClose
    public void OnClose(){
        orderWebSockets.remove(this);
        log.info("【websocket消息】连接断开, 总数:{}", orderWebSockets.size());

    }

    @OnMessage
    public  void OnMessage(String message){
        log.info("【websocket消息】收到客户端发来的消息:{}", message);

    }

    public void sendMessage(String message){
        for (OrderWebSocket o:orderWebSockets) {
            log.info("【websocket消息】广播消息, message={}", message);
            try {
                o.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

}
