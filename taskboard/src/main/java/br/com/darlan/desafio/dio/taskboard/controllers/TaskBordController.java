package br.com.darlan.desafio.dio.taskboard.controllers;

import br.com.darlan.desafio.dio.taskboard.dto.TaskBoardDTO;
import br.com.darlan.desafio.dio.taskboard.services.TaskBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/taskboard")
public class TaskBordController {
    @Autowired
    private TaskBoardService service;

    @GetMapping
    public ResponseEntity<List<TaskBoardDTO>> list() throws SQLException {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskBoardDTO> findById(@PathVariable Long id) throws SQLException {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TaskBoardDTO> insert(@RequestBody TaskBoardDTO dto) throws SQLException {
        return ResponseEntity.ok(service.insert(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws SQLException {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.GONE).body("");
    }
}
