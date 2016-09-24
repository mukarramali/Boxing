package prashushi.boxing;

/**
 * Created by Dell User on 9/13/2016.
 */
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Hashtable;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.DatabaseUtils;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.database.sqlite.SQLiteDatabase;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Boxing";
    public static final String TABLE_NAME_FIGHTS = "fights";
    public static final String FIGHTS_ID = "fight_id";
    public static final String FIGHTER1 = "fighter1";
    public static final String FIGHTER2 = "fighter2";
    public static final String ROUNDS = "rounds";
    public static final String ROUND_NO = "round_no";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "create table if not exists "+TABLE_NAME_FIGHTS +" " +
                        "(fight_id integer primary key, fighter1 text, fighter2 text, rounds int, round_no int)"
        );
//add field type_flag
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table if not exists "+TABLE_NAME_FIGHTS +" " +
                        "(fight_id integer primary key, fighter1 text, fighter2 text, rounds int, round_no int)"
        );
//add field type_flag
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_FIGHTS );
        onCreate(db);
    }

    public boolean insertFight  (int FIGHTS_ID, String name, String cost, String quantity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("item_name", name);
        contentValues.put("item_cost", cost);
        contentValues.put("item_qty", quantity);
//add field type_flag
        db.insert(TABLE_NAME_FIGHTS , null, contentValues);
        return true;
    }


    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME_FIGHTS);
    }
    //type_flag
    public boolean updateItem (String id, String cost, String quantity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("item_qty", quantity);
        contentValues.put("item_cost", cost);
        db.update(TABLE_NAME_FIGHTS, contentValues, "item_id = ? ", new String[] {id } );

        if(quantity.compareTo("0")==0)
            deleteItems(id);
        return true;
    }

    public Integer deleteItems (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_FIGHTS, "item_id = ? ", new String[] { id });
    }

    public void deleteDB ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_FIGHTS );
        db.execSQL(
                "create table "+TABLE_NAME_FIGHTS +" " +
                        "(item_id integer primary key, item_name text,item_cost text,item_qty text)"
        );

    }

    public ArrayList<String> getItem(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME_FIGHTS +" where item_id='"+id+"'", null );
        ArrayList<String> array=new ArrayList<>();
        array.add(res.getString(res.getColumnIndex(FIGHTS_ID)));
        res.close();
        return array;
    }

    public String getQuantity(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME_FIGHTS +" where item_id='"+id+"'", null );
        res.moveToFirst();
        String qty=""+res.getString(res.getColumnIndex(FIGHTS_ID));
        res.close();
        return qty;
    }

    public Boolean ifPresent(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME_FIGHTS +" where item_id='"+id+"'", null );
        int n=res.getCount();
        res.close();
        if(n>0)
            return true;
        else
            return false;
    }

    public JSONArray getAllItems()
    {
        JSONArray jsonArray=new JSONArray();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME_FIGHTS, null );
        res.moveToFirst();
        int i=0;
        while(!res.isAfterLast()){
            JSONObject obj = new JSONObject();
            try {
                obj.put("fight_id",res.getString(res.getColumnIndex(FIGHTS_ID) ));
                jsonArray.put(i++, obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            res.moveToNext();
        }
        res.close();
        return jsonArray;
    }
}