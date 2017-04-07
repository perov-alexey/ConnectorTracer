package ru.rsreu.controllers;

import org.springframework.web.bind.annotation.*;
import ru.rsreu.factory.AlgorithmFactory;
import ru.rsreu.factory.AlgorithmTypes;
import ru.rsreu.tracer.algorithms.Algorithm;
import ru.rsreu.tracer.helpers.FieldHelper;
import ru.rsreu.tracer.pojo.Field;
import ru.rsreu.tracer.utils.FieldGenerator;

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

    /**
     * Generate field
     * @param connectorsAmount Amount of connectors in generated field
     * @param linksAmount Amount of links in generated field
     * @param pinsAmount Amount of pins in each connector
     * @param columnPinsAmount Amount of pin's columns in each connector
     * @param minChannelCapacity Minimum capacity of the channels
     * @param maxChannelCapacity Maximum capacity of the channels
     * @return Generated field
     */
    @RequestMapping(value = "/field", method = RequestMethod.GET)
    private @ResponseBody Field generateField(@RequestParam int connectorsAmount,
                                              @RequestParam int linksAmount,
                                              @RequestParam int pinsAmount,
                                              @RequestParam int columnPinsAmount,
                                              @RequestParam int minChannelCapacity,
                                              @RequestParam int maxChannelCapacity) {
        return new FieldGenerator().generateField(connectorsAmount, pinsAmount, columnPinsAmount, linksAmount,
                minChannelCapacity, maxChannelCapacity);
    }

}
