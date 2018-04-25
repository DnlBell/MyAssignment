package dnlbell.org.myassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView userName, occupation, description;
    Button returnToForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();

        userName = findViewById(R.id.userName);
        occupation = findViewById(R.id.occupation);
        description = findViewById(R.id.description);
        returnToForm = findViewById(R.id.returnToForm);

        String userNameAge = intent.getStringExtra("userName") + ", " + intent.getStringExtra("age");

        userName.setText(userNameAge);
        occupation.setText(intent.getStringExtra("occupation"));
        description.setText(intent.getStringExtra("Description"));



        returnToForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
    }

    public void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

