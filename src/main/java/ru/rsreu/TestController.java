package ru.rsreu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    @RequestMapping("test")
    public ModelAndView testMethod() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("testView");
        return mav;
    }

}
