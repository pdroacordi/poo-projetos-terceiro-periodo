package data.usecasesimplementation;

import domain.usecases.IRomanNumeralConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class RomanNumeralConverter implements IRomanNumeralConverter {

    @Override
    public String toRoman(int number) {
        if( number < 1 || number > 3999 ) return "Número deve estar entre 1 e 3999.";

        Map<String, Integer> valuesMap = new LinkedHashMap<>();
        valuesMap.put("M", 1000);
        valuesMap.put("CM", 900);
        valuesMap.put("D", 500);
        valuesMap.put("CD", 400);
        valuesMap.put("C", 100);
        valuesMap.put("XC", 90);
        valuesMap.put("L", 50);
        valuesMap.put("XL", 40);
        valuesMap.put("X", 10);
        valuesMap.put("IX", 9);
        valuesMap.put("V", 5);
        valuesMap.put("IV", 4);
        valuesMap.put("I", 1);

        StringBuilder romanNumeral = new StringBuilder();

        while(number > 0) {
            for( Map.Entry<String, Integer> entry : valuesMap.entrySet()) {
                if( number - entry.getValue() >= 0 ) {
                    romanNumeral.append(entry.getKey());
                    number -= entry.getValue();
                    break;
                }
            }
        }
        return romanNumeral.toString();
    }
}
