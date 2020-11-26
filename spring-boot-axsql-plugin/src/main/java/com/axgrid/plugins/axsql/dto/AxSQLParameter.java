package com.axgrid.plugins.axsql.dto;

import com.axgrid.jdbc.axsql.AxSQLGrammarParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AxSQLParameter {
    int index;
    String type;
    String name;
    String cast;

    public static AxSQLParameter parse(int index, AxSQLGrammarParser.ParamContext ctx) {
        return new AxSQLParameter(
                index,
                ctx.path().getText(),
                ctx.variable() != null ? ctx.variable().getText() : String.format("param%d", index),
                ctx.cast() != null ? ctx.cast().getText() : null
        );
    }

}
