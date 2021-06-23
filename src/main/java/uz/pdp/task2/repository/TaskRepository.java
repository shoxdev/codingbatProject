package uz.pdp.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task2.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Integer> {

    boolean existsByNameAndSectionId(String name, Integer section_id);
}
