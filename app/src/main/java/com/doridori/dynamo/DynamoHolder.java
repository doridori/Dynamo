package com.doridori.dynamo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Holds a ref to a n Dynamos. Subclass one of these for each controller type in your app.
 *
 * This generic Dynamo holder has a notion of Dynamo `meta` data. This is used to hook up View
 * elements (Activities, Fragments and View) to Dynamo instances. This can be of the following type:
 *
 * - Fixed Name: This works well for a View that will show the same data regardless of the View
 *   instance i.e. an app wide notepad. The fixed name could be the class name or some other constant.
 *
 * - Variable Name: You may have a View element that takes some input data (say a data source URL or
 *   arguments) via the Intent Bundle, which is used to curate the data obtained via the controller
 *   instance. You could then use this as the meta data. This would mean View instances that show
 *   the same data would hook up to existing Dynamos that may already have loaded the data etc.
 *
 * This class also has a notion of size. The Dynamos are held in a FIFO queue of the passed-in size.
 *
 * Alternatively you may want to improve this class by adding some form of WeakReferencing and
 * caching as many Dynamo instances as possible OR introduce your own method of calculating size
 * constraints.
 *
 * @param <T> Dynamo class
 * //TODO test
 */
public class DynamoHolder<T>
{
    /**
     * Map for quick access to Dynamo indexed by meta
     */
    private Map<String, T> mDynamoMap = new HashMap<>();
    /**
     * Queue of meta for easy size limiting
     */
    private Queue<String> mMetaQueue = new LinkedList<>();

    private final int mMaxSize;

    /**
     * @param maxSize 1 or more
     */
    public DynamoHolder(int maxSize)
    {
        if(maxSize < 1)
            throw new IllegalArgumentException("maxSize must be bigger than 1:"+maxSize);

        mMaxSize = maxSize;
    }

    /**
     * @param meta This can be anything i.e. UUID, url, something else that can represent the related View component
     * @return
     */
    public T getDynamo(String meta, DynamoFactory<T> dynamoFactory)
    {
        if(!mDynamoMap.containsKey(meta))
        {
            T dynamo = dynamoFactory.buildDynamo();
            mDynamoMap.put(meta, dynamo);
            mMetaQueue.add(meta);

            //check max size not exceeded
            if(mMetaQueue.size() > mMaxSize)
            {
                String metaRemoved = mMetaQueue.remove();
                mDynamoMap.remove(metaRemoved);
            }

            return dynamo;
        }
        else
        {
            return mDynamoMap.get(meta);
        }
    }

    /**
     * Some Dynamos may require extra initialisation args. This interface allows creation of Dynamo
     * to be defined at time of request. If using Dynamo init args make sure to include them in the
     * meta field of {@link #getDynamo(String, com.doridori.dynamo.DynamoHolder.DynamoFactory)}
     */
    public interface DynamoFactory<T>
    {
        public  T buildDynamo();
    }
}
