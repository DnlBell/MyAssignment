package dnlbell.org.myassignment;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;

import dnlbell.org.myassignment.Entity.Settings;

import java.lang.ref.WeakReference;
import java.util.List;

public class SettingsFragment extends Fragment {

    public Spinner hour, minute, meridiem, radius, sexuality, gender, rangeLow,rangeHigh;
    public Switch privacy;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View SettingsView = inflater.inflate(R.layout.fragment_settings, container, false);




        return SettingsView;
    }

}
