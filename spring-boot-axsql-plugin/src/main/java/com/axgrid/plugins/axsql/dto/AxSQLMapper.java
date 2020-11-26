package com.axgrid.plugins.axsql.dto;

import com.axgrid.jdbc.axsql.AxSQLGrammarParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AxSQLMapper {
    String mapperClass;

    public static AxSQLMapper parse(AxSQLGrammarParser.MapperContext ctx) {
        return new AxSQLMapper(ctx.path().getText());
    }
}
