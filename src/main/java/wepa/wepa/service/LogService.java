package wepa.wepa.service;

import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import wepa.wepa.Wepa;
import wepa.wepa.domain.Person;
import wepa.wepa.repository.PersonRepository;

@Service
public class LogService {

    @Autowired
    private PersonRepository personRepository;

    static Logger log = Logger.getLogger(Wepa.class.getName());

    public void info(String message) {
        log.info(message);

    }

    public void debug(String message) {
        log.debug(message);

    }

//    public void log(LogHandle logHandle, String message) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth != null) {
//            // for testing 
//
//            Person loggedIn = personRepository.findByName(auth.getName());
//
//            Log log = new Log();
//
//            log.setLogMessage(message);
//            log.setPerson(loggedIn);
//            log.setDate(new Date(System.currentTimeMillis()));
//            log.setLogHandle(logHandle);
//
//            logRepository.save(log);
//        }
//    }
}
