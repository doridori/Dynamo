package com.doridori.dynamoexample;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Singleton. Handles xController instances.
 * //TODO example of clearing a load of controllers at once
 //TODO example of memory constrained controllers
 //TODO Scoping - session - screen by screen - elements on one screen
 //TODO some controllers will be attached to view
 */
public class DynamoManager
{
    //======================================================================================
    // Singleton
    //======================================================================================

    private static DynamoManager sInstance = new DynamoManager();

    public static synchronized DynamoManager getInstance()
    {
        return sInstance;
    }

    //======================================================================================
    // Controller Instance holder
    //======================================================================================

    /**
     * Holds a ref to a n xControllers. Subclass one of these for each controller type.
     *
     * @param <T> Controller class
     * //TODO test
     */
    private abstract class XControllerHolder<T>
    {
        /**
         * Map for quick access to xControllers indexed by meta
         */
        private Map<String, T> mXControllerMap = new HashMap<>();
        /**
         * Queue of meta for easy size limiting
         */
        private Queue<String> mMetaQueue = new LinkedList<>();

        private final int mMaxSize;

        /**
         * @param maxSize 1 or more
         */
        protected XControllerHolder(int maxSize)
        {
            if(maxSize < 1)
                throw new IllegalArgumentException("maxSize must be bigger than 1:"+maxSize);

            mMaxSize = maxSize;
        }

        /**
         * @param meta This can be anything i.e. UUID, url, something else that can represent the related View component
         * @return
         */
        T getXController(String meta)
        {
            if(!mXControllerMap.containsKey(meta))
            {
                T xController = newXController();
                mXControllerMap.put(meta, xController);
                mMetaQueue.add(meta);

                //check max size not exceeded
                if(mMetaQueue.size() > mMaxSize)
                {
                    String metaRemoved = mMetaQueue.remove();
                    mXControllerMap.remove(metaRemoved);
                }

                return xController;
            }
            else
            {
                return mXControllerMap.get(meta);
            }
        }

        /**
         * Generics and object creation dont mix - plus some controllers may require extra
         * initialisation args so pretty easy to add in this way
         */
        abstract T newXController();
    }

    //======================================================================================
    // Computation xController mgmt
    //======================================================================================

    /**
     * Controller manager declaration
     */
    private XControllerHolder<ComputationDynamo> mComputationXControllerHolder =
            new XControllerHolder<ComputationDynamo>(1)
                {
                    @Override
                    ComputationDynamo newXController()
                    {
                        return new ComputationDynamo();
                    }
                };

    /**
     * @param meta See {@link DynamoManager.XControllerHolder#getXController(String)} meta arg
     * @return
     */
    public ComputationDynamo getComputationXController(String meta)
    {
        return mComputationXControllerHolder.getXController(meta);
    }

}
