package dnlbell.org.myassignment;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import dnlbell.org.myassignment.Entity.Settings;
import dnlbell.org.myassignment.settingsDao;

@Database(entities = {Settings.class}, version = 1, exportSchema = false)
public abstract class AppDb extends RoomDatabase{
    public abstract settingsDao settingsDao();
}
