package com.axgrid.plugins.axsql.dto;

import com.axgrid.jdbc.axsql.AxSQLGrammarLexer;
import com.axgrid.jdbc.axsql.AxSQLGrammarParser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AxSQLFileDescription {
    String name;
    String className;
    String packageName;

    List<AxSQLCommandDescription> commands;

    public static AxSQLFileDescription parse(String content) {
        var lexer = new AxSQLGrammarLexer(CharStreams.fromString(content.trim()+"\n"));
        var parser = new AxSQLGrammarParser(new CommonTokenStream(lexer));
        List<AxSQLGrammarParser.QueryContext> commands = new ArrayList<>(parser.queries().query());
        AxSQLFileDescription res = new AxSQLFileDescription();
        res.setCommands(commands.stream().map(AxSQLCommandDescription::parse).collect(Collectors.toList()));
        if (res.commands.stream().anyMatch(item -> item.getName() == null || item.getName().isBlank())) {

        }
        return res;
    }

}
