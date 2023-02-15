package com.simulated_3d.Test;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.simulated_3d.Entity.Product.QItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
@TestPropertySource(locations = "classpath:application-test.properties")
public class TEST_Java {


/*
    TODO 테스트
*/
    @Test
    @DisplayName("NPE 테스트")
    public void NPE_Test() throws Exception {

        String reg_time = "1d";

        Reg_Time_After("1d");
    }


/*
    TODO 메서드
*/
    BooleanExpression Reg_Time_After(String reg_time)
    {
        LocalDateTime date_time = LocalDateTime.now();


        if(StringUtils.equals("all",reg_time))
        {
            log.debug("all 체크");
            return  (BooleanExpression) null;
        }

        int num = Integer.parseInt(reg_time.substring(0,reg_time.length()-1));
        char type = reg_time.charAt(reg_time.length()-1);

        switch (type)
        {
            case 'd':
                date_time = date_time.minusDays(num);
                break;
            case 'w':
                date_time =date_time.minusWeeks(num);
                break;
            case 'm':
                date_time = date_time.minusMonths(num);
                break;
            case 'y':
                date_time = date_time.minusYears(num);
                break;
        }

        log.debug("시간 체크 " + date_time);
        return QItem.item.reg_time.after(date_time);
    }
}
