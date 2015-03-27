package com.doridori.dynamo;

import com.doridori.dynamo.StateMachine;

import java.util.Observable;

/**
 * A base Dynamo. This is very lightweight and holds the StateMachine handling code.
 *
 * @param <T> Your base State class. You can leave this as {@link com.doridori.dynamo.StateMachine.State}
 *           if you are not adding an extra State interface.
 */
public class Dynamo<T extends StateMachine.State> extends Observable
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
        mStateMachine.nextState(newState);
        setChanged();
        notifyObservers();
    }
}
