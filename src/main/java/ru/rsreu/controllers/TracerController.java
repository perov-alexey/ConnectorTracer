package ru.rsreu.controllers;

import org.springframework.web.bind.annotation.*;
import ru.rsreu.tracer.algorithms.Algorithm;
import ru.rsreu.tracer.algorithms.SecondAlgorithm;
import ru.rsreu.tracer.helpers.FieldHelper;
import ru.rsreu.tracer.pojo.Field;

@RestController
public class TracerController {

    @RequestMapping(value = "/field", method = RequestMethod.POST, headers = {"content-type=application/json"})
    private @ResponseBody Field traceField(@RequestBody Field field) {
        Algorithm alg = new SecondAlgorithm();
        FieldHelper.prepareLinks(field);
        alg.execute(field);
        return field;
    }

}
