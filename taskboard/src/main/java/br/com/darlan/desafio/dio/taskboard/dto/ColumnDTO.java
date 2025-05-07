package br.com.darlan.desafio.dio.taskboard.dto;

public record ColumnDTO(Long id,
                        String name,
                        Integer order,
                        String kind) { }
