/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json.lexer;

/**
 *
 * @author ASUS
 */
public class SyntaxData {
    private ValidTokens token;
    private Integer line;
    private Integer column;

    public SyntaxData(ValidTokens token, Integer line, Integer column) {
        this.token = token;
        this.line = line;
        this.column = column;
    }

    public ValidTokens getToken() {
        return token;
    }

    public void setToken(ValidTokens token) {
        this.token = token;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
    
}
