/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json.lexer;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 *
 * @author ASUS
 */
public enum ValidTokens implements Serializable {
    L_CORCHETE("\\["),
    R_CORCHETE("\\]"),
    L_LLAVE("\\{"),
    R_LLAVE("\\}"),
    COMA(","),
    DOS_PUNTOS(":"),
    LITERAL_CADENA("\".*\""),
    LITERAL_NUM("[0-9]+(\\.([0-9])+)?((e|E)(\\+|\\-)?[0-9]+)?"),
    PR_TRUE("true|TRUE"),
    PR_FALSE("false|FALSE"),
    PR_NULL("null|NULL"),
    EOF("match con el fin de archivo");

    private final String value;

    public String getValue() {
        return this.value;
    }

    private ValidTokens(String value) {
        this.value = value;
    }

    public static void main(String[] args) {
        String a = "{";
        String[] lexeme = a.split("|");
        int position = 0;
        String tokenToCompare = "";

        for (int i = 0; i < ValidTokens.DOS_PUNTOS.getValue().indexOf("|"); i++) {
            tokenToCompare = tokenToCompare + lexeme[position];
            position++;
        }
        System.out.println(Pattern.matches("\\{", "{"));
        System.out.println(Pattern.matches(ValidTokens.L_LLAVE.getValue(), tokenToCompare.length() != 0 ? tokenToCompare : lexeme[position]));
    }

}
