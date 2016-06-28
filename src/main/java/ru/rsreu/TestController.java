package ru.rsreu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.rsreu.tracer.Shop;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public @ResponseBody Shop getShopInJSON(@PathVariable String name) {

        Shop shop = new Shop();
        shop.setName(name);
        shop.setStaffName(new String[]{"mkyong1", "mkyong2"});

        return shop;

    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public @ResponseBody String returnString(@PathVariable String name) {
        return "hello!";

    }

}
