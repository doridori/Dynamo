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
        Dynamo dynamo = new Dynamo(){};
        Observer observerMock = Mockito.mock(Observer.class);
        dynamo.addObserver(observerMock);

        dynamo.newState(Mockito.mock(StateMachine.State.class));
        Mockito.verify(observerMock, Mockito.times(1)).update(Matchers.isA(Observable.class), Matchers.any());
    }

    @Test
    public void observerCalledOnDoubleStateChange_shouldOnlyPassLastStateOnce()
    {
        //old version had bug when synchronous state change the observer would be called twice with the newest state

        final Dynamo dynamo = new Dynamo(){};

        final StateMachine.State secondState = new StateMachine.State(){};
        final StateMachine.State firstState = new StateMachine.State(){
            @Override
            public void enteringState()
            {
                super.enteringState();
                dynamo.newState(secondState);
            }
        };

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
                       Assert.assertEquals(secondState, dynamo.getStateMachine().getCurrentState());
                        break;
                    default:
                        Assert.fail("bad count:"+callCount);
                }
            }
        });

        dynamo.addObserver(observerSpy);

        dynamo.newState(firstState);

        //make sure the update method was called only twice so the test code in the observerSpy was defo executed
        Mockito.verify(observerSpy, Mockito.times(1)).update(Matchers.isA(Observable.class), Matchers.any());
    }
}
