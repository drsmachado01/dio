package br.com.darlan.desafio.dio.taskboard.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskBoardDTO{
    private Long id;
    private String name;
    private List<ColumnDTO> columns;
}
