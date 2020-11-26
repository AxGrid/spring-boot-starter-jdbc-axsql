package com.axgrid.plugins.axsql;

import com.axgrid.jdbc.axsql.AxSQLGrammarParser;

public final class ParserUtils {

    public static String getName(AxSQLGrammarParser.Execute_nameContext ctx) {
        String execution = getExecution(ctx);
        if (execution == null) return ctx.getText().trim();
        return ctx.getText().trim().replace(execution, "");
    }

    public static String getExecution(AxSQLGrammarParser.Execute_nameContext ctx) {
        if (ctx.EXECUTE_NAME() == null) return null;
        String name = ctx.EXECUTE_NAME().getText().trim();
        if (name.endsWith("<!")) return "<!";
        if (name.endsWith("!")) return "!";
        return null;
    }





}
