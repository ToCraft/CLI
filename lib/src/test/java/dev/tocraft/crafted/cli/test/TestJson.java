package dev.tocraft.crafted.cli.test;

import dev.tocraft.crafted.cli.json.JsonParseException;
import dev.tocraft.crafted.cli.json.JsonParser;
import org.junit.Assert;
import org.junit.Test;

public class TestJson {
    @Test
    public void testPrimary() {
        Assert.assertTrue(JsonParser.parseJson("true").asBool().get());
        Assert.assertFalse(JsonParser.parseJson("false").asBool().get());
        Assert.assertEquals(5, JsonParser.parseJson("5").asInt().get());
        Assert.assertEquals(2.2D, JsonParser.parseJson("2.2").asDouble().get(), 0);
        Assert.assertEquals(5D, JsonParser.parseJson("5").asDouble().get(), 0);
        Assert.assertEquals("hallo", JsonParser.parseJson("\"hallo\"").asString().get());
        Assert.assertThrows(JsonParseException.class, () -> JsonParser.parseJson("hallo").asString().get());
    }

    @Test
    public void testAdvanced() {
        String obj = "[1,2,3,4,5]";
        Assert.assertEquals(obj, JsonParser.parseJson(obj).toJson());
        obj = "[\"a\",false,4,2.3]";
        Assert.assertEquals("[\"a\",2.3,4,false]", JsonParser.parseJson(obj).toJson());
        obj = "{\"key\":\"value\",\"other\":3}";
        Assert.assertEquals("{\n" +
                "    \"key\": \"value\",\n" +
                "    \"other\": 3\n" +
                "}", JsonParser.parseJson(obj).asObject().toPrettyJson());
        obj = "[\"test\",{\"key\":\"value\",\"other\":3}]";
        Assert.assertEquals(obj, JsonParser.parseJson(obj).toJson());
    }
}
