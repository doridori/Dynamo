package couk.doridori.dynamo;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Observable;

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
}
