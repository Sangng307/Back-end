package com.poly.demo.reqRespModels;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewCustomeDataResponse {
    private List<NewCustomData> data;
    private int trend;
    private long total;
}
