package br.com.ykronnos.testesunitarioscurso.utils;

public class UtilsMethods {

    public Double convertToDouble(String number) {
        if(number == null) return 0D;

        String numberReplace = number.replaceAll(",", ".");

        if(isNumberic(numberReplace)) return Double.parseDouble(number);

        return 0D;
    }

    public boolean isNumberic(String b) {
        if(b == null) return false;

        String numberReplace = b.replaceAll(",", ".");

        return numberReplace.matches("[0-9]+[\\.]?[0-9]*");
    }

}
