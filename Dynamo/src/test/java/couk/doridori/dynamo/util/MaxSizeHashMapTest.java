package couk.doridori.dynamo.util;

import couk.doridori.dynamo.util.MaxSizeHashMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by dori on 30/03/2015.
 */
public class MaxSizeHashMapTest
{
    @Test
    public void shouldRemoveLastUsedWhenOverLimit_insertOrderOnly()
    {
        Map<String, String> map = new MaxSizeHashMap<String, String>(2);
        map.put("oneKey", "oneVal");
        map.put("twoKey", "twoVal");
        map.put("threeKey", "threeVal");

        Assert.assertFalse(map.containsKey("oneKey"));
        Assert.assertEquals(map.get("twoKey"), "twoVal");
        Assert.assertEquals(map.get("threeKey"), "threeVal");
    }

    @Test
    public void shouldRemoveLastUsedWhenOverLimit_accessOrder()
    {
        Map<String, String> map = new MaxSizeHashMap<String, String>(2);
        map.put("oneKey", "oneVal");
        map.put("twoKey", "twoVal");
        map.get("oneKey"); //getting should rearrange map order
        map.put("threeKey", "threeVal");


        Assert.assertFalse(map.containsKey("twoKey"));
        Assert.assertEquals(map.get("oneKey"), "oneVal");
        Assert.assertEquals(map.get("threeKey"), "threeVal");
    }
}
