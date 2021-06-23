package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Language;
import uz.pdp.task2.payload.LanguageDto;
import uz.pdp.task2.repository.LanguageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {
    
    @Autowired
    LanguageRepository languageRepository;
    
    
    public ResponseEntity<?> getAll() {
        List<Language> all = languageRepository.findAll();
        return ResponseEntity.ok(all);
    }

    public ResponseEntity<?> getById(Integer id) {
        Optional<Language> languageOptional = languageRepository.findById(id);
        if (!languageOptional.isPresent()){
            return ResponseEntity.status(404).body("Bunday Language mavjud emas");
        }
        Language language = languageOptional.get();
        return ResponseEntity.ok(language);
    }

    public ResponseEntity<?> add(LanguageDto languageDto) {
        boolean exists = languageRepository.existsByName(languageDto.getName());
        if (exists){
            return ResponseEntity.status(409).body("Bunday language mavjud");
        }
        Language language=new Language();
        language.setName(languageDto.getName());
        languageRepository.save(language);
        return ResponseEntity.ok("Saqlandi");
    }

    public ResponseEntity<?> edit(Integer id, LanguageDto languageDto) {
        Optional<Language> languageOptional = languageRepository.findById(id);
        if (!languageOptional.isPresent()){
            return ResponseEntity.status(404).body("Bunday mavjud emas");
        }
        Language language = languageOptional.get();
        language.setName(languageDto.getName());
        languageRepository.save(language);
        return ResponseEntity.ok("Tahrirlandi");
    }

    public ResponseEntity<?> delete(Integer id) {
        try {
            languageRepository.deleteById(id);
            return ResponseEntity.ok("Uchirildi");
        }catch (Exception e){
            return ResponseEntity.status(409).body("Xatolik");
        }
    }
}
