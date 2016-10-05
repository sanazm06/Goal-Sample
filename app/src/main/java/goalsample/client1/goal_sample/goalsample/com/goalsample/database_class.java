package goalsample.client1.goal_sample.goalsample.com.goalsample;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Sana on 13-Apr-16.
 */
public class database_class {
    Context con;
    MainActivity ma;
    private dbhelper ourhelper;
    public SQLiteDatabase ourDatabase;
    public final static String createTable="CREATE TABLE goal ( _id INTEGER PRIMARY KEY AUTOINCREMENT,Goal_Name Text,Goal_Units INTEGER,Goal_Noof_Days INTEGER,Goal_day INTEGER,Goal_month INTEGER,Goal_year INTEGER);" ;
    private static final String DataBase_Table ="GOAL";

    private static final String key_ID ="_id";
    private static final String key_GOAL_NAME ="Goal_Name";
    private static final String key_GOAL_UNITS="Goal_Units";
    private static final String key_GOAL_NOOF_DAYS ="Goal_Noof_Days";
    private static final String key_GOAL_DAY="Goal_day";
    private static final String key_GOAL_MONTH="Goal_month";
    private static final String key_GOAL_YEAR="Goal_year";
    int getmarks; int nomarks;
    String nam;
    private Toast toast;
    private LinearLayout mainLinearr;
    private Animation blink;


    database_class(Context conn)
    {
        this.con=conn;
        //ourhelper = new quransummary_DicAdapter(conn, DataBase_Table , null,1);
    }

    public static class dbhelper extends SQLiteOpenHelper
    {

        public dbhelper(Context context) {
            //super(context, "Transport.db", null, 1);
            super(context, "GOAL", null, 1);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(createTable);
            // TODO Auto-generated method stub
				/*db.execSQL("CREAT TABLE" + DataBase_Table + "(" + key_id + "INTEGER PRIMARY KEY AUTOINCREMENT,"+
						key_name +  " TEXT NOT NULL," +
								key_address+ "TEXT NOT NULL"
								);*/
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub

        }
    }
    public void open()
    {
        // TODO Auto-generated method stub
       ourhelper= new   dbhelper(con);
        ourDatabase =ourhelper.getWritableDatabase();
    }
    public void AddNewGoal(String name,int units, int noofdays,int day,int month,int year)

    {
        blink = AnimationUtils.loadAnimation(con, R.anim.blink);
        nam = name;
        ContentValues values = new ContentValues(5);
        values.put("Goal_Name", name);
        values.put("Goal_Units", units);
        values.put("Goal_Noof_Days", noofdays);
        values.put("Goal_day", day);
        values.put("Goal_month", month);
        values.put("Goal_year", year);

        boolean chk = exist();
        // if(chk == true)
        ourDatabase.insert("goal", "Goal_Name", values);
        Toast.makeText(con, "For INSTANCE Click to view last added item", Toast.LENGTH_SHORT).show();
        ma = new MainActivity();
        Intent i = new Intent(con,MainActivity.class);
        i.putExtra("NAME" , nam);
        con.startActivity(i);
        //ma.dynamically_add_buttons(nam);
    }
    Boolean exist()
    {
        Cursor c = null;
        boolean tableExists = false;
/* get cursor on it */
        try
        {
            c = ourDatabase.query("goal", null, null, null, null, null, null);
            if(c.getCount() == 0 || c.getCount() < 114)
                tableExists = true;
        }
        catch (Exception e) {
    /* fail */
        }
        return tableExists;
    }

    public void delete_goal(String buttonTextt)
    {
        ourDatabase = ourhelper.getWritableDatabase();
        //ourDatabase.delete("goal","where Goal_Name ='"+buttonTextt+"' ", null);
        ourDatabase.execSQL("DELETE FROM " + "goal"+ " WHERE "+"Goal_Name"+"='"+buttonTextt+"'");
        ourDatabase.close();

    }
    public Cursor show_all_goals()

    {
        ourDatabase = ourhelper.getReadableDatabase();
        Cursor c = ourDatabase.rawQuery("select * from goal",null);
        return  c;
    }



    public Cursor show_specific_goal(String name)

    {
        ourDatabase = ourhelper.getReadableDatabase();
        Cursor c = ourDatabase.rawQuery("select * from goal where Goal_Name='"+name+"'",null);
        return  c;
    }

    public Cursor pick_surah(int ques, int prange)

    {
        ourDatabase = ourhelper.getReadableDatabase();
        //Cursor c = ourDatabase.rawQuery("select * from surah where ParaNo > 1 and ParaNo <= '"+prange+"' ",null);
        Cursor c = ourDatabase.rawQuery("select * from surah where ParaNo BETWEEN 1 AND '"+prange+"' order by RANDOM()  limit '"+ques+"' ",null);
        return  c;
    }


    public Boolean check_surahSeq(String item , int seqnum)

    {
        ourDatabase = ourhelper.getReadableDatabase();
        Cursor c = ourDatabase.rawQuery("select * from surah where SurahName='"+item+"'",null);
        c.moveToPosition(0);
        int picked_seqnum = c.getInt(c.getColumnIndex("SurahNo"));
        if(picked_seqnum == seqnum) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean check_surahfact(String item , int seqnum, int ruku, int ayaat)

    {
        ourDatabase = ourhelper.getReadableDatabase();
        Cursor c = ourDatabase.rawQuery("select * from surah where SurahName='"+item+"'",null);
        c.moveToPosition(0);
        int picked_seqnum = c.getInt(c.getColumnIndex("SurahNo"));
        int picked_ruku = c.getInt(c.getColumnIndex("Ruku"));
        int picked_ayaat = c.getInt(c.getColumnIndex("Ayaat"));
        if(picked_seqnum == seqnum && picked_ruku == ruku && picked_ayaat == ayaat)
        {
           // getmarks = getmarks +1;
            return true;
        }
        /*else if(picked_seqnum == seqnum && picked_ruku == ruku ) {
            getmarks = getmarks +2;
            return getmarks;
        }
        else if(picked_seqnum == seqnum && picked_ayaat == ayaat) {
            getmarks = getmarks +2;
            return getmarks;
        }

        else if(picked_ruku == ruku && picked_ayaat == ayaat) {
            getmarks = getmarks +2;
            return getmarks;
        }
        else if(picked_seqnum == seqnum) {
            getmarks = getmarks +1;
            return getmarks;
        }
        else if(picked_ruku == ruku) {
            getmarks = getmarks +1;
            return getmarks;
        }
        else if(picked_ayaat == ayaat) {
            getmarks = getmarks +1;
            return getmarks;
        }*/
        else {
           // nomarks = nomarks +1;
            return false;
        }
    }

}
