package br.com.darlan.desafio.dio.taskboard.entities;
import br.com.darlan.desafio.dio.taskboard.dto.CardDTO;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlockEntity {
    private Long id;
    private Long cardId;
    private String reason;
    private LocalDateTime blockedAt;
    private LocalDateTime unblockedAt;
    String unblockReason;
}
