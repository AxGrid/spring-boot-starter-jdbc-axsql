package com.axgrid.plugins.axsql;

import com.axgrid.jdbc.axsql.AxSQLGrammarParser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ParserUtils {

    public static String getName(AxSQLGrammarParser.Execute_nameContext ctx) {
        String execution = getExecution(ctx);
        if (execution == null) return ctx.getText().trim();
        return ctx.getText().trim().replace(execution, "");
    }

    public static String smartTrim(String text) {
        if(text == null || text.isBlank()) return text;
        while (text.contains("  "))
            text = text.replace("  ", " ");
        return text.trim();
    }

    public static String getExecution(AxSQLGrammarParser.Execute_nameContext ctx) {
        if (ctx.EXECUTE_NAME() == null) return null;
        String name = ctx.EXECUTE_NAME().getText().trim();
        if (name.endsWith("<!")) return "<!";
        if (name.endsWith("!")) return "!";
        return null;
    }

    public static String getFlatSQL(AxSQLGrammarParser.StatementContext statement) {
        return smartTrim(statement.line().stream().map(line -> line.sql().getText().trim()).collect(Collectors.joining(" ")));
    }

    private enum State {
        QUERY,
        STRING_SINGLE_QUOTE,
        STRING_DOUBLE_QUOTE,
        PARAMETER
    }

    @Data
    public static class SQLParam {

        private final String name;
        private final int startIndex;

        private SQLParam(String name, int startIndex) {
            this.name = name;
            this.startIndex = startIndex;
        }

        public SQLParam offsetLeft(int offset) {
            return new SQLParam(name, startIndex - offset);
        }

        public int getNameLength() { return name.length() + 1; }
        public int getEndIndex() { return startIndex + getNameLength(); }

        public boolean isNamed() {
            return !name.equals("?");
        }

        @Override
        public String toString() {
            return "SQLParam{" +
                    "name='" + name + '\'' +
                    ", startIndex=" + startIndex +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SQLParam sqlParam = (SQLParam) o;
            return startIndex == sqlParam.startIndex &&
                    Objects.equals(name, sqlParam.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, startIndex);
        }

        public static SQLParam create(String name, int startedA) {
            return new SQLParam(name, startedA);
        }
    }

    public static List<SQLParam> search(String query) {
        State state = State.QUERY;
        ArrayList<SQLParam> params = new ArrayList<>();
        StringBuilder namedParam = new StringBuilder();
        int paramNameStartedAt = -1;
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
                            params.add(SQLParam.create("?", i));
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
                        params.add(SQLParam.create(namedParam.toString(), paramNameStartedAt));
                        namedParam = new StringBuilder();
                        paramNameStartedAt = -1;
                        state = State.QUERY;
                    }
                    break;
            }
        }
        if (state == State.PARAMETER) {
            params.add(SQLParam.create(namedParam.toString(), paramNameStartedAt));
        }

        return params;
    }

}
