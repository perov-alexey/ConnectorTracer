package ru.rsreu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @RequestMapping("/test")
    public @ResponseBody Object testMethod() {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("testView");
//        return mav;
        List test = new ArrayList();
        test.add("sdfsdf");
        return test;
    }

}
