package wepa.wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.wepa.domain.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    
    Language findByLanguageCode(String languageCode);
}
