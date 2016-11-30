package wepa.wepa.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Profile("default")
@Controller
public class SandboxController {

    @RequestMapping("/sandbox")
    public String handleDefault() {
        return "showWeek";
    }
}
