package ru.rsreu;

import org.springframework.web.bind.annotation.*;
import ru.rsreu.tracer.IOUtils;
import ru.rsreu.tracer.algorithms.Algorithm;
import ru.rsreu.tracer.algorithms.SecondAlgorithm;
import ru.rsreu.tracer.helpers.FieldHelper;
import ru.rsreu.tracer.pojo.Field;

@RestController
public class TestController {

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public @ResponseBody Field execute() {

        Algorithm alg = new SecondAlgorithm();

        Field field = IOUtils.readField("config.json");

        FieldHelper.prepareLinks(field);
        alg.execute(field);
        return field;

    }

}
