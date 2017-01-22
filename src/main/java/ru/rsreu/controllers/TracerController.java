package ru.rsreu.controllers;

import org.springframework.web.bind.annotation.*;
import ru.rsreu.tracer.algorithms.Algorithm;
import ru.rsreu.tracer.algorithms.FirstAlgorithm;
import ru.rsreu.tracer.helpers.FieldHelper;
import ru.rsreu.tracer.pojo.Field;

import java.util.List;

@RestController
public class TracerController {

    @RequestMapping(value = "/field", method = RequestMethod.POST)
    private @ResponseBody List<Field> traceField(@RequestBody Field field) {
        Algorithm alg = new FirstAlgorithm();
        FieldHelper.prepareLinks(field);
        List<Field> steps = alg.execute(field);
        return steps;
    }

}
