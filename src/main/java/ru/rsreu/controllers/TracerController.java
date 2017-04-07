package ru.rsreu.controllers;

import org.springframework.web.bind.annotation.*;
import ru.rsreu.factory.AlgorithmFactory;
import ru.rsreu.factory.AlgorithmTypes;
import ru.rsreu.tracer.algorithms.Algorithm;
import ru.rsreu.tracer.helpers.FieldHelper;
import ru.rsreu.tracer.pojo.Field;

import java.util.List;

@RestController
public class TracerController {

    /**
     * Trace field
     * @param field Field to be parsed
     * @param debugEnabled Enable debug mode, be aware, not all algorithms support this mode
     * @param requireBestSolution Enable it to find best solution (not only feasible solution). Be aware, in most cases
     *                            this algorithm can take a lot of memory and time
     * @param algorithmType Algorithm type, all types are listed in AlgorithmTypes enum
     * @return Traced field
     */
    @RequestMapping(value = "/traced", method = RequestMethod.POST)
    private @ResponseBody List<Field> traceField(@RequestBody Field field,
                                                 @RequestParam(required = false, defaultValue = "false") boolean debugEnabled,
                                                 @RequestParam(required = false, defaultValue = "true") boolean requireBestSolution,
                                                 @RequestParam String algorithmType) {
        Algorithm alg = AlgorithmFactory.createAlgorithm(AlgorithmTypes.valueOf(algorithmType));
        FieldHelper.prepareLinks(field);
        List<Field> steps = alg.execute(field, debugEnabled, requireBestSolution);
        return steps;
    }

}
