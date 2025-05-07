package br.com.darlan.desafio.dio.taskboard.dao;

import br.com.darlan.desafio.dio.taskboard.entities.ColumnEntity;
import br.com.darlan.desafio.dio.taskboard.entities.TaskBoardEntity;
import br.com.darlan.desafio.dio.taskboard.enumerations.ColumnKind;
import br.com.darlan.desafio.dio.taskboard.utils.TaskBoardUtils;
import com.mysql.cj.jdbc.StatementImpl;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Setter
public class ColumnDAO {
    private Connection con;

    public Optional<ColumnEntity> insert(ColumnEntity entity) throws SQLException {
        String query = "INSERT INTO BOARD_COLUMNS(`NAME`, `ORDER`, `KIND`, `BOARD_ID`) VALUES (?,?,?,?)";
        try(PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, entity.getName());
            stm.setInt(2, entity.getOrder());
            stm.setString(3, entity.getKind().name());
            stm.setLong(4, entity.getBoard().getId());

            stm.executeUpdate();
            if(stm instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
                return Optional.of(entity);
            }
            return Optional.empty();
        }
    }

    public List<ColumnEntity> list() throws SQLException {
        String query = "SELECT ´id`, `name , `order`, `kind`, `board_id` " +
                "FROM BOARD_COLUMNS";
        try(PreparedStatement stm = con.prepareStatement(query)) {
            stm.executeQuery();
            var rs = stm.getResultSet();
            List<ColumnEntity> columns = new ArrayList<>();
            while(rs.next()) {
                columns.add(ColumnEntity.builder()
                                .id(rs.getLong("id"))
                                .name(rs.getString("name"))
                                .kind(ColumnKind.fromName(rs.getString("kind")))
                                .order(rs.getInt("order"))
                                .board(TaskBoardEntity.builder().id(rs.getLong("board_id")).build())
                        .build());
            }
            return columns;
        }
    }
}
