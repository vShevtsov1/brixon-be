package com.example.demo.projects.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class paymentsPlan {
    private String blockName;
    private String percent1;
    private String sum1;

    private String percent2;
    private String sum2;


    private String percent3;
    private String sum3;

    private String percent4;
    private String sum4;
}
