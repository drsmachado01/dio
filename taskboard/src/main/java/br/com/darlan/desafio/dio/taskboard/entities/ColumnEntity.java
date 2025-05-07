package br.com.darlan.desafio.dio.taskboard.entities;

import br.com.darlan.desafio.dio.taskboard.enumerations.ColumnKind;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ColumnEntity {
    private Long id;
    private TaskBoardEntity board;
    private String name;
    private Integer order;
    private ColumnKind kind;
}
