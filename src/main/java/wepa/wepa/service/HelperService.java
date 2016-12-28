package wepa.wepa.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import wepa.wepa.domain.Course;
import wepa.wepa.domain.Person;
import wepa.wepa.domain.Week;
import wepa.wepa.repository.WeekRepository;

@Service
public class HelperService {

    @Autowired
    private WeekRepository weeklyExerciseRepository;

    public Model countPagination(int pageNum, int pageTotal, Model model, String path) {
        int current = pageNum + 1;
        int previous = current - 1;
        int next = current + 1;
        if (previous >= 1) {
            model.addAttribute("previous", previous);
        }

        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, pageTotal);

        if (next <= end) {
            model.addAttribute("next", next);
        }

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("pagePath", path);

        return model;
    }

//    private Model pagination(Page<Object> page, Model model, String path) {
//        int current = page.getNumber() + 1;
//        int previous = current - 1;
//        int next = current + 1;
//        if (previous >= 1) {
//            model.addAttribute("previous", previous);
//        }
//
//        int begin = Math.max(1, current - 5);
//        int end = Math.min(begin + 10, page.getTotalPages());
//
//        if (next <= end) {
//            model.addAttribute("next", next);
//        }
//
//        model.addAttribute("personLog", page);
//        model.addAttribute("beginIndex", begin);
//        model.addAttribute("endIndex", end);
//        model.addAttribute("currentIndex", current);
//        model.addAttribute("pagePath", path);
//
//        return model;
//    }
    public List<Week> generateWeekList(Integer weeks, Course course) {

        if (weeks == null) {
            weeks = 0;
        }

        List<Week> weeklist = new ArrayList<>();
        for (int i = 1; i <= weeks; i++) {
            Week we = new Week();
            we.setWeek(i);
            we.setDescription("" + i);
            we.setCourse(course);
            weeklyExerciseRepository.save(we);
            weeklist.add(we);
        }
        return weeklist;
    }
}
