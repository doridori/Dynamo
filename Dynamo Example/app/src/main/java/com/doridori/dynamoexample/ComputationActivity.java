package com.doridori.dynamoexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

/**
 * Example Activity that interacts with a stateful-Dynamo instance
 */
public class ComputationActivity extends ActionBarActivity implements Observer, ComputationDynamo.Visitor
{
    private ComputationDynamo mComputationDynamo;

    private Button mGoButton;
    private TextView mResultTxt;

    //============================================================================
    // Lifecycle
    //============================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoButton = (Button) findViewById(R.id.button_go);
        mResultTxt = (TextView) findViewById(R.id.result);

        //create / obtain ref to controller with some meta data - can be created with intent extras OR a saved-state persisted UUID - //TODO some simple way to specify scope rules needed.
        //For this example we just want one controller attached to this activity that will persist for ALL instances of this activity.
        mComputationDynamo = DynamoManager.getInstance().getComputationDynamo("ExampleFixedControllerName");
        mComputationDynamo.addObserver(this);
        mComputationDynamo.visitCurrentState(this);
    }

    @Override protected void onDestroy()
    {
        super.onDestroy();
        mComputationDynamo.deleteObserver(this);
    }

    //============================================================================
    // Buttons
    //============================================================================

    public void goButtonPressed(View view)
    {
        mComputationDynamo.startComputation();
    }

    //======================================================================================
    // Observe controller changes
    //======================================================================================

    @Override
    public void update(Observable observable, Object data)
    {
        //visiting the current state will result in one of the onState() methods in this class to be called
        mComputationDynamo.visitCurrentState(this);
    }

    //======================================================================================
    // Dynamo States
    //======================================================================================
    // the below onState methods are where we should perform all UI transitions.

    @Override
    public void onState(ComputationDynamo.UninitializedState state)
    {
        mResultTxt.setText("Ready & waiting");
    }

    @Override
    public void onState(ComputationDynamo.PerformingComputationState state)
    {
        mResultTxt.setText("Thinking...");
        mGoButton.setEnabled(false);
    }

    @Override
    public void onState(ComputationDynamo.ComputationFinishedState state)
    {
        mResultTxt.setText("I think its "+state.getResult()+"!");
        mGoButton.setText("Compute another!");
        mGoButton.setEnabled(true);
    }
}
