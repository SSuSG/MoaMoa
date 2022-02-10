package SG.MoaMoa.controller;

import SG.MoaMoa.domain.User;
import SG.MoaMoa.web.argumentresolver.Login;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(
            @Login User loginUser , Model model
    ){
        if(loginUser == null){
            return "loginHome";
        }

        return "loginHome";
    }

}
