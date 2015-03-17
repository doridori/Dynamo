package com.doridori.dynamoexample;

import android.os.AsyncTask;

import com.doridori.dynamoexample.state.StateMachine;

import java.util.Observable;
import java.util.Random;

/**
 * Follows the Pattern X spec
 *
 * //todo what can we abstrct - not much - boilerplate is accept method on each state and main visit method
 * //what state generation can we drive with annotations? We need to define states but some boilerplate methods,
 * we need to define visitor interface so can code against (or do we? could annotate methods for state calling!!!e
 *
 * //TODO HIDING BUTTON - could have a UI presentor in here which is passed through with each state change? that way view logic spec is captured outside of the view impl itself
 * //TODO can we use annotation processing for any of this? - maye
 */
public class ComputationDynamo extends Observable
{
    //======================================================================================
    // FIELDS
    //======================================================================================

    private StateMachine<StateBehaviour> mComputationStateMachine = new StateMachine();

    //======================================================================================
    // METHODS
    //======================================================================================

    public ComputationDynamo()
    {
        mComputationStateMachine.nextState(new UninitializedState());
    }

    public void startComputation()
    {
        //instead of calling through to state/strategy methods we could push the new state on
        // directly - but then would need to check current state and that requires instanceOf
        // and handling of state transitions outside of the states
        mComputationStateMachine.getCurrentState().startComputation();
    }

    //======================================================================================
    // STATE HANDLING
    //======================================================================================

    private void newState(StateBehaviour newState)
    {
        mComputationStateMachine.nextState(newState);
        setChanged();
        notifyObservers();
    }

    //======================================================================================
    // STATES
    //======================================================================================

    /**
     * Interface for shared state behaviour methods.
     */
    private abstract class StateBehaviour extends StateMachine.State
    {
        public abstract void startComputation();

        /**
         * Visitor pattern
         */
        public abstract void accept(ComputationVisitor visitor);
    }

    public class UninitializedState extends StateBehaviour
    {
        @Override
        public void startComputation()
        {
            newState(new PerformingComputationState());
        }

        @Override
        public void accept(ComputationVisitor visitor)
        {
            visitor.onState(this);
        }
    }

    public class PerformingComputationState extends StateBehaviour
    {
        @Override
        public void enteringState()
        {
            new AsyncTask<Void, Void, Integer>()
            {
                @Override
                protected Integer doInBackground(Void... params)
                {
                    try
                    {
                        Thread.sleep(3000); //Oh hai! Don't mind me, just taking a rest
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    int randomResult = new Random().nextInt(1000);
                    return randomResult;
                }

                @Override
                protected void onPostExecute(Integer integer)
                {
                    newState(new ComputationFinishedState(integer));
                }
            }.execute();
        }

        @Override
        public void startComputation()
        {
            throw new IllegalStateException("Cannot start computation in this state "+this.getClass().getName());
        }

        @Override
        public void accept(ComputationVisitor visitor)
        {
            visitor.onState(this);
        }
    }

    public class ComputationFinishedState extends StateBehaviour
    {
        private int mResult;

        public ComputationFinishedState(int result)
        {
            mResult = result;
        }

        /**
         *  An example of a state having its own interface so the View can easily grab state related data
         */
        public int getResult()
        {
            return mResult;
        }

        @Override
        public void startComputation()
        {
            newState(new PerformingComputationState());
        }

        @Override
        public void accept(ComputationVisitor visitor)
        {
            visitor.onState(this);
        }
    }

    //======================================================================================
    // VISITOR
    //======================================================================================

    public interface ComputationVisitor
    {
        public void onState(UninitializedState state);
        public void onState(PerformingComputationState state);
        public void onState(ComputationFinishedState state);
    }

    public void visitCurrentState(ComputationVisitor computationVisitor)
    {
        mComputationStateMachine.getCurrentState().accept(computationVisitor);
    }




}
