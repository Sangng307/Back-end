package com.poly.demo.reqRespModels;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyRevenueResponse {
    private List<DailyRevenueData> data;
    private int trend;
    private double total;
}
