package com.poly.demo.reqRespModels;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyRevenueData {
    private Date date;
    private double value;
}
