package couk.doridori.dynamo;

import couk.doridori.dynamo.util.MaxSizeHashMap;

import java.util.*;

/**
 * Holds a ref to a n Dynamos. Subclass one of these for each Dynamo subclass type in your app.
 *
 * This generic DynamoHolder has a notion of Dynamo `meta` data. This is used to hook up view
 * elements (Activities, Fragments and View) to Dynamo instances. This is what allows reconnection
 * of view instances to existing Dynamos.
 *
 * This can be of the following type:
 *
 * - Fixed Name: This works well for a View that will show the same data regardless of the View
 *   instance i.e. an app wide notepad. The fixed name could be the class name or some other constant.
 *
 * - Variable Name - Input data: You may have a View element that takes some input data (say a data source URL or
 *   arguments) via the Intent Bundle, which is used to curate the data obtained via the controller
 *   instance. You could then use this as the meta data. This would mean View instances that show
 *   the same data would hook up to existing Dynamos that may already have loaded the data etc.
 *
 * - Variable Name - UUID: You may also want to pass a UUID or incrementing reference through here,
 *   making sure to save and reload this with the savedInstanceState (or persistence Bundle if using a View)
 *
 * This class also has a notion of size. The Dynamos are held in a Least Recently Used (LRU) queue of the passed-in size.
 *
 * Alternatively you may want to improve this class by adding some form of WeakReferencing and
 * caching as many Dynamo instances as possible OR introduce your own method of calculating size
 * constraints.
 *
 * @param <T> Dynamo class
 */
public class DynamoHolder<T>
{
    /**
     * Map for quick access to Dynamo indexed by meta
     */
    private MaxSizeHashMap<String, T> mDynamoMap;

    /**
     * @param maxSize 1 or more
     */
    public DynamoHolder(int maxSize)
    {
        mDynamoMap = new MaxSizeHashMap<>(maxSize);
    }

    /**
     * @param meta See the doc for this class {@link couk.doridori.dynamo.DynamoHolder}
     * @param dynamoFactory Factory that defines how to build a Dynamo
     * @return an existing Dynamo associated with meta, or a new one if no association exists
     */
    public T getDynamo(String meta, DynamoFactory<T> dynamoFactory)
    {
        if(!mDynamoMap.containsKey(meta))
        {
            T dynamo = dynamoFactory.buildDynamo();
            mDynamoMap.put(meta, dynamo);

            return dynamo;
        }
        else
        {
            return mDynamoMap.get(meta);
        }
    }

    public void clearAll()
    {
        mDynamoMap.clear();
    }

    public void clear(String meta)
    {
        mDynamoMap.remove(meta);
    }

    public Map<String, T> getAll()
    {
        return mDynamoMap;
    }

    /**
     * Some Dynamos may require extra initialisation args. This interface allows creation of Dynamo
     * to be defined at time of request. If using Dynamo init args make sure to include them in the
     * meta field of {@link #getDynamo(String, couk.doridori.dynamo.DynamoHolder.DynamoFactory)}
     */
    public interface DynamoFactory<T>
    {
        public T buildDynamo();
    }
}
