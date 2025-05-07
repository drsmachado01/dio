package br.com.darlan.desafio.dio.taskboard.dto;

import java.time.LocalDateTime;

public record BlockDTO(CardDTO card,
                       Long id,
                       String reason,
                       LocalDateTime blockedAt,
                       LocalDateTime unblockedAt,
                       String unblockReason) { }
