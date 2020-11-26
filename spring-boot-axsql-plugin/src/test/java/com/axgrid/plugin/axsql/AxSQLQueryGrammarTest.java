package com.axgrid.plugin.axsql;

import com.axgrid.jdbc.axsql.AxSQLQueryGrammarLexer;
import com.axgrid.jdbc.axsql.AxSQLQueryGrammarParser;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
public class AxSQLQueryGrammarTest {



    @Test
    public void testQuery() {
        String query = "SELECT * from user_name WHERE id=? AND sub='?' AND sub_name=':not' AND name=:name AND type = ? AND test in ?";

        var lexer = new AxSQLQueryGrammarLexer(CharStreams.fromString(query));
        var parser = new AxSQLQueryGrammarParser(new CommonTokenStream(lexer));

        var parts = new ArrayList<>(parser.sql().part());
        var params = parts.stream().filter(item -> item.param() != null).collect(Collectors.toList());

        log.info("Parts: {} Params:{}", parts.size(), params.size());
        log.info("Parts Index: {}", params.get(0).param().start.getStartIndex());
        Assert.assertEquals(params.size(), 4);
    }
}
