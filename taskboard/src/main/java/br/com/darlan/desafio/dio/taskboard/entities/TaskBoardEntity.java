package br.com.darlan.desafio.dio.taskboard.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskBoardEntity {
    private Long id;
    private String name;
    private List<ColumnEntity> columns;

    public void addColumn(ColumnEntity column) {
        if(null == columns) {
            columns = new ArrayList<>();
        }
        columns.add(column);
    }
}
