package com.be.bedemo.mapper.qualifier;

import org.mapstruct.Named;

@Named("CalculateAnnualGrossQualifier")
public class CalculateAnnualGrossQualifier {

    @Named("CalculateAnnualGross")
    public static int calculateAnnualGross(int averageGross){
        return ((averageGross * 12) * 120) / 100;
    }
}
