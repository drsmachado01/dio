package br.com.darlan.desafio.dio.taskboard.services;

import br.com.darlan.desafio.dio.taskboard.config.ConnectionConfig;
import br.com.darlan.desafio.dio.taskboard.dao.CardDAO;
import br.com.darlan.desafio.dio.taskboard.dto.CardDTO;
import br.com.darlan.desafio.dio.taskboard.utils.TaskBoardUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class CardService {
    private CardDAO cardDAO;
    private Connection con;
    private ConnectionConfig connectionConfig;

    public CardService(CardDAO cardDAO, ConnectionConfig connectionConfig) {
        this.cardDAO = cardDAO;
        this.connectionConfig = connectionConfig;
    }

    public CardDTO save(CardDTO cardDTO) throws SQLException {
        if(null == con || con.isClosed()) {
            con = connectionConfig.getConnection();
        }
        try {
            con = connectionConfig.getConnection();
            return TaskBoardUtils.fromCardEntityToCardDTO(cardDAO.save(con, TaskBoardUtils.fromCardDTOToCardEntity(cardDTO)));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            con.close();
        }
    }

    public List<CardDTO> findAll() throws SQLException {
        if (null == con || con.isClosed()) {
            con = connectionConfig.getConnection();
        }
        try {
            con = connectionConfig.getConnection();
            return TaskBoardUtils.fromCardEntityListToCardDTOList(cardDAO.getCards(con));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            con.close();
        }
    }

    public CardDTO findById(Long id) throws SQLException {
        if (null == con || con.isClosed()) {
            con = connectionConfig.getConnection();
        }
        try {
            con = connectionConfig.getConnection();
            return TaskBoardUtils.fromCardEntityToCardDTO(cardDAO.getCard(con, id));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            con.close();
        }
    }

    public void delete(Long id) throws SQLException {
        if (null == con || con.isClosed()) {
            con = connectionConfig.getConnection();
        }
        try {
            con = connectionConfig.getConnection();
            cardDAO.delete(con, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            con.close();
        }
    }
}
