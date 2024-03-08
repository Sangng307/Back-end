package com.poly.demo.reqRespModels;

import lombok.*;

import java.util.List;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyOrderResponse {
    private List<DailyOrderData> data;
    private int trend;
    private long total;
}
