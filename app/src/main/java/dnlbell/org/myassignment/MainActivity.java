package dnlbell.org.myassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private EditText name, email, userName;
    private Button submit;
    private Spinner month,day,year;
    private TextView errorText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Integer> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        ArrayAdapter<Integer> yearsAdapter;

        for (int i = 1900; i <= thisYear; i++)
        {
            years.add(i);
        }
        Collections.reverse(years);

        yearsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        Spinner spinYear = findViewById(R.id.year);
        spinYear.setAdapter(yearsAdapter);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        userName = findViewById(R.id.userName);
        month = findViewById(R.id.month);
        day = findViewById(R.id.day);
        submit = findViewById(R.id.submit);
        errorText = findViewById(R.id.error);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //errorText.setText("Button clicked");

                //TODO: validate input, bundle successful info and pass to SuccessActivity

            }
        });


    }

}
