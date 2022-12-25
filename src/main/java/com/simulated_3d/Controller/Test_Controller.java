package com.simulated_3d.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class Test_Controller {

    @PostMapping(value = "/test")
    public void Request_Parameter(@RequestParam("test") String param)
    {
        System.out.println(param);
    }
}
