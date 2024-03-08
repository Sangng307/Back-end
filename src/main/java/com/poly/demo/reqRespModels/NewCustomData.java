package com.poly.demo.reqRespModels;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewCustomData {
    private Date date;
    private long value;

}
