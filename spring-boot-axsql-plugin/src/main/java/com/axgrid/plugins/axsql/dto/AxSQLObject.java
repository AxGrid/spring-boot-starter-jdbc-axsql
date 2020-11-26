package com.axgrid.plugins.axsql.dto;

import com.axgrid.jdbc.axsql.AxSQLGrammarParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AxSQLObject {
    String objectClass;

    public static AxSQLObject parse(AxSQLGrammarParser.ObjectContext objectContext) {
        return new AxSQLObject(objectContext.path().toString());
    }
}
