package uz.pdp.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task2.entity.Section;

public interface SectionRepository extends JpaRepository<Section,Integer> {

    boolean existsByNameAndLanguageId(String name, Integer language_id);
}
