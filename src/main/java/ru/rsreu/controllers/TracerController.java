package ru.rsreu.controllers;

import org.springframework.web.bind.annotation.*;
import ru.rsreu.tracer.algorithms.Algorithm;
import ru.rsreu.tracer.algorithms.DynamicProgrammingAlgorithm;
import ru.rsreu.tracer.helpers.FieldHelper;
import ru.rsreu.tracer.pojo.Field;

import java.util.List;

@RestController
public class TracerController {

    @RequestMapping(value = "/field", method = RequestMethod.POST)
    private @ResponseBody List<Field> traceField(@RequestBody Field field,
                                                 @RequestParam(required = false, defaultValue = "false") boolean debugEnabled,
                                                 @RequestParam(required = false, defaultValue = "true") boolean requireBestSolution) {
//        Algorithm alg = new BruteForceAlgorithm();
        Algorithm alg = new DynamicProgrammingAlgorithm();
        FieldHelper.prepareLinks(field);
        List<Field> steps = alg.execute(field, debugEnabled, requireBestSolution);
        return steps;
    }

}
