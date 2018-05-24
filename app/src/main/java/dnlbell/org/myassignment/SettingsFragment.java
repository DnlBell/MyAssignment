package dnlbell.org.myassignment;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import dnlbell.org.myassignment.Entity.Settings;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsFragment extends Fragment {

    public Spinner hour, minute, meridiem, radius, sexuality, gender, rangeLow,rangeHigh;
    public Switch privacy;
    public Button save;
    public TextView settingsError;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View SettingsView = inflater.inflate(R.layout.fragment_settings, container, false);

        hour = SettingsView.findViewById(R.id.hour);
        minute = SettingsView.findViewById(R.id.minute);
        meridiem = SettingsView.findViewById(R.id.meridiem);
        radius = SettingsView.findViewById(R.id.radius);
        sexuality = SettingsView.findViewById(R.id.sexuality);
        gender = SettingsView.findViewById(R.id.gender);
        rangeLow = SettingsView.findViewById(R.id.rangeLow);
        rangeHigh = SettingsView.findViewById(R.id.rangeHigh);
        privacy = SettingsView.findViewById(R.id.privacy);
        save = SettingsView.findViewById(R.id.save);
        settingsError = SettingsView.findViewById(R.id.settingsError);

        ArrayList<Integer> years = getAges(100,18);
        ArrayAdapter<Integer> integerArrayAdapter;

        integerArrayAdapter = new ArrayAdapter<>(SettingsView.getContext(), android.R.layout.simple_spinner_item,years );
        rangeHigh.setAdapter(integerArrayAdapter);
        rangeLow.setAdapter(integerArrayAdapter);


        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                int low = getIntValue(rangeLow);
                int high = getIntValue(rangeHigh);

                if(low <= high) {
                    settingsError.setText("");
                    updateDatabase(v);
                }else {
                    settingsError.setText(R.string.invalidRange);
                }
            }
        });

        new GetSettings(this,0).execute();

        return SettingsView;
    }

    public void updateDatabase(View view) {
        Settings newSettings = new Settings();


        int hour = Integer.parseInt(this.hour.getSelectedItem().toString());
        int minute = Integer.parseInt(this.minute.getSelectedItem().toString());
        int radius = Integer.parseInt(this.radius.getSelectedItem().toString());
        int rangeLow = Integer.parseInt(this.rangeLow.getSelectedItem().toString());
        int rangeHigh = Integer.parseInt(this.rangeHigh.getSelectedItem().toString());

        boolean meridiem;
        if (this.meridiem.getSelectedItem().toString() == "PM") {
            meridiem = true;
        }else {
            meridiem = false;
        }
        boolean privacy = this.privacy.isChecked();

        String sexuality = (String) this.sexuality.getSelectedItem();
        String gender = (String) this.gender.getSelectedItem();

        newSettings.setId(0);
        newSettings.setHour(hour);
        newSettings.setMinute(minute);
        newSettings.setRadius(radius);
        newSettings.setRangeLow(rangeLow);
        newSettings.setRangeHigh(rangeHigh);
        newSettings.setMeridiem(meridiem);
        newSettings.setPrivacy(privacy);
        newSettings.setSexuality(sexuality);
        newSettings.setGender(gender);

        new UpdateSettings(this, newSettings).execute();
    }

    public static int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    public static int getIndex(Spinner spinner, int myInt){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myInt)){
                return i;
            }
        }

        return 0;
    }

    public static int getIntValue(Spinner mySpinner) {
        int value = (int) mySpinner.getSelectedItem();
        return value;
    }

    public static String getStringValue(Spinner mySpinner) {
        String value = mySpinner.getSelectedItem().toString();
        return value;
    }

    public static  ArrayList<Integer> getAges(int max, int min){
        ArrayList<Integer> years = new ArrayList<>();

        for (int i = min; i <= max; i++) {
            years.add(i);
        }
        return years;
    }

    private static class GetSettings extends AsyncTask<Void, Void, Settings> {
        private WeakReference<Fragment> weakFragment;
        private int id;

        public GetSettings(Fragment fragment, int id){
            weakFragment = new WeakReference<>(fragment);
            this.id = id;
        }

        @Override
        protected Settings doInBackground(Void... voids){
            Fragment fragment = weakFragment.get();
            if(fragment == null){
                return null;
            }

            AppDb db = AppDbSingleton.getDatabase(fragment.getContext());

            List<Settings> settings = db.settingsDao().getSettingsById(id);

            if(settings.size() <= 0 || settings.get(0) == null){
                return null;
            }

            return settings.get(0);
        }

        @Override
        protected void onPostExecute(Settings settings) {
            SettingsFragment fragment = (SettingsFragment) weakFragment.get();
            if(settings == null || fragment == null) {
                return;
            }
            fragment.hour.setSelection(getIndex(fragment.hour,settings.getHour()));
            fragment.minute.setSelection(getIndex(fragment.minute,settings.getMinute()));
            fragment.meridiem.setSelected(settings.isMeridiem());
            fragment.radius.setSelection(getIndex(fragment.meridiem,settings.getRadius()));
            fragment.sexuality.setSelection(getIndex( fragment.sexuality,settings.getSexuality()));
            fragment.gender.setSelection(getIndex(fragment.gender,settings.getGender()));
            fragment.rangeLow.setSelection(getIndex(fragment.rangeLow,settings.getRangeLow()));
            fragment.rangeHigh.setSelection(getIndex(fragment.rangeHigh,settings.getRangeHigh()));
            fragment.privacy.setSelected(settings.isPrivacy());
        }
    }

    private static class UpdateSettings extends AsyncTask<Void,Void,Settings> {
        private WeakReference<Fragment> weakFragment;
        private Settings settings;

        public UpdateSettings(Fragment fragment, Settings settings) {
            weakFragment = new WeakReference<>(fragment);
            this.settings = settings;
        }

        @Override
        protected Settings doInBackground(Void... voids) {
            Fragment fragment = weakFragment.get();
            if(fragment == null) {
                return null;
            }

            AppDb db = AppDbSingleton.getDatabase(fragment.getContext());

            db.settingsDao().updateSettings(settings);
            return settings;
        }

        @Override
        protected void onPostExecute(Settings settings) {
            SettingsFragment fragment = (SettingsFragment) weakFragment.get();
            if(settings == null || fragment == null) {
                return;
            }

            fragment.hour.setSelection(getIndex(fragment.hour,settings.getHour()));
            fragment.minute.setSelection(getIndex(fragment.minute,settings.getMinute()));
            fragment.meridiem.setSelected(settings.isMeridiem());
            fragment.radius.setSelection(getIndex(fragment.meridiem,settings.getRadius()));
            fragment.sexuality.setSelection(getIndex( fragment.sexuality,settings.getSexuality()));
            fragment.gender.setSelection(getIndex(fragment.gender,settings.getGender()));
            fragment.rangeLow.setSelection(getIndex(fragment.rangeLow,settings.getRangeLow()));
            fragment.rangeHigh.setSelection(getIndex(fragment.rangeHigh,settings.getRangeHigh()));
            fragment.privacy.setSelected(settings.isPrivacy());

        }
    }


}

