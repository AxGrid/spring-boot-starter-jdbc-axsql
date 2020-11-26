package com.axgrid.plugin.axsql;

import com.axgrid.plugins.axsql.dto.AxSQLFileDescription;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class AxSQLPluginTest {

    @Test
    public void testOne() throws Exception {
        String content = AxSQLGrammarTest.resourceContent("test-one.sql");
        var description = AxSQLFileDescription.parse(content);
        Assert.assertNotNull(description);
        Assert.assertEquals(description.getCommands().size(), 1);
        var command = description.getCommands().get(0);
        Assert.assertEquals(command.getName(), "updateNamed");
        Assert.assertEquals(command.getExecution(), "!");
        Assert.assertEquals(command.getParameters().size(), 4);
        Assert.assertEquals(command.getMapper().getMapperClass(), "com.axgrid.MyMapper");
        Assert.assertEquals(command.getParameters().get(0).getIndex(), 0);
        Assert.assertEquals(command.getParameters().get(0).getName(), "id");

        Assert.assertEquals(command.getParameters().get(1).getIndex(), 1);
        Assert.assertEquals(command.getParameters().get(1).getName(), "name");
        Assert.assertEquals(command.getParameters().get(1).getType(), "String");
        Assert.assertNull(command.getParameters().get(1).getCast());


        Assert.assertEquals(command.getParameters().get(2).getIndex(), 2);
        Assert.assertEquals(command.getParameters().get(2).getName(), "k");
        Assert.assertEquals(command.getParameters().get(2).getType(), "java.utils.Integer");
        Assert.assertNull(command.getParameters().get(2).getCast());

        Assert.assertEquals(command.getParameters().get(3).getIndex(), 3);
        Assert.assertEquals(command.getParameters().get(3).getName(), "enum");
        Assert.assertEquals(command.getParameters().get(3).getType(), "AxTestEnum");
        Assert.assertNotNull(command.getParameters().get(3).getCast());
        Assert.assertEquals(command.getParameters().get(3).getCast(), ".getInteger()");

        Assert.assertEquals(command.getSql().toLowerCase(), "update test set name=?, k=?, enum=? where id=?;");
    }
}
