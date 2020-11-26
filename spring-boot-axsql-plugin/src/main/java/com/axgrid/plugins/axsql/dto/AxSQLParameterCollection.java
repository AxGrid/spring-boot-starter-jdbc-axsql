package com.axgrid.plugins.axsql.dto;

import com.axgrid.plugins.axsql.ParserUtils;

import java.util.*;
import java.util.stream.Collectors;

public class AxSQLParameterCollection extends ArrayList<AxSQLParameter> {

    final Map<String, AxSQLParameter> metaParametersMap;
    final List<AxSQLParameter> metaParameters;

    public void addNamed(int index, String name) {
        if (metaParametersMap.containsKey(name))
            this.add(metaParametersMap.get(name).clone(index));
        else
            this.add(new AxSQLParameter(index, "Object", name, null, true));
    }

    public void addUnNamed(int index) {
        if (index < metaParameters.size()) {
            this.add(metaParameters.get(index).clone(index));
        } else {
            this.add(new AxSQLParameter(index, "Object", null, null, false));
        }
    }

    public AxSQLParameterCollection(Collection<AxSQLParameter> collection) {
        this.metaParameters = new ArrayList<>(collection);
        this.metaParametersMap = collection.stream().filter(AxSQLParameter::isNamed).collect(Collectors.toMap(AxSQLParameter::getName, item -> item));
    }

    public AxSQLParameterCollection() {
        this(Collections.emptyList());
    }

    enum State {
        QUERY,
        STRING_SINGLE_QUOTE,
        STRING_DOUBLE_QUOTE,
        PARAMETER
    }

    public void create(String query) {
        State state = State.QUERY;
        int paramNameStartedAt = -1;
        StringBuilder namedParam = new StringBuilder();
        int index = 0;
        for (int i = 0; i < query.length(); i++) {
            char c = query.charAt(i);
            switch (state) {
                case QUERY:
                    switch (c) {
                        default:
                            break;
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
                            addUnNamed(index++);
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
                        ParserUtils.SQLParam.create(namedParam.toString(), paramNameStartedAt);
                        namedParam = new StringBuilder();
                        paramNameStartedAt = -1;
                        state = State.QUERY;
                    }
                    break;
            }
        }

    }

}
