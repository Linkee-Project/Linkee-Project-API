package com.example.linkeeuserservice.relation.command.application.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelationCreateRequest {
    private Long requesterId;
    private Long receiverId;
}
