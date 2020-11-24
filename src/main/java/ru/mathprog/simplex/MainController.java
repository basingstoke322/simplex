package ru.mathprog.simplex;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {
    Integer rawCount, partCount;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/matrix")
    public String matrix(@RequestParam Map<String,String> allRequestParams, Model model){
        InputData matrix = new InputData();
        rawCount = Integer.parseInt(allRequestParams.get("rawCount"));
        partCount = Integer.parseInt(allRequestParams.get("partCount"));
        matrix.setRows(rawCount);
        matrix.setCols(partCount);
        model.addAttribute("matrix", matrix);
        return "input_matrix";
    }

    @PostMapping("/answer")
    public String answer(@RequestParam Map<String,String> allRequestParams, @ModelAttribute("matrix") InputData matrix, Model model){
        matrix.setCols(partCount);
        matrix.setRows(rawCount);
        matrix.init();
        allRequestParams
                .entrySet()
                .stream()
                .filter((x) -> x.getKey().split("")[0].equals("x"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .forEach(matrix::setMatrixCell);
        allRequestParams
                .entrySet()
                .stream()
                .filter((x) -> x.getKey().split("")[0].equals("p"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .forEach(matrix::setPricesCell);
        allRequestParams
                .entrySet()
                .stream()
                .filter((x) -> x.getKey().split("")[0].equals("c"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .forEach(matrix::setCountsCell);
        model.addAttribute("simplex", new Simplex(matrix));
        return "answer";
    }
}
