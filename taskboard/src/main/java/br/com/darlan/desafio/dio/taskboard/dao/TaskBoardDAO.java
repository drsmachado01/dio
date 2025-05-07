package br.com.darlan.desafio.dio.taskboard.dao;

import br.com.darlan.desafio.dio.taskboard.entities.ColumnEntity;
import br.com.darlan.desafio.dio.taskboard.entities.TaskBoardEntity;
import br.com.darlan.desafio.dio.taskboard.enumerations.ColumnKind;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mysql.cj.jdbc.StatementImpl;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Setter
public class TaskBoardDAO {
    private Connection con;

    public List<TaskBoardEntity> list() throws SQLException {
        String query = "SELECT b.id, b.name, c.id, c.name, c.order, c.kind " +
                "FROM BOARDS b " +
                "INNER JOIN BOARD_COLUMNS c ON c.board_id = b.id";
        List<TaskBoardEntity> boards = new ArrayList<>();
        try(PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            TaskBoardEntity entity = null;
            while (rs.next()) {
                Long boardID = rs.getLong("b.id");
                if(entity == null || !boardID.equals(entity.getId())) {
                    entity = TaskBoardEntity.builder()
                            .id(boardID)
                            .name(rs.getString("b.name"))
                            .build();
                    boards.add(entity);
                }
                ColumnEntity ce = getColumnEntity(rs, entity);
                entity.addColumn(ce);
            }

            return boards;
        }
    }

    public Optional<TaskBoardEntity> findById(Long id) throws SQLException {
        String query = "SELECT b.id, b.name, c.id, c.name, c.order, c.kind " +
                "FROM BOARDS b " +
                "INNER JOIN BOARD_COLUMNS c ON c.board_id = b.id " +
                "WHERE b.id = ?";
        TaskBoardEntity board = null;
        try(PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, id);
            stm.executeQuery();
            ResultSet rs = stm.getResultSet();
            while(rs.next()) {
                if(null == board) {
                    board = TaskBoardEntity.builder()
                            .id(rs.getLong("b.id"))
                            .name(rs.getString("b.name"))
                            .build();
                }
                ColumnEntity ce = getColumnEntity(rs, board);
                board.addColumn(ce);
            }
            return Optional.ofNullable(board);
        }
    }

    private static ColumnEntity getColumnEntity(ResultSet rs, TaskBoardEntity board) throws SQLException {
        ColumnEntity ce = ColumnEntity.builder()
                .id(rs.getLong("c.id"))
                .name(rs.getString("c.name"))
                .order(rs.getInt("c.order"))
                .kind(ColumnKind.fromName(rs.getString("c.kind")))
                .build();
        return ce;
    }

    public void delete(Long id) throws SQLException {
        String query = "DELETE FROM BOARDS WHERE id = ?";
        TaskBoardEntity board = null;
        try(PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, id);
            stm.executeUpdate();
        } catch(Exception ex) {
            log.error("ERRO", ex);
            throw new SQLException(ex.getMessage(), ex.getCause());
        }
    }

    public Optional<TaskBoardEntity> insert(String name) throws SQLException {
        String query = "INSERT INTO BOARDS(name) VALUES (?)";
        TaskBoardEntity board = null;
        try(PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, name);
            stm.executeUpdate();
            if(stm instanceof StatementImpl impl) {
                board = TaskBoardEntity.builder()
                        .id(impl.getLastInsertID())
                        .name(name)
                        .build();
            }
            return Optional.ofNullable(board);
        }
    }
}
