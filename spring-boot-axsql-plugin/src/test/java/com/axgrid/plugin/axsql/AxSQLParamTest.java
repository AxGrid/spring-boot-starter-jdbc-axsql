package com.axgrid.plugin.axsql;

import com.axgrid.plugins.axsql.ParserUtils;
import org.junit.Assert;
import org.junit.Test;

public class AxSQLParamTest {

    @Test
    public void sqlParamTest() {
        var params = ParserUtils.search("SELECT * from user_name WHERE id=? AND sub='?' AND sub_name=':not' AND name=:name AND type = ? AND test in ?");
        Assert.assertEquals(params.size(), 4);

    }

}
