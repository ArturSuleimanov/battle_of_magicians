package com.interview.magicians.handlers;

import com.interview.magicians.dto.Greeting;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.util.HtmlUtils;

import java.util.HashSet;
import java.util.Objects;

@Component
@AllArgsConstructor
public class WebSocketEventHandler {

    public static final HashSet<String> participiants = new HashSet<>();

    private final SimpMessagingTemplate template;
    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        template.convertAndSend("/topic/greetings", new Greeting(HtmlUtils.htmlEscape(Objects.requireNonNull(event.getUser()).getName()) + " has joined!"));
        participiants.add(event.getUser().getName());
        template.convertAndSend("/topic/participiants", participiants);
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        template.convertAndSend("/topic/greetings", new Greeting(HtmlUtils.htmlEscape(Objects.requireNonNull(event.getUser()).getName()) + " has disconnected!"));
        participiants.remove(event.getUser().getName());
        template.convertAndSend("/topic/participiants", participiants);
    }

    @EventListener
    private void handleSessionSubscribe(SessionSubscribeEvent event) {
        template.convertAndSendToUser(event.getUser().getName(), "/queue/participiants", participiants);
    }

}
