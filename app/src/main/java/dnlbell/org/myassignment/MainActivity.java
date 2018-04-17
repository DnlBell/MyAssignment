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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private EditText name, email, userName;
    private Button submit;
    private Spinner month,day,year;
    private TextView errorText,nameFlag,emailFlag,userNameFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the year array populated by having all years since 1900 listed
        ArrayList<Integer> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        ArrayAdapter<Integer> yearsAdapter;

        for (int i = 1900; i <= thisYear; i++)
        {
            years.add(i);
        }
        Collections.reverse(years);

        //adapting the arrayList years into the year spinner
        yearsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        year = findViewById(R.id.year);
        year.setAdapter(yearsAdapter);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        userName = findViewById(R.id.userName);
        month = findViewById(R.id.month);
        day = findViewById(R.id.day);
        submit = findViewById(R.id.submit);
        errorText = findViewById(R.id.error);
        nameFlag = findViewById(R.id.nameFlag);
        emailFlag = findViewById(R.id.emailFlag);
        userNameFlag = findViewById(R.id.userNameFlag);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: validate input, bundle successful info and pass to SuccessActivity
                String errorList = getString(R.string.Error);
                boolean invalid = false;

                Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText().toString());

                if(name.getText().toString().length() > 32 || name.getText().toString().length() == 0) {
                    errorList+=getString(R.string.nameErrorMessage);
                    invalid = true;
                    nameFlag.setText(R.string.flag);
                }else {
                    nameFlag.setText(R.string.empty);
                }
                if(!emailMatcher.find()){
                    errorList+=getString(R.string.emailErrorMessage);
                    emailFlag.setText(R.string.flag);
                    invalid = true;
                }else {
                    emailFlag.setText(R.string.empty);
                }
                if(userName.getText().toString().length() > 32 || userName.getText().toString().length() == 0) {
                    errorList+=getString(R.string.userNameErrorMessage);
                    userNameFlag.setText(R.string.flag);
                    invalid = true;
                }else {
                    nameFlag.setText(R.string.empty);
                }


            }
        });


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Read values from the "savedInstanceState"-object and put them in your textview
        errorText.setText(savedInstanceState.getString("errorText"));
        nameFlag.setText(savedInstanceState.getString("nameFlag"));
        emailFlag.setText(savedInstanceState.getString("emailFlag"));
        userNameFlag.setText(savedInstanceState.getString("userNameFlag"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the values you need from your textview into "outState"-object
        outState.putString("errorText",errorText.getText().toString());
        outState.putString("nameFlag",nameFlag.getText().toString());
        outState.putString("emailFlag",emailFlag.getText().toString());
        outState.putString("userNameFlag",userNameFlag.getText().toString());
        super.onSaveInstanceState(outState);
    }



}
