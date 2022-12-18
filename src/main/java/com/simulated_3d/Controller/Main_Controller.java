package com.simulated_3d.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Main_Controller {

    @GetMapping(value = "/")
    public String Main()
    {
        return "Main";
    }


}
