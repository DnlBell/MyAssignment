package dnlbell.org.myassignment;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.content.Context;

public class AppDbSingleton {

    private static AppDb db;

    public static AppDb getDatabase(Context context){
        if(db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                    AppDb.class, "myDatabase")
                    .build();
        }
        return db;
    }
}
