package dnlbell.org.myassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Integer> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        ArrayAdapter<Integer> adapter;

        for (int i = 1900; i <= thisYear; i++)
        {
            years.add(i);
        }
        Collections.reverse(years);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);

        Spinner spinYear = findViewById(R.id.year);
        spinYear.setAdapter(adapter);


    }
}
