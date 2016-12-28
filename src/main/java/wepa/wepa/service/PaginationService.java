package wepa.wepa.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import wepa.wepa.domain.Course;
import wepa.wepa.domain.Person;

@Service
public class PaginationService {

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

}
