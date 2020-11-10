package ru.mathprog.simplex;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model){
        Matrix matrix = new Matrix();
        model.addAttribute("matrix", matrix);
        return "index";
    }

    @PostMapping("/matrix")
    public String matrix(@ModelAttribute("matrix") Matrix matrix,
                         Model model){
        matrix.init();
        model.addAttribute("matrix", matrix);
        return "input_matrix";
    }

    @PostMapping("/answer")
    public String answer(@ModelAttribute("matrix") Matrix matrix){
//        allRequestParams.forEach(matrix::setCell);
        System.out.println(matrix.matrix);
        return "answer";
    }
}
