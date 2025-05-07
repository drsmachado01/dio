package br.com.darlan.desafio.dio.taskboard.utils;

import br.com.darlan.desafio.dio.taskboard.dto.ColumnDTO;
import br.com.darlan.desafio.dio.taskboard.dto.TaskBoardDTO;
import br.com.darlan.desafio.dio.taskboard.entities.ColumnEntity;
import br.com.darlan.desafio.dio.taskboard.entities.TaskBoardEntity;
import br.com.darlan.desafio.dio.taskboard.enumerations.ColumnKind;

import java.util.List;

public class TaskBoardUtils {
    public static TaskBoardDTO taskBoardEntityToTaskBoardDTO(TaskBoardEntity entity) {
        var tbd = TaskBoardDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
        var columns = columnEntityListToColumnDTOList(entity.getColumns(), tbd);
        tbd.setColumns(columns);
        return tbd;
    }

    public static TaskBoardEntity taskBoardDTOToTaskBoardEntity(TaskBoardDTO dto) {
        var tbe = TaskBoardEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
        var columns = columnDTOListToColumnEntityList(dto.getColumns(), tbe);
        tbe.setColumns(columns);
        return tbe;
    }

    public static List<TaskBoardEntity> taskBoardDTOListToTaskBoardEntityList(List<TaskBoardDTO> dtos) {
        if(null == dtos) {
            return null;
        }
        return dtos.stream().map(TaskBoardUtils::taskBoardDTOToTaskBoardEntity).toList();
    }

    public static List<TaskBoardDTO> taskBoardEntityListToTaskBoardDTOList(List<TaskBoardEntity> entities) {
        if(null == entities) {
            return null;
        }
        return entities.stream().map(TaskBoardUtils::taskBoardEntityToTaskBoardDTO).toList();
    }

    public static ColumnDTO columnEntityToColumnDTO(ColumnEntity entity, TaskBoardDTO tbd) {
        return new ColumnDTO(entity.getId(), entity.getName(), entity.getOrder(), entity.getKind().name());
    }

    public static ColumnEntity columnDTOToColumnEntity(ColumnDTO dto, TaskBoardEntity tbe) {
        return ColumnEntity.builder()
                .board(tbe)
                .kind(ColumnKind.fromName(dto.kind()))
                .order(dto.order())
                .name(dto.name())
                .id(dto.id())
                .build();
    }

    public static List<ColumnEntity> columnDTOListToColumnEntityList(List<ColumnDTO> dtos, TaskBoardEntity tbe) {
        if(null == dtos) {
            return null;
        }
        return dtos.stream().map(dto -> TaskBoardUtils.columnDTOToColumnEntity(dto, tbe)).toList();
    }

    public static List<ColumnDTO> columnEntityListToColumnDTOList(List<ColumnEntity> entities, TaskBoardDTO tbd) {
        if(null == entities) {
            return null;
        }
        return entities.stream().map(entity -> TaskBoardUtils.columnEntityToColumnDTO(entity, tbd)).toList();
    }
}
