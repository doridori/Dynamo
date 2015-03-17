package com.doridori.dynamoexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;


public class ComputationActivity extends ActionBarActivity implements Observer, ComputationDynamo.ComputationVisitor
{
    //TODO populate with annotation and auto-saved state for UUID?
    private ComputationDynamo mXController;

    private Button mGoButton;
    private TextView mResultTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoButton = (Button) findViewById(R.id.button_go);
        mResultTxt = (TextView) findViewById(R.id.result);

        //create / obtain ref to controller with some meta data - can be created with intent extras - some simple way to specify scope rules needed.
        //For this example we just want one controller attached to this activity that will persist for ALL instances of this activity.
        //TODO list differnt strategies - UUID (with saved state persistence), fixed name (i.e. twitter feed), url (i.e. bbc), intent extra
        mXController = DynamoManager.getInstance().getComputationXController("ExampleFixedControllerName");
        mXController.addObserver(this);
        mXController.visitCurrentState(this);
    }

    public void goButtonPressed(View view)
    {
        mXController.startComputation();
    }

    //======================================================================================
    // Observe controller changes
    //======================================================================================

    @Override
    public void update(Observable observable, Object data)
    {
        mXController.visitCurrentState(this);
    }

    //======================================================================================
    // Controller States
    //======================================================================================

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
