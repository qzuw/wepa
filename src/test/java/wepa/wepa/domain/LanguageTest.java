package wepa.wepa.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wepa.wepa.repository.LanguageRepository;
import wepa.wepa.repository.LogRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LanguageTest {

    @Autowired
    LanguageRepository languageRepository;

    @Test
    public void testLanguage() {
        Language l = new Language();
        l.setLanguageName("Klingon");
        l.setLanguageCode("k4");
        assertEquals("Klingon", l.getLanguageName());
        languageRepository.save(l);

        assertEquals(l, languageRepository.findOne(l.getId()));
        assertEquals("Klingon", languageRepository.findOne(l.getId()).getLanguageName());
    }
}
