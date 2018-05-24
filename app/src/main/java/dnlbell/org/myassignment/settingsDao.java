package dnlbell.org.myassignment;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import dnlbell.org.myassignment.Entity.Settings;

import java.util.List;

@Dao
public interface settingsDao {
    @Query( "SELECT * FROM Settings")
    List<Settings> getSettings();

    @Update
    void updateSettings(Settings... settings);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Settings... settings);

    @Delete
    void delete(Settings... settings);

}

