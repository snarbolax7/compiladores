/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json.lexer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author ASUS
 */
public class JsonLexer {

    /**
     * @param args the command line arguments
     */
    static int lineNumber = 0;
    static String EOF = "eof";
    static int globalPosition = 0;
    static String output = "";

    public static void main(String[] args) {
        try {
            File myObj = new File(args[0]);
            try (Scanner myReader = new Scanner(myObj);
                    FileOutputStream outPutFile = new FileOutputStream("output.txt")) {
                while (myReader.hasNextLine()) {
                    lineNumber++;
                    String line = myReader.nextLine();
                    String[] lexemes = blanks(line).split("|");
                    try {
                        String token = matchToken(lexemes);
                        writeOutPut(outPutFile, token);
                    } catch (Exception ex) {
                        System.out.println("Error ocurrido en la línea: " + lineNumber + ", con mensaje: " + ex.getMessage());
                    }
                    globalPosition = 0;
                }
                outPutFile.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred: "+ e.getMessage());
        }
    }

    private static void writeOutPut(final FileOutputStream outPutFile, String token) throws IOException {
        output = output + token;
        System.out.println(output);
        byte[] data = output.getBytes("UTF-8");
        outPutFile.write(data);
        output = "";
    }

    private static String matchToken(String[] lexemes) throws Exception {
        String tokenLine = "";
        while (globalPosition < lexemes.length) {
            ValidTokens token = getToken(lexemes, globalPosition);
            if (token != null) {
                if (globalPosition != lexemes.length) {
                    tokenLine = tokenLine + token.toString() + " ";
                } else {
                    tokenLine = tokenLine + token.toString() + "\n";
                }
            }
        }
        output = output.concat("\n");
        return tokenLine;
    }

    private static ValidTokens getToken(String[] lexemes, int position) throws Exception {
        switch (lexemes[position]) {
            case ("["):
                if (isTerminal(lexemes, position, ValidTokens.L_CORCHETE)) {
                    return ValidTokens.L_CORCHETE;
                } else {
                    throw new Exception("Error, token esperado: " + ValidTokens.L_CORCHETE.getValue() + "en la posición:" + position);
                }
            case ("]"):
                if (isTerminal(lexemes, position, ValidTokens.R_CORCHETE)) {
                    return ValidTokens.R_CORCHETE;
                } else {
                    throw new Exception("Error, token esperado: " + ValidTokens.R_CORCHETE.getValue() + "en la posición:" + position);
                }
            case ("{"):
                if (isTerminal(lexemes, position, ValidTokens.L_LLAVE)) {
                    return ValidTokens.L_LLAVE;
                } else {
                    throw new Exception("Error, token esperado: " + ValidTokens.L_LLAVE.getValue() + "en la posición:" + position);
                }
            case ("}"):
                if (isTerminal(lexemes, position, ValidTokens.R_LLAVE)) {
                    return ValidTokens.R_LLAVE;
                } else {
                    throw new Exception("Error, token esperado: " + ValidTokens.R_LLAVE.getValue() + "en la posición:" + position);
                }
            case (","):
                if (isTerminal(lexemes, position, ValidTokens.COMA)) {
                    return ValidTokens.COMA;
                } else {
                    throw new Exception("Error, token esperado: " + ValidTokens.COMA.getValue() + "en la posición:" + position);
                }
            case (":"):
                if (isTerminal(lexemes, position, ValidTokens.DOS_PUNTOS)) {
                    return ValidTokens.DOS_PUNTOS;
                } else {
                    throw new Exception("Error, token esperado: " + ValidTokens.DOS_PUNTOS.getValue() + "en la posición:" + position);
                }
            case ("T"):
            case ("t"):
                if (isTerminal(lexemes, position, ValidTokens.PR_TRUE)) {
                    return ValidTokens.PR_TRUE;
                } else {
                    throw new Exception("Error, token esperado: " + ValidTokens.PR_TRUE.getValue() + "en la posición:" + position);
                }
            case ("F"):
            case ("f"):
                if (isTerminal(lexemes, position, ValidTokens.PR_FALSE)) {
                    return ValidTokens.PR_FALSE;
                } else {
                    throw new Exception("Error, token esperado: " + ValidTokens.PR_FALSE.getValue() + "en la posición:" + position);
                }
            case ("N"):
            case ("n"):
                if (isTerminal(lexemes, position, ValidTokens.PR_NULL)) {
                    return ValidTokens.PR_NULL;
                } else {
                    throw new Exception("Error, token esperado: " + ValidTokens.PR_NULL.getValue() + "en la posición:" + position);
                }
            case ("EOF"):
                if (isTerminal(lexemes, position, ValidTokens.EOF)) {
                    return ValidTokens.EOF;
                } else {
                    throw new Exception("Error, token esperado: " + ValidTokens.EOF.getValue() + "en la posición:" + position);
                }
            case ("\""):
                if (isString(lexemes, position)) {
                    return ValidTokens.LITERAL_CADENA;
                } else {
                    throw new Exception("Error, token esperado: " + ValidTokens.LITERAL_CADENA.getValue() + "en la posición:" + position);
                }
            case (""):
                globalPosition++;
                return null;
            default:
                if (isNumber(lexemes, position)) {
                    return ValidTokens.LITERAL_NUM;
                } else {
                    throw new Exception("Error, token esperado: " + ValidTokens.LITERAL_NUM.getValue() + "en la posición:" + position);
                }

        }
    }

    private static boolean isTerminal(String[] lexemes, int position, ValidTokens token) {
        String tokenToCompare = "";
        if (token.getValue().contains("|")) {
            for (int i = 0; i < token.getValue().indexOf("|"); i++) {
                if (lexemes[position].equals(" ") || Pattern.matches(token.getValue(), tokenToCompare)) {
                    break;
                }
                tokenToCompare = tokenToCompare + lexemes[position];
                position++;
            }
            if (Pattern.matches(token.getValue(), tokenToCompare.length() != 0 ? tokenToCompare : lexemes[position])) {
                globalPosition = position;
                return true;
            }
        }
        if (Pattern.matches(token.getValue(), tokenToCompare.length() != 0 ? tokenToCompare : lexemes[position])) {
            position++;
            globalPosition = position;
            return true;
        }

        return false;
    }

    private static String blanks(String nextLine) {
        return nextLine.replace(" ", "").replace("\n", "").replace("\t", "");
    }

    private static boolean isString(String[] lexemes, int position) {
        String tokenToCompare = "";
        for (int i = 0; i < lastIndexOfLine(lexemes); i++) {
            tokenToCompare = tokenToCompare + lexemes[position];
            position++;
            if (Pattern.matches(ValidTokens.LITERAL_CADENA.getValue(), tokenToCompare) || endOfLine(position, lexemes)) {
                break;
            }
        }
        if (Pattern.matches(ValidTokens.LITERAL_CADENA.getValue(), tokenToCompare.length() != 0 ? tokenToCompare : lexemes[position])) {
            globalPosition = position;
            return true;
        }
        return false;
    }

    private static boolean isNumber(String[] lexemes, int position) {
        String tokenToCompare = "";
        for (int i = 0; i < lastIndexOfLine(lexemes); i++) {
            tokenToCompare = tokenToCompare + lexemes[position];
            position++;
            if (!Pattern.matches(ValidTokens.LITERAL_NUM.getValue(), tokenToCompare) || endOfLine(position, lexemes)) {
                tokenToCompare = tokenToCompare.replace(lexemes[position - 1], "");
                break;
            }
        }
        if (Pattern.matches(ValidTokens.LITERAL_NUM.getValue(), tokenToCompare.length() != 0 ? tokenToCompare : lexemes[position])) {
            globalPosition = position;
            return true;
        }
        return false;
    }

    private static int lastIndexOfLine(String[] lexemes) {
        return lexemes.length - 1;
    }

    private static boolean endOfLine(int position, String[] lexemes) {
        return position > lastIndexOfLine(lexemes);
    }
}
