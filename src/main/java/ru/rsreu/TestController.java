package ru.rsreu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rsreu.tracer.Field;

@Controller
public class TestController {

    @RequestMapping("/test")
    public Field testMethod() {
        Field test = new Field();
        test.setTest("sdasdasd");
        return test;
    }

}
