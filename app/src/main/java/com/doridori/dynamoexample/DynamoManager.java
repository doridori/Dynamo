package com.doridori.dynamoexample;

import com.doridori.dynamo.DynamoHolder;

/**
 * Singleton. Handles Dynamo instances.
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
    // Computation Dynamo mgmt
    //======================================================================================

    /**
     * Controller manager declaration
     */
    public DynamoHolder<ComputationDynamo> mComputationDynamoHolder =
            new DynamoHolder<ComputationDynamo>(1)
                {
                    @Override
                    protected ComputationDynamo newDynamo()
                    {
                        return new ComputationDynamo();
                    }
                };

    /**
     * @param meta See {@link DynamoHolder#getDynamo(String)} meta arg
     * @return
     */
    public ComputationDynamo getComputationDynamo(String meta)
    {
        return mComputationDynamoHolder.getDynamo(meta);
    }

    //anon class that can create instance of dynamo
    //or bundle pass in instead
    //or define controllers and holder size in list
}
