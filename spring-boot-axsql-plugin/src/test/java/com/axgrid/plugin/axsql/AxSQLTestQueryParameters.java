package com.axgrid.plugin.axsql;

import com.axgrid.plugins.axsql.dto.AxSQLParameterCollection;
import com.axgrid.plugins.axsql.dto.AxSQLQueryParameterCollection;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class AxSQLTestQueryParameters {

    @Test
    public void testOrderQueries() throws Exception {

        String line = "INSERT INTO test (name, id, k, s1, s2, v) VALUES (?, :id, ?, 'no?', ':or_yes', :v);";
        AxSQLQueryParameterCollection res = AxSQLQueryParameterCollection.parse(line);
        Assert.assertEquals(res.size(), 4);
        Assert.assertEquals(res.get(0).getIndex(), 0);
        Assert.assertEquals(res.get(1).getIndex(), 1);

        Assert.assertEquals(res.get(0).isNamed(), false);
        Assert.assertEquals(res.get(1).isNamed(), true);
        Assert.assertEquals(res.get(1).getName(), "id");
        Assert.assertEquals(res.get(2).isNamed(), false);
        Assert.assertEquals(res.get(3).isNamed(), true);
        Assert.assertEquals(res.get(3).getName(), "v");

        String p = line.substring(res.get(1).getStartPosition(), res.get(1).getEndPosition());
        log.info("P is [{}]", p);
        Assert.assertEquals(p, ":id");
        p = line.substring(res.get(3).getStartPosition(), res.get(3).getEndPosition());
        log.info("P is [{}]", p);
        Assert.assertEquals(p, ":v");

        String cleanSql = res.cleanupQuery();
        log.info("Query is [{}]", cleanSql);

    }
}
