package com.one.smartexchanges.models;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

public class Consult {
    private final String result;
    private final String codeBase;
    private final String codeTarget;
    private final double valueBase;
    private final double valueTarget;
    private final String consultDateTime;

    public Consult(Exchange exchange) {
        this.result = exchange.result();
        this.codeBase = exchange.base_code();
        this.codeTarget = exchange.target_code();
        this.valueBase = exchange.conversion_result()/exchange.conversion_rate();
        this.valueTarget = exchange.conversion_result();
        this.consultDateTime = String.valueOf(LocalDateTime.now());
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if (result.equals("success")) {
            return "Result: " + result + "\n" +
                    "Value to convert: " + decimalFormat.format(valueBase) + codeBase + "\n" +
                    "Value converted " + decimalFormat.format(valueTarget) + codeTarget + "\n" +
                    "Consult date: " + consultDateTime + "\n";
        }else {
            return "Result: " + result + "\n" +
                    "Code currency not found" + "\n" +
                    "Consult date: " + consultDateTime;
        }
    }
}
