package couk.doridori.dynamo;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Dorian Cussen
 *         Date: 30/03/2015
 */
public class DynamoTest
{
    @Test
    public void observerCalledOnSingleStateChange()
    {
        Dynamo dynamo = new Dynamo();
        Observer observerMock = Mockito.mock(Observer.class);
        dynamo.addObserver(observerMock);

        dynamo.newState(Mockito.mock(StateMachine.State.class));
        Mockito.verify(observerMock, Mockito.times(1)).update(Matchers.isA(Observable.class), Matchers.any());
    }

    @Test
    public void observerCalledOnDoubleStateChange()
    {
        //did see a bug with original impl that back to back transitions would result if the middle State lifecycle methods being skipped

        final Dynamo dynamo = new Dynamo();
        final StateMachine.State firstState = new StateMachine.State(){};
        final StateMachine.State secondState = new StateMachine.State(){};

        Observer observerSpy = Mockito.spy(new Observer()
        {
            int callCount = 0;

            @Override
            public void update(Observable o, Object arg)
            {
                callCount++;
                switch (callCount)
                {
                    case 1:
                       Assert.assertEquals(firstState, dynamo.getStateMachine().getCurrentState());
                        break;
                    case 2:
                        Assert.assertEquals(secondState, dynamo.getStateMachine().getCurrentState());
                        break;
                    default:
                        Assert.fail("bad count:"+callCount);
                }
            }
        });

        dynamo.addObserver(observerSpy);

        dynamo.newState(firstState);
        dynamo.newState(secondState);

        //make sure the update method was called only twice so the test code in the observerSpy was defo executed
        Mockito.verify(observerSpy, Mockito.times(2)).update(Matchers.isA(Observable.class), Matchers.any());
    }
}
