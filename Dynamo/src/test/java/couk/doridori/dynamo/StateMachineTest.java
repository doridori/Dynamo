package couk.doridori.dynamo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Tests for {@link couk.doridori.dynamo.StateMachine}
 *
 * @author Dorian Cussen
 *         Date: 27/03/2015
 */
public class StateMachineTest
{
    private StateMachine<StateMachine.State> mStateStateMachine;

    @Before
    public void setup()
    {
        mStateStateMachine = new StateMachine<StateMachine.State>();
    }

    @After
    public void tearDown()
    {
        mStateStateMachine = null;
    }

    @Test
    public void firstState_lifecycleMethodsCalled()
    {
        StateMachine.State mockedState = Mockito.mock(StateMachine.State.class);
        mStateStateMachine.nextState(mockedState);
        Mockito.verify(mockedState).enteringState();
    }

    @Test
    public void singleStateTransition_callBothStateTransitionMethods()
    {
        StateMachine.State firstMockedState = Mockito.mock(StateMachine.State.class);
        StateMachine.State secondMockedState = Mockito.mock(StateMachine.State.class);

        InOrder inOrder = Mockito.inOrder(firstMockedState, secondMockedState);

        mStateStateMachine.nextState(firstMockedState);
        inOrder.verify(firstMockedState).enteringState();

        mStateStateMachine.nextState(secondMockedState);
        inOrder.verify(firstMockedState).exitingState();
        inOrder.verify(secondMockedState).enteringState();
    }
}
