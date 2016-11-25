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
import wepa.wepa.repository.MessageRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageTest {

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    MessageRepository messageRepository;

    @Test
    public void messageTest() {
        Message m = new Message();
        m.setMessageContent("hello");
        assertEquals("hello", m.getMessageContent());
        Language l = new Language();
        l.setLanguageName("Klingon");
        languageRepository.save(l);
        m.setMessageLanguage(l);
        assertEquals(l, m.getMessageLanguage());
        messageRepository.save(m);
        Message retrieved = messageRepository.findOne(m.getId());
        assertNotNull(retrieved);
        assertEquals("hello", retrieved.getMessageContent());
        assertEquals("Klingon", retrieved.getMessageLanguage().getLanguageName());
    }
}
