/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json.lexer;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public enum ValidSyntax implements Serializable {
    
//    JSON("element eof"),
//    ELEMENT("object|array"),
//    ARRAY("[element-list]|[]"),
//    ELEMENT_LIST("element element-list'"),
//    ELEMENT_LIST_PRIME(",element element-list'|ε"),
//    OBJECT("{attributes-list}|{}"),
//    ATTRIBUTES_LIST("attribute attribute-list'"),
//    ATTRIBUTES_LIST_PRIME(",attribute attribute-list'|ε"),
//    ATTRIBUTE(ValidTokens.LITERAL_CADENA.getValue()+" : "+"element|"
//            +ValidTokens.LITERAL_CADENA.getValue()+"|"
//            +ValidTokens.LITERAL_NUM.getValue()+"|"
//            +ValidTokens.PR_TRUE.getValue()+"|"
//            +ValidTokens.PR_FALSE.getValue()+"|"
//            +ValidTokens.PR_NULL.getValue()),
//    ATTRIBUTE_NAME(ValidTokens.LITERAL_CADENA.getValue()),
//    ATTRIBUTE_VALUE("element|"
//            +ValidTokens.LITERAL_CADENA.getValue()+"|"
//            +ValidTokens.LITERAL_NUM.getValue()+"|"
//            +ValidTokens.PR_TRUE.getValue()+"|"
//            +ValidTokens.PR_FALSE.getValue()+"|"
//            +ValidTokens.PR_NULL.getValue());
//    private final String value;
    
    JSON("element eof"),
    ELEMENT("object|array"),
    ARRAY("[element-list]|[]"),
    ELEMENT_LIST("element element-list'"),
    ELEMENT_LIST_PRIME(",element element-list'|ε"),
    OBJECT("{attributes-list}|{}"),
    ATTRIBUTES_LIST("attribute attribute-list'"),
    ATTRIBUTES_LIST_PRIME(",attribute attribute-list'|ε"),
    ATTRIBUTE("attribute-name : attribute-value"),
    ATTRIBUTE_NAME(ValidTokens.LITERAL_CADENA.getValue()),
    ATTRIBUTE_VALUE("element|"
            +ValidTokens.LITERAL_CADENA.getValue()+"|"
            +ValidTokens.LITERAL_NUM.getValue()+"|"
            +ValidTokens.PR_TRUE.getValue()+"|"
            +ValidTokens.PR_FALSE.getValue()+"|"
            +ValidTokens.PR_NULL.getValue());
    private final String value;

    public String getValue() {
        return value;
    }

    private ValidSyntax(String value) {
        this.value = value;
    }
}
