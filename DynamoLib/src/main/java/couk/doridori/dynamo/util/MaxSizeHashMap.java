package couk.doridori.dynamo.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Map impl that will limit the size of the map and work on a last-used-first-out-basis
 */
public class MaxSizeHashMap<K, V> extends LinkedHashMap<K, V>
{
    private final int mMaxSize;

    public MaxSizeHashMap(int maxSize) {
        super(
                maxSize,
                maxSize, //load factor is ignored in the Map impl anyhow
                true); //access should rearrange the underlying linkedHashMap so last used gets kicked out first

        mMaxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > mMaxSize;
    }
}