package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.payload.SectionDto;
import uz.pdp.task2.service.SectionService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/section")
public class SectionController {

    @Autowired
    SectionService sectionService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return sectionService.getAll();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getById(@PathVariable Integer id){
        return  sectionService.getById(id);
    }

    @PostMapping
    public  ResponseEntity<?> add(@Valid @RequestBody SectionDto sectionDto){
        return sectionService.add(sectionDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody SectionDto sectionDto, @PathVariable Integer id){
        return sectionService.edit(sectionDto,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return sectionService.delete(id);
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
