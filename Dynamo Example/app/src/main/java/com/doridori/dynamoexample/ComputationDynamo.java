package com.doridori.dynamoexample;

import android.os.AsyncTask;

import com.doridori.dynamo.Dynamo;
import com.doridori.dynamo.StateMachine;

import java.util.Random;

/**
 * Example Dynamo implementation that performs some arbitrary asynchronous operation.
 */
public class ComputationDynamo extends Dynamo<ComputationDynamo.ComputationState>
{
    //======================================================================================
    // CONSTRUCTOR
    //======================================================================================

    public ComputationDynamo()
    {
        //set the initial state
        newState(new UninitializedState());
    }

    //======================================================================================
    // METHODS
    //======================================================================================

    public void startComputation()
    {
        // instead of calling through to state/strategy methods we could push the new state on
        // directly - but then would need to check current state and that requires instanceOf
        // and handling of state transitions outside of the states
        getStateMachine().getCurrentState().startComputation();
    }

    //======================================================================================
    // STATES
    //======================================================================================

    /**
     * Interface for shared computation state behaviour methods.
     *
     * Abstract as extending an abstract class.
     */
    protected abstract class ComputationState extends StateMachine.State
    {
        public abstract void startComputation();

        /**
         * Visitor pattern
         */
        public abstract void accept(ComputationVisitor visitor);
    }

    public class UninitializedState extends ComputationState
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

    public class PerformingComputationState extends ComputationState
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

    public class ComputationFinishedState extends ComputationState
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
        getStateMachine().getCurrentState().accept(computationVisitor);
    }
}
