package goalsample.client1.goal_sample.goalsample.com.goalsample;

import android.content.Intent;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button button = null;
    private Animation blink;
    private Button add;
    private Toast toast;
   // private LinearLayout mainLinear;
    private String getname = null;
    public LinearLayout mainLinear;
    static int i;
    private database_class dbc;
    private GridView gridview;
    LinearLayout[] item = new LinearLayout[]{
    };
    List<LinearLayout> ITEM_LIST;
    private ArrayAdapter<LinearLayout> arrayadapter;
    //private Cursor total_goals;
    String buttonText;

    @SuppressWarnings("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       mainLinear = (LinearLayout)findViewById(R.id.mainLinear);
    /*
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Set<String> buttonSet = prefs.getStringSet("saved_buttons", null);*/
        dbc = new database_class(MainActivity.this);
        dbc.open();
        fetch_all_goals();

      /*  if(buttonSet != null) {
           // int total_goals = dbc.count();
           // for (int i = 1; i <= total_goals; i++) {
               for (String buttonText : buttonSet) {

            }*/

            blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
            add = (Button) findViewById(R.id.add);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add.startAnimation(blink);
                    Intent i = new Intent(MainActivity.this, AddNewGoal.class);
                    startActivity(i);
                }
            });

          /* // if (i == 2) {
                Bundle b = getIntent().getExtras();
                getname = b.getString("NAME" , "qazity").toString();
                if (getname.matches("qazity")) {
                   // Toast.makeText(MainActivity.this, " dafad", Toast.LENGTH_SHORT).show();
                } else {
                   // i = 0;
                    dynamically_add_buttons();
               // }


        }*/
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
       /* if (id == R.id.action_refresh) {
            fetch_all_goals();
            return true;
        }*/
        if (id == R.id.action_exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }





    public void fetch_all_goals() {
        Cursor c = dbc.show_all_goals();

        if (c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                String gName = c.getString(c.getColumnIndexOrThrow("Goal_Name"));
                //  Toast.makeText(MainActivity.this , "NAME IS: " +gName , Toast.LENGTH_SHORT).show();

                button = new Button(this);
                button.setText(gName);
                button.setTextColor(Color.WHITE);
                button.setBackgroundResource(R.drawable.rounded);
                button.setGravity(Gravity.CENTER);
                button.setPadding(20, 20, 20, 10);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                // layoutParams.topMargin = 10;
                // layoutParams.setMargins(10, 10, 10, 10);
                button.setLayoutParams(layoutParams);
                mainLinear.addView(button);
                c.moveToNext();
            }
           // Toast.makeText(MainActivity.this, " For Instance Click to view last added item", Toast.LENGTH_SHORT).show();
           final Button finalButton = button;
            // button = ((Button) findViewById(id_));
             button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   finalButton.startAnimation(blink);
                    Intent i = new Intent(MainActivity.this, PopupGoals.class);
                    startActivity(i);

                }
            });
           button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    MainActivity.this.showDialog(1);
                    Button b = (Button)v;
                    buttonText = b.getText().toString();
                    //Toast.makeText(MainActivity.this , "Clicked: " +buttonText , Toast.LENGTH_LONG).show();
                    return true;
                }
            });
         /*   mainLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    buttonText = b.getText().toString();
                    Toast.makeText(MainActivity.this , "Clicked: " +buttonText , Toast.LENGTH_LONG).show();

                }
            });*/
        }

        else
        {
            Toast.makeText(MainActivity.this , "Add Some Goals" , Toast.LENGTH_LONG).show();
        }

        }



    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog =null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (id)
        {
            case 1:
                builder.setMessage("Sure you want to delete Goal?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();


                            }
                        })
                        .setNegativeButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbc.delete_goal(buttonText);
                            }
                        })
                        .setCancelable(false);
                dialog = builder.create();
                break;

            default:

        }
        return dialog;
    }


   /* public void dynamically_add_buttons() {
        //int total_goals = dbc.count();
              //  for (int i = 1; i <= total_goals; i++) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                   // params.topMargin = 10;
                  //  params.setMargins(20, 20, 20, 20);
                    Button button = null;

                     button = new Button(this);
                    button.setText(getname);
                   // button.setId(i);
                    //final int id_ = button.getId();
                    button.setTextColor(Color.WHITE);
                    button.setBackgroundResource(R.drawable.rounded);
                    button.setGravity(Gravity.CENTER);
                    button.setPadding(20, 20, 20, 20);
                   button.setLayoutParams(params);
                    mainLinear.addView(button);
                    final Button finalButton = button;
                   // button = ((Button) findViewById(id_));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finalButton.startAnimation(blink);
                            Intent i = new Intent(MainActivity.this, PopupGoals.class);
                            startActivity(i);

                        }
                    });
                    button.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            return false;
                        }
                    });

                    //here comes the part that saves the button strings persistently
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Set<String> buttonSet = prefs.getStringSet("saved_buttons", null);
                    if (buttonSet == null) {
                        buttonSet = new HashSet<String>();
                    }
                    buttonSet.add(getname);
                    prefs.edit().putStringSet("saved_buttons", buttonSet).commit();
    }*/
}
