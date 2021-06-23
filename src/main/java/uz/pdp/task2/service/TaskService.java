package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Section;
import uz.pdp.task2.entity.Task;
import uz.pdp.task2.payload.TaskDto;
import uz.pdp.task2.repository.SectionRepository;
import uz.pdp.task2.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    TaskRepository taskRepository;


    public ResponseEntity<?> getAll() {
        List<Task> taskList = taskRepository.findAll();
        return ResponseEntity.ok(taskList);
    }

    public ResponseEntity<?> getById(Integer id) {
        Optional<Task> tasksOptional = taskRepository.findById(id);
        if (!tasksOptional.isPresent()){
            return ResponseEntity.status(404).body("Bunday task topilmadi");
        }
        Task task = tasksOptional.get();
        return ResponseEntity.ok(task);
    }

    public ResponseEntity<?> add(TaskDto taskDto) {
        boolean exists = taskRepository.existsByNameAndSectionId(taskDto.getName(), taskDto.getSectionId());
        if (exists){
            return ResponseEntity.status(409).body("Bu Section da bunday task mavjud");
        }
        Optional<Section> sectionOptional = sectionRepository.findById(taskDto.getSectionId());
        if (!sectionOptional.isPresent()){
            return ResponseEntity.status(404).body("bunday section topilmadi");
        }
        Section section = sectionOptional.get();
        Task task=new Task();
        task.setName(taskDto.getName());
        task.setSection(section);
        taskRepository.save(task);
        return ResponseEntity.ok("Saqlandi");
    }

    public ResponseEntity<?> edit(TaskDto taskDto, Integer id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()){
            return ResponseEntity.status(404).body("Bunday task topilmadi");
        }
        Optional<Section> sectionOptional = sectionRepository.findById(taskDto.getSectionId());
        if (!sectionOptional.isPresent()){
            return ResponseEntity.status(404).body("Bunday language topilmadi");
        }
        boolean exists = taskRepository.existsByNameAndSectionId(taskDto.getName(), taskDto.getSectionId());
        if (exists){
            return ResponseEntity.status(409).body("Bu Section da bunday task mavjud");
        }
        Task task = taskOptional.get();
        task.setName(taskDto.getName());
        Section section = sectionOptional.get();
        task.setSection(section);
        taskRepository.save(task);
        return ResponseEntity.ok("Tahrirlandi");
    }

    public ResponseEntity<?> delete(Integer id) {
        try {
            taskRepository.deleteById(id);
            return ResponseEntity.ok("Uchirildi");
        }catch (Exception e){
            return ResponseEntity.status(409).body("Xatolik");
        }
    }
}
