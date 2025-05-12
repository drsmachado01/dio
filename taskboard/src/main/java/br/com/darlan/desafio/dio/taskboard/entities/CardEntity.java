package br.com.darlan.desafio.dio.taskboard.entities;
import br.com.darlan.desafio.dio.taskboard.dto.ColumnDTO;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardEntity {
    private Long id;
    private Long columnId;
    private String title;
    private String description;
}
