package com.linkee.linkeeapi.chat_message.command.application.controller;

import com.linkee.linkeeapi.chat_message.command.application.service.ChatMessageMongoService;
import com.linkee.linkeeapi.chat_message.command.domain.aggregate.entity.ChatMessageMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mongo/chat")
public class ChatMessageMongoController {

    private final ChatMessageMongoService chatMessageMongoService;

    @PostMapping
    public ResponseEntity<ChatMessageMongo> sendMessage(@RequestBody ChatMessageMongo message) {
        return ResponseEntity.ok(chatMessageMongoService.save(message));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<List<ChatMessageMongo>> getMessages(@PathVariable Long roomId) {
        return ResponseEntity.ok(chatMessageMongoService.getMessages(roomId));
    }
}
