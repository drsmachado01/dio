package br.com.darlan.desafio.dio.taskboard.dao;

import br.com.darlan.desafio.dio.taskboard.entities.CardEntity;
import com.mysql.cj.jdbc.StatementImpl;
import lombok.Setter;
import org.flywaydb.database.mysql.MySQLConnection;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Setter
@Component
public class CardDAO {
    private Connection con;

    public List<CardEntity> getCards(Connection con) throws SQLException {
        String query = "SELECT * FROM CARDS";
        try(PreparedStatement stm = con.prepareStatement(query)) {
            stm.executeQuery();
            var rs = stm.getResultSet();
            List<CardEntity> cards = new ArrayList<>();
            while (rs.next()) {
                cards.add(CardEntity.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .description(rs.getString("description"))
                        .columnId(rs.getLong("column_id"))
                        .build());
            }
            return cards;

        }
    }

    public CardEntity getCard(Connection con, Long id) throws SQLException {
        String query = "SELECT * FROM CARDS WHERE ID = ?";
        try(PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, id);
            stm.executeQuery();
            var rs = stm.getResultSet();
            if (rs.next()) {
                return CardEntity.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .description(rs.getString("description"))
                        .columnId(rs.getLong("column_id"))
                        .build();
            }
            return null;
        }
    }

    public CardEntity save(Connection con, CardEntity card) throws SQLException {
        String query = "INSERT INTO CARDS (title, description, column_id) VALUES (?, ?, ?)";
        try(PreparedStatement stm = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, card.getTitle());
            stm.setString(2, card.getDescription());
            stm.setLong(3, card.getColumnId());
            stm.executeUpdate();
            var rs = stm.getGeneratedKeys();
            if(stm instanceof StatementImpl impl) {
                card.setId(impl.getLastInsertID());
            }
            return card;
        }
    }

    public void delete(Connection con, Long id) throws SQLException {
        String query = "DELETE FROM CARDS WHERE ID = ?";
        try(PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, id);
            stm.executeUpdate();
        }
    }
}
