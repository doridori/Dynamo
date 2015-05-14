/*
Copyright 2015 Dorian Cussen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

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
