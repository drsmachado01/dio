package br.com.darlan.desafio.dio.taskboard.services;

import br.com.darlan.desafio.dio.taskboard.config.ConnectionConfig;
import br.com.darlan.desafio.dio.taskboard.dao.BlockDAO;
import br.com.darlan.desafio.dio.taskboard.dto.BlockDTO;
import br.com.darlan.desafio.dio.taskboard.utils.TaskBoardUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class BlockService {
    private BlockDAO blockDAO;
    private Connection  con;
    private ConnectionConfig connectionConfig;

    @Autowired
    public BlockService(BlockDAO blockDAO, ConnectionConfig connectionConfig) {
        this.blockDAO = blockDAO;
        this.connectionConfig = connectionConfig;
    }

    public BlockDTO save(BlockDTO blockDTO) throws SQLException {
        if(null == con || con.isClosed()) {
            con = connectionConfig.getConnection();
            blockDAO.setCon(con);
        }

        try {
            return TaskBoardUtils.fromBlockEntityToBlockDTO(blockDAO.saveBlock(TaskBoardUtils.fromBlockDTOToBlockEntity(blockDTO)));
        } catch (SQLException e) {
            log.error("Erro", e);
            throw e;
        } finally {
            con.close();
        }
    }

    public List<BlockDTO> findAll() throws SQLException {
        if(null == con || con.isClosed()) {
            con = connectionConfig.getConnection();
            blockDAO.setCon(con);
        }

        try {
            return TaskBoardUtils.fromBlockEntityListToBlockDTOList(blockDAO.getBlocks());
        } catch (SQLException e) {
            log.error("Erro", e);
            throw e;
        } finally {
            con.close();
        }
    }

    public BlockDTO findById(Long id) throws SQLException {
        if(null == con || con.isClosed()) {
            con = connectionConfig.getConnection();
            blockDAO.setCon(con);
        }

        try {
            return TaskBoardUtils.fromBlockEntityToBlockDTO(blockDAO.getBlock(id));
        } catch (SQLException e) {
            log.error("Erro", e);
            throw e;
        } finally {
            con.close();
        }
    }

    public void delete(Long id) throws SQLException {
        if (null == con || con.isClosed()) {
            con = connectionConfig.getConnection();
            blockDAO.setCon(con);
        }

        try {
            blockDAO.deleteBlock(id);
        } catch (SQLException e) {
            log.error("Erro", e);
            throw e;
        } finally {
            con.close();
        }
    }
}
