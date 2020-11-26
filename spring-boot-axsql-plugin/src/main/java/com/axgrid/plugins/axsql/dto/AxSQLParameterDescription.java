package com.axgrid.plugins.axsql.dto;

import com.axgrid.jdbc.axsql.AxSQLGrammarParser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AxSQLParameterDescription {
    int index;
    String type;
    String name;
    String cast;
    int startPosition = -1;
    int endPosition = -1;

    public static AxSQLParameterDescription createUnnamed(int index, int startPosition) {
        var res = new AxSQLParameterDescription();
        res.setIndex(index);
        res.setStartPosition(startPosition);
        res.setEndPosition(startPosition+1);
        return res;
    }

    public static AxSQLParameterDescription createNamed(int index, String name, int startPosition, int endPosition) {
        var res = new AxSQLParameterDescription();
        res.setIndex(index);
        res.setStartPosition(startPosition);
        res.setEndPosition(endPosition);
        res.setName(name);
        return res;
    }

    public AxSQLParameterDescription(int index,  String type, String name, String cast) {
        this.index = index;
        this.type = type;
        this.name = name;
        this.cast =cast;
    }

    public String getName() { return isNamed() ? name : String.format("param%d", index); }
    public boolean isNamed() { return name != null && !name.equals(""); }

    public int getStartPosition(int offset) {
        return startPosition - offset;
    }

    public int getEndPosition(int offset) {
        return endPosition - offset;
    }


    public static AxSQLParameterDescription parse(int index, AxSQLGrammarParser.ParamContext ctx) {
        return new AxSQLParameterDescription(
                index,
                ctx.path() != null ? ctx.path().getText() : null,
                ctx.variable() != null ? ctx.variable().getText() : null,
                ctx.cast() != null ? ctx.cast().getText() : null
        );
    }
}
