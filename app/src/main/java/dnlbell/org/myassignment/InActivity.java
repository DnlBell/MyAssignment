package dnlbell.org.myassignment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class InActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);

        Bundle profileBundle = new Bundle();
        profileBundle.putString("userName",getIntent().getStringExtra("userName"));
        profileBundle.putInt("age",getIntent().getIntExtra("age",0));
        profileBundle.putString("occupation",getIntent().getStringExtra("occupation"));
        profileBundle.putString("description", getIntent().getStringExtra("description"));

        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(profileBundle);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.inView,profileFragment, "profileFragment");
        transaction.commit();
    }
}