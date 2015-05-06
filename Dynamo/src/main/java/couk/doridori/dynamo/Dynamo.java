package couk.doridori.dynamo;

import couk.doridori.dynamo.StateMachine;

import java.util.Observable;

/**
 * A base Dynamo. This is very lightweight and holds the StateMachine handling code.
 *
 * This abstract class holds a `StateMachine` instance and extends `Observable`. You extends this for each `Dynamo`
 * subclass you require in your application and your implementation will consist of your own `State` classes and
 * logic for switching between them.
 *
 * @param <T> Your base State class. You can leave this as {@link couk.doridori.dynamo.StateMachine.State}
 *           if you are not adding an extra State interface.
 */
public abstract class Dynamo<T extends StateMachine.State> extends Observable
{
    //======================================================================================
    // FIELDS
    //======================================================================================

    private StateMachine<T> mStateMachine = new StateMachine();

    //======================================================================================
    // GETTERS
    //======================================================================================

    protected StateMachine<T> getStateMachine()
    {
        return mStateMachine;
    }

    //======================================================================================
    // STATE HANDLING
    //======================================================================================

    protected void newState(T newState)
    {
        setChanged();
        mStateMachine.nextState(newState);
        notifyObservers();
    }
}
