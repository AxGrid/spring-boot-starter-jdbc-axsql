package com.axgrid.plugin.axsql;

import com.axgrid.jdbc.axsql.AxSQLGrammarLexer;
import com.axgrid.jdbc.axsql.AxSQLGrammarParser;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AxSQLGrammarTest {

    public static String resourceContent(String name) throws URISyntaxException, IOException {
        var path = Paths.get(AxSQLGrammarTest.class.getResource("/" + name).toURI());
        return new String(Files.readAllBytes(path));
    }

    @Test
    public void testGrammar() throws Exception {
        String text = resourceContent("test-table.sql");
        text = text.trim() + "\n";

        //log.debug("Text:{}", text);

        var lexer = new AxSQLGrammarLexer(CharStreams.fromString(text));
        var parser = new AxSQLGrammarParser(new CommonTokenStream(lexer));
        List<AxSQLGrammarParser.QueryContext> q = new ArrayList<>(parser.queries().query());
        log.info("Queries Count:{}",  q.size());
        String sql = q.stream().map(item ->
                item.statement().line().stream().map(line -> line.sql().getText().trim()).collect(Collectors.joining(" "))
        ).collect(Collectors.joining("\n ---- LINE ---- \n"));
        log.info("sql:{}",sql);
    }

}
