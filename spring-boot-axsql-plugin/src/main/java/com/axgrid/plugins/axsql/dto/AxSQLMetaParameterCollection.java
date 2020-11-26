package com.axgrid.plugins.axsql.dto;
import com.axgrid.jdbc.axsql.AxSQLGrammarParser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AxSQLMetaParameterCollection extends ArrayList<AxSQLParameterDescription> {

    public static AxSQLMetaParameterCollection parse(List<AxSQLGrammarParser.ParamContext> ctx) {
        AxSQLMetaParameterCollection res = new AxSQLMetaParameterCollection();
        for(int i=0;i<ctx.size();i++)
            res.add(AxSQLParameterDescription.parse(i, ctx.get(i)));
        return res;
    }

}
