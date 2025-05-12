package br.com.darlan.desafio.dio.taskboard.dao;

import br.com.darlan.desafio.dio.taskboard.entities.BlockEntity;
import com.mysql.cj.jdbc.StatementImpl;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Setter
public class BlockDAO {
    private Connection con;

    public List<BlockEntity> getBlocks() throws SQLException {
        String query = "SELECT * FROM blocks";
        try(PreparedStatement stm = con.prepareStatement(query)) {
            stm.executeQuery();
            var rs = stm.getResultSet();
            List<BlockEntity> blocks = new ArrayList<>();
            while (rs.next()) {
                blocks.add(BlockEntity.builder()
                        .id(rs.getLong("id"))
                        .cardId(rs.getLong("card_id"))
                        .reason(rs.getString("reason"))
                        .blockedAt(rs.getTimestamp("blocked_at").toLocalDateTime())
                        .unblockReason(rs.getString("unblock_reason"))
                        .unblockedAt(rs.getTimestamp("unblocked_at").toLocalDateTime())
                        .build());
            }
            return blocks;
        }
    }

    public BlockEntity getBlock(Long id) throws SQLException {
        String query = "SELECT * FROM blocks WHERE id = ?";
        try(PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, id);
            stm.executeQuery();
            var rs = stm.getResultSet();
            if(rs.next()) {
                return BlockEntity.builder()
                        .id(rs.getLong("id"))
                        .cardId(rs.getLong("card_id"))
                        .reason(rs.getString("reason"))
                        .blockedAt(rs.getTimestamp("blocked_at").toLocalDateTime())
                        .unblockReason(rs.getString("unblock_reason"))
                        .unblockedAt(rs.getTimestamp("unblocked_at").toLocalDateTime())
                        .build();
            }
            return null;
        }
    }

    public BlockEntity saveBlock(BlockEntity block) throws SQLException {
        String query = "INSERT INTO blocks(card_id, reason, blocked_at, unblock_reason, unblocked_at) VALUES(?, ?, ?, ?, ?)";
        try(PreparedStatement stm = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stm.setLong(1, block.getCardId());
            stm.setString(2, block.getReason());
            stm.setTimestamp(3, java.sql.Timestamp.valueOf(block.getBlockedAt()));
            stm.setString(4, block.getUnblockReason());
            stm.setTimestamp(5, java.sql.Timestamp.valueOf(block.getUnblockedAt()));
            stm.executeUpdate();
            var rs = stm.getGeneratedKeys();
            if(stm instanceof StatementImpl impl) {
                block.setId(impl.getLastInsertID());
            }
            return block;
        }
    }

    public void deleteBlock(Long id) throws SQLException {
        String query = "DELETE FROM blocks WHERE id = ?";
        try(PreparedStatement stm = con.prepareStatement(query)) {
            stm.setLong(1, id);
            stm.executeUpdate();
        }
    }
}
