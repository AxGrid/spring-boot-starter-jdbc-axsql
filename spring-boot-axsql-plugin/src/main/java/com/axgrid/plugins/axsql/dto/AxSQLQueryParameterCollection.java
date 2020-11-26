package com.axgrid.plugins.axsql.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
public class AxSQLQueryParameterCollection extends ArrayList<AxSQLParameterDescription> {


    private enum State {
        QUERY,
        STRING_SINGLE_QUOTE,
        STRING_DOUBLE_QUOTE,
        PARAMETER
    }

    String query;

    public String cleanupQuery() {
        StringBuilder res = new StringBuilder(query);
        int offset = 0;
        for (var param : this) {
            if (param.isNamed()) {
                res.replace(param.getStartPosition(offset), param.getEndPosition(offset), "?");
                offset += param.getName().length();
            }
        }
        return res.toString();
    }


    public static AxSQLQueryParameterCollection parse(String query) {
        AxSQLQueryParameterCollection res = new AxSQLQueryParameterCollection();
        res.setQuery(query);
        State state = State.QUERY;
        int index = 0;
        int paramNameStartedAt = -1;
        StringBuilder namedParam = new StringBuilder();
        for (int i = 0; i < query.length(); i++) {
            char c = query.charAt(i);
            switch (state) {
                case QUERY:
                    switch (c) {
                        case '\'':
                            state = State.STRING_SINGLE_QUOTE;
                            break;
                        case '\"':
                            state = State.STRING_DOUBLE_QUOTE;
                            break;
                        case ':':
                            state = State.PARAMETER;
                            paramNameStartedAt = i;
                            namedParam = new StringBuilder();
                            break;
                        case '?':
                            //TODO: Вносим параметр
                            res.add(AxSQLParameterDescription.createUnnamed(index++, i));
                            break;
                        default:
                            break;
                    }
                    break;
                case STRING_SINGLE_QUOTE:
                    if (c == '\'') state = State.QUERY;
                    break;
                case STRING_DOUBLE_QUOTE:
                    if (c == '\"') state = State.QUERY;
                    break;
                case PARAMETER:
                    if (Character.isAlphabetic(c) || Character.isDigit(c) || c == '_')
                        namedParam.append(c);
                    else {
                        res.add(AxSQLParameterDescription.createNamed(index++, namedParam.toString(), paramNameStartedAt, i));
                        namedParam = new StringBuilder();
                        paramNameStartedAt = -1;
                        state = State.QUERY;
                    }
                    break;
            }
        }
        if (state == State.PARAMETER) { // Последний параметр
            res.add(AxSQLParameterDescription.createNamed(index, namedParam.toString(), paramNameStartedAt, query.length()));
        }
        return res;
    }
}
