package com.axgrid.plugins.axsql.dto;

import com.axgrid.jdbc.axsql.AxSQLGrammarParser;
import com.axgrid.plugins.axsql.ParserUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AxSQLCommandDescription {

    String name;
    String execution;
    String sql;
    List<AxSQLParameter> parameters;
    AxSQLMapper mapper;
    AxSQLObject object;

    public static AxSQLCommandDescription parse(AxSQLGrammarParser.QueryContext ctx) {
        AxSQLCommandDescription res = new AxSQLCommandDescription();

        if (ctx.name().execute_name() != null) {
            res.setName(ParserUtils.getName(ctx.name().execute_name()));
            res.setExecution(ParserUtils.getExecution(ctx.name().execute_name()));
        }

        res.setSql(ParserUtils.getFlatSQL(ctx.statement()));
        res.setObject(ctx.object().stream().map(AxSQLObject::parse).findFirst().orElse(null));
        res.setMapper(ctx.mapper().stream().map(AxSQLMapper::parse).findFirst().orElse(null));
        List<AxSQLParameter> parameters = new ArrayList<>();
        for(int i=0;i<ctx.param().size();i++)
            parameters.add(AxSQLParameter.parse(i, ctx.param(i)));
        res.setParameters(parameters);

        return res;
    }

}
