package br.com.darlan.desafio.dio.taskboard.dto;

public record CardDTO(ColumnDTO column,
                      Long id,
                      String title,
                      String description) { }
