package dnlbell.org.myassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    final static String DATE_FORMAT = "MM-dd-yyy";
    private EditText name, email, userName, occupation, description;
    private Button submit;
    private Spinner month, day, year;
    private TextView errorText, nameFlag, emailFlag, userNameFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the year array populated by having all years since 1900 listed
        ArrayList<Integer> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        ArrayAdapter<Integer> yearsAdapter;

        for (int i = 1900; i <= thisYear; i++) {
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
        occupation = findViewById(R.id.occupation);
        description = findViewById(R.id.description);
        submit = findViewById(R.id.submit);
        errorText = findViewById(R.id.error);
        nameFlag = findViewById(R.id.nameFlag);
        emailFlag = findViewById(R.id.emailFlag);
        userNameFlag = findViewById(R.id.userNameFlag);

        description.setMovementMethod(new ScrollingMovementMethod());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = month.getSelectedItem().toString() + "-" + day.getSelectedItem().toString() + "-" + year.getSelectedItem().toString();
                String errorList = getString(R.string.Error);
                boolean invalid = false;
                errorText.setText("");

                Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText().toString());

                if (name.getText().toString().length() > 32 || name.getText().toString().length() == 0) {
                    errorList += getString(R.string.nameErrorMessage);
                    invalid = true;
                    nameFlag.setText(R.string.flag);
                } else {
                    nameFlag.setText(R.string.empty);
                }
                if (!emailMatcher.find()) {
                    errorList += getString(R.string.emailErrorMessage);
                    emailFlag.setText(R.string.flag);
                    invalid = true;
                } else {
                    emailFlag.setText(R.string.empty);
                }
                if (userName.getText().toString().length() > 32 || userName.getText().toString().length() == 0) {
                    errorList += getString(R.string.userNameErrorMessage);
                    userNameFlag.setText(R.string.flag);
                    invalid = true;
                } else {
                    userNameFlag.setText(R.string.empty);
                }
                if (!isDateValid(date)) {
                    errorList += getString(R.string.invalidDate);
                    invalid = true;
                }
                DateFormat df = new SimpleDateFormat(DATE_FORMAT);
                Calendar birthDate = Calendar.getInstance();

                try{
                birthDate.setTime(df.parse(date));
                    if(getAge(birthDate)<18){
                        errorList += getString(R.string.invalidAge);
                        invalid = true;
                    }
                } catch (Exception e) {
                    errorList += "";
                }

                //check if the input has passed and return any errors if there are any.
                if(invalid){
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText(errorList);
                } else {
                    goToSuccessActivity(userName.getText().toString());
                }


            }
        });


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Read values from the "savedInstanceState"-object and put them in your textView
        errorText.setText(savedInstanceState.getString("errorText"));
        nameFlag.setText(savedInstanceState.getString("nameFlag"));
        emailFlag.setText(savedInstanceState.getString("emailFlag"));
        userNameFlag.setText(savedInstanceState.getString("userNameFlag"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the values you need from your textview into "outState"-object
        outState.putString("errorText", errorText.getText().toString());
        outState.putString("nameFlag", nameFlag.getText().toString());
        outState.putString("emailFlag", emailFlag.getText().toString());
        outState.putString("userNameFlag", userNameFlag.getText().toString());
        super.onSaveInstanceState(outState);
    }

    private static boolean isDateValid(String date) {

        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;


    }

    public static int getAge(Calendar dob) {

        Calendar today = Calendar.getInstance();

        int curYear = today.get(Calendar.YEAR);
        int dobYear = dob.get(Calendar.YEAR);

        int age = curYear - dobYear;


        // if dob is month or day is behind today's month or day
        // reduce age by 1
        int curMonth = today.get(Calendar.MONTH);
        int dobMonth = dob.get(Calendar.MONTH);

        if (dobMonth > curMonth) { // this year can't be counted!
            age--;
        } else if (dobMonth == curMonth) { // same month? check for day
            int curDay = today.get(Calendar.DAY_OF_MONTH);
            int dobDay = dob.get(Calendar.DAY_OF_MONTH);
            if (dobDay > curDay) { // this year can't be counted!
                age--;

            }
        }
        return age;
    }

    public void goToSuccessActivity(String userName){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("userName",userName);
        startActivity(intent);
    }

}
