package goalsample.client1.goal_sample.goalsample.com.goalsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewGoal extends AppCompatActivity {

    private Animation blink;
    private EditText goalname_editText;
    private EditText goalunit_editText;
    private EditText goaldays_editText;
    private DatePicker date;
    private Button addgoal;
    private String goalname;
    private int goalunit;
    private int goaldays;
    private int dayofMonth;
    private int month;
    private int year;
    private database_class dbc;
    String value2;
    String value3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_goal);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        goalname_editText = (EditText)findViewById(R.id.goalname_editText);
        goalunit_editText = (EditText)findViewById(R.id.goalunit_editText);
        goaldays_editText = (EditText)findViewById(R.id.goaldays_editText);
        date = (DatePicker)findViewById(R.id.datePicker);
        addgoal = (Button)findViewById(R.id.addgoal);

        goalname = goalname_editText.getText().toString();
        value2 = goalunit_editText.getText().toString();
        value3 = goaldays_editText.getText().toString();
        dayofMonth = date.getDayOfMonth();
        month = date.getMonth();
        year= date.getYear();

       dbc = new database_class(AddNewGoal.this);
        dbc.open();

        addgoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // if(!goalname.isEmpty() && !value2.isEmpty() && !value3.isEmpty()) {
                //if(goalname.matches("") || value2 .matches("") ||  value3 .matches("")) {
                if(goalname_editText.getText().toString().matches("") || goalunit_editText.getText().toString() .matches("") ||  goaldays_editText.getText().toString() .matches(""))
                {
                    Toast.makeText(AddNewGoal.this , "Please Fill Fields" , Toast.LENGTH_SHORT).show();

                }
                else
                {
                    goalunit = Integer.valueOf(goalunit_editText.getText().toString());
                    goaldays = Integer.valueOf(goaldays_editText.getText().toString());

                   /* goalunit = Integer.parseInt(value2);
                    goaldays = Integer.parseInt(value3);*/
                    Save_into_DB();
                }
            }
        });

    }


    public  void Save_into_DB()
    {
            dbc.AddNewGoal(goalname_editText.getText().toString(), goalunit, goaldays, dayofMonth, month,year);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_refresh) {
            fetch_all_goals();
            return true;
        }*/
        if (id == R.id.action_exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
