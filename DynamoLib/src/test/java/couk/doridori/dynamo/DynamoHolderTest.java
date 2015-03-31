package couk.doridori.dynamo;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Dorian Cussen
 *         Date: 30/03/2015
 */
public class DynamoHolderTest
{
    private static class TestDynamo extends Dynamo{};

    @Test
    public void singleDynamo_testCanRetrieve()
    {
        testCanRetrieveSameMetaName(1);
        testCanRetrieveSameMetaName(2);
    }

    /**
     * Tests that a Dyanmo can be retreived from a DynamoHolder when using the same meta name.
     *
     * @param holderSize allows the test to be run with diff sizes of DynamoHolder
     */
    private void testCanRetrieveSameMetaName(int holderSize)
    {
        DynamoHolder<TestDynamo> testDynamoHolder = new DynamoHolder<TestDynamo>(holderSize);
        DynamoHolder.DynamoFactory dynamoFactorySpy = Mockito.spy(new DynamoHolder.DynamoFactory<TestDynamo>()
        {
            @Override
            public TestDynamo buildDynamo()
            {
                return new TestDynamo();
            }
        });

        Dynamo firstDynamoRequest = testDynamoHolder.getDynamo("testMeta", dynamoFactorySpy);
        Dynamo secondDynamoRequest = testDynamoHolder.getDynamo("testMeta", dynamoFactorySpy);

        Assert.assertEquals(firstDynamoRequest, secondDynamoRequest);

        Mockito.verify(dynamoFactorySpy, Mockito.times(1)).buildDynamo();
    }

    @Test
    public void testCanRetrieveSameMetaName_multipleDynamos()
    {
        DynamoHolder<TestDynamo> testDynamoHolder = new DynamoHolder<TestDynamo>(10);
        DynamoHolder.DynamoFactory dynamoFactorySpy = Mockito.spy(new DynamoHolder.DynamoFactory<TestDynamo>()
        {
            @Override
            public TestDynamo buildDynamo()
            {
                return new TestDynamo();
            }
        });

        Dynamo firstDynamoRequest = testDynamoHolder.getDynamo("testMeta1", dynamoFactorySpy);
        Dynamo secondDynamoRequest = testDynamoHolder.getDynamo("testMeta2", dynamoFactorySpy);
        Dynamo thirdDynamoRequest = testDynamoHolder.getDynamo("testMeta1", dynamoFactorySpy);
        Dynamo fourthDynamoRequest = testDynamoHolder.getDynamo("testMeta2", dynamoFactorySpy);

        Assert.assertEquals(firstDynamoRequest, thirdDynamoRequest);
        Assert.assertEquals(secondDynamoRequest, fourthDynamoRequest);
        Assert.assertNotEquals(firstDynamoRequest, secondDynamoRequest);

        Mockito.verify(dynamoFactorySpy, Mockito.times(2)).buildDynamo();
    }

    @Test
    public void testSizeLimiting_singleDyanmoHolder_shouldRecreateTrimmedDynamos()
    {
        DynamoHolder<TestDynamo> testDynamoHolder = new DynamoHolder<TestDynamo>(1);
        DynamoHolder.DynamoFactory dynamoFactorySpy = Mockito.spy(new DynamoHolder.DynamoFactory<TestDynamo>()
        {
            @Override
            public TestDynamo buildDynamo()
            {
                return new TestDynamo();
            }
        });

        Dynamo firstDynamoRequest = testDynamoHolder.getDynamo("testMeta1", dynamoFactorySpy);
        Dynamo secondDynamoRequest = testDynamoHolder.getDynamo("testMeta2", dynamoFactorySpy);
        Dynamo thirdDynamoRequest = testDynamoHolder.getDynamo("testMeta1", dynamoFactorySpy);
        Dynamo fourthDynamoRequest = testDynamoHolder.getDynamo("testMeta2", dynamoFactorySpy);

        Assert.assertNotEquals(firstDynamoRequest, thirdDynamoRequest);
        Assert.assertNotEquals(secondDynamoRequest, fourthDynamoRequest);
        Assert.assertNotEquals(firstDynamoRequest, secondDynamoRequest);

        Mockito.verify(dynamoFactorySpy, Mockito.times(4)).buildDynamo();
    }

    @Test
    public void testClearAll()
    {
        final String DYNAMO_NAME = "testMeta1";

        DynamoHolder<TestDynamo> testDynamoHolder = new DynamoHolder<TestDynamo>(2);
        DynamoHolder.DynamoFactory dynamoFactory = new DynamoHolder.DynamoFactory<TestDynamo>()
        {
            @Override
            public TestDynamo buildDynamo()
            {
                return new TestDynamo();
            }
        };

        Dynamo firstDynamoRequest = testDynamoHolder.getDynamo(DYNAMO_NAME, dynamoFactory);
        Dynamo secondDynamoRequest = testDynamoHolder.getDynamo(DYNAMO_NAME, dynamoFactory);
        Assert.assertEquals(firstDynamoRequest, secondDynamoRequest);

        testDynamoHolder.clearAll();

        Dynamo thirdDynamoRequest = testDynamoHolder.getDynamo(DYNAMO_NAME, dynamoFactory);
        Assert.assertNotEquals(thirdDynamoRequest, secondDynamoRequest);
    }

    @Test
    public void testClearSpecific()
    {
        final String DYNAMO_NAME = "testMeta1";

        DynamoHolder<TestDynamo> testDynamoHolder = new DynamoHolder<TestDynamo>(2);
        DynamoHolder.DynamoFactory dynamoFactory = new DynamoHolder.DynamoFactory<TestDynamo>()
        {
            @Override
            public TestDynamo buildDynamo()
            {
                return new TestDynamo();
            }
        };

        Dynamo firstDynamoRequest = testDynamoHolder.getDynamo(DYNAMO_NAME, dynamoFactory);
        Dynamo secondDynamoRequest = testDynamoHolder.getDynamo(DYNAMO_NAME, dynamoFactory);
        Assert.assertEquals(firstDynamoRequest, secondDynamoRequest);

        testDynamoHolder.clear(DYNAMO_NAME);

        Dynamo thirdDynamoRequest = testDynamoHolder.getDynamo(DYNAMO_NAME, dynamoFactory);
        Assert.assertNotEquals(thirdDynamoRequest, secondDynamoRequest);
    }
}
