package wepa.wepa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExController {
    @RequestMapping("/ex")
    public String handleDefault() {
        return "fillExercises";
    }
}
