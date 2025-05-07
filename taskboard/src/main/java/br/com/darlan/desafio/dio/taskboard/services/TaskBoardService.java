package br.com.darlan.desafio.dio.taskboard.services;

import br.com.darlan.desafio.dio.taskboard.config.ConnectionConfig;
import br.com.darlan.desafio.dio.taskboard.dao.ColumnDAO;
import br.com.darlan.desafio.dio.taskboard.dao.TaskBoardDAO;
import br.com.darlan.desafio.dio.taskboard.dto.TaskBoardDTO;
import br.com.darlan.desafio.dio.taskboard.entities.ColumnEntity;
import br.com.darlan.desafio.dio.taskboard.utils.TaskBoardUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class TaskBoardService {
    private TaskBoardDAO dao;
    private ColumnDAO columnDAO;
    private ConnectionConfig connectionConfig;
    private Connection con;

    public TaskBoardService(final TaskBoardDAO dao,
                            final ColumnDAO columnDAO,
                            final ConnectionConfig connectionConfig) {
        this.dao = dao;
        this.columnDAO = columnDAO;
        this.connectionConfig = connectionConfig;
    }

    public List<TaskBoardDTO> list() throws SQLException {
        try {
            if(null == this.con || this.con.isClosed()) {
                this.con = this.connectionConfig.getConnection();
                this.dao.setCon(this.con);
            }
            return TaskBoardUtils.taskBoardEntityListToTaskBoardDTOList(dao.list());
        } catch(SQLException ex) {
            log.error("Erro: ", ex);
            throw ex;
        } finally {
            if(null != con && !con.isClosed()) {
                con.close();
            }
        }
    }

    public TaskBoardDTO findById(Long id) throws SQLException {
        try {
            if(null == this.con || this.con.isClosed()) {
                this.con = this.connectionConfig.getConnection();
                this.dao.setCon(this.con);
            }
            var dto = TaskBoardUtils.taskBoardEntityToTaskBoardDTO(dao.findById(id).orElseThrow());
            this.con.commit();
            return dto;
        } catch(SQLException ex) {
            log.error("Erro: ", ex);
            throw ex;
        } finally {
            if(null != con && !con.isClosed()) {
                con.close();
            }
        }
    }

    public TaskBoardDTO insert(TaskBoardDTO dto) throws SQLException {
        try {
            if(null == this.con || this.con.isClosed()) {
                this.con = this.connectionConfig.getConnection();
                this.dao.setCon(this.con);
                this.columnDAO.setCon(this.con);
            }
            var columns = dto.getColumns();
            var tbEntity = dao.insert(dto.getName()).orElseThrow();
            columns.forEach(column -> {
                try {
                    ColumnEntity entity = TaskBoardUtils.columnDTOToColumnEntity(column, tbEntity);
                    entity.setBoard(tbEntity);
                    entity = columnDAO.insert(entity).orElseThrow();
                    tbEntity.addColumn(entity);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            dto = TaskBoardUtils.taskBoardEntityToTaskBoardDTO(tbEntity);
            this.con.commit();
            return dto;
        } catch(SQLException ex) {
            log.error("Erro: ", ex);
            throw ex;
        } finally {
            if(null != con && !con.isClosed()) {
                con.close();
            }
        }
    }

    public void delete(Long id) throws SQLException {
        try {
            if(null == this.con || this.con.isClosed()) {
                this.con = this.connectionConfig.getConnection();
                this.dao.setCon(this.con);
            }
            dao.delete(id);
            con.commit();
        } catch(SQLException ex) {
            log.error("Erro: ", ex);
            throw ex;
        } finally {
            if(null != con && !con.isClosed()) {
                con.close();
            }
        }
    }
}
