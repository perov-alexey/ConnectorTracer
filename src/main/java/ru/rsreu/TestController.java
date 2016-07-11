package ru.rsreu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.rsreu.tracer.Shop;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public @ResponseBody Shop getShopInJSON(@RequestParam String name, @RequestBody Shop shop2) {

        Shop shop = new Shop();
        shop.setName(name);
        shop.setStaffName(new String[]{"mkyong1", "mkyong2"});

        return shop;

    }

}
