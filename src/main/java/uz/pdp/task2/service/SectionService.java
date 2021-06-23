package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Language;
import uz.pdp.task2.entity.Section;
import uz.pdp.task2.payload.SectionDto;
import uz.pdp.task2.repository.LanguageRepository;
import uz.pdp.task2.repository.SectionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    LanguageRepository languageRepository;


    public ResponseEntity<?> getAll() {
        List<Section> sectionList = sectionRepository.findAll();
        return ResponseEntity.ok(sectionList);
    }

    public ResponseEntity<?> getById(Integer id) {
        Optional<Section> sectionOptional = sectionRepository.findById(id);
        if (!sectionOptional.isPresent()){
            return ResponseEntity.status(404).body("Bunday section topilmadi");
        }
        Section section = sectionOptional.get();
        return ResponseEntity.ok(section);
    }

    public ResponseEntity<?> add(SectionDto sectionDto) {
        boolean exists = sectionRepository.existsByNameAndLanguageId(sectionDto.getName(), sectionDto.getLanguageId());
        if (exists){
            return ResponseEntity.status(409).body("Bu Language da bunday section mavjud");
        }
        Optional<Language> languageOptional = languageRepository.findById(sectionDto.getLanguageId());
        if (!languageOptional.isPresent()){
            return ResponseEntity.status(404).body("bunday language topilmadi");
        }
        Language language = languageOptional.get();
        Section section=new Section();
        section.setName(sectionDto.getName());
        section.setLanguage(language);
        sectionRepository.save(section);
        return ResponseEntity.ok("Saqlandi");
    }

    public ResponseEntity<?> edit(SectionDto sectionDto, Integer id) {
        Optional<Section> sectionOptional = sectionRepository.findById(id);
        if (!sectionOptional.isPresent()){
            return ResponseEntity.status(404).body("Bunday section topilmadi");
        }
        Optional<Language> languageOptional = languageRepository.findById(sectionDto.getLanguageId());
        if (!languageOptional.isPresent()){
            return ResponseEntity.status(404).body("Bunday language topilmadi");
        }
        boolean exists = sectionRepository.existsByNameAndLanguageId(sectionDto.getName(), sectionDto.getLanguageId());
        if (exists){
            return ResponseEntity.status(409).body("Bu Language da bunday section mavjud");
        }
        Section section = sectionOptional.get();
        section.setName(sectionDto.getName());
        Language language = languageOptional.get();
        section.setLanguage(language);
        sectionRepository.save(section);
        return ResponseEntity.ok("Tahrirlandi");
    }

    public ResponseEntity<?> delete(Integer id) {
        try {
            sectionRepository.deleteById(id);
            return ResponseEntity.ok("Uchirildi");
        }catch (Exception e){
            return ResponseEntity.status(409).body("Xatolik");
        }
    }
}
