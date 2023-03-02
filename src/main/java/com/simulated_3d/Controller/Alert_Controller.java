package com.simulated_3d.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simulated_3d.DTO.Alert_Dto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Map;

@Controller
@Slf4j
public class Alert_Controller {

    protected String Alert_And_Redirect(final Alert_Dto alert_dto, Model model)
    {

        model.addAttribute("alert_dto",alert_dto);

        return "Common/Alert_And_Redirect";
    }

    protected Map<String,Object> Convert_Map(Object object)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = objectMapper.convertValue(object,Map.class);

        return map;
    }
}
