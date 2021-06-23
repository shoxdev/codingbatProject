package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.payload.TaskDto;
import uz.pdp.task2.service.TaskService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getById(@PathVariable Integer id){
        return  taskService.getById(id);
    }

    @PostMapping
    public  ResponseEntity<?> add(@Valid @RequestBody TaskDto taskDto){
        return taskService.add(taskDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody TaskDto taskDto, @PathVariable Integer id){
        return taskService.edit(taskDto,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return taskService.delete(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
