package dnlbell.org.myassignment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    TextView userName, occupation, description;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);

        userName = profileView.findViewById(R.id.userName);
        occupation = profileView.findViewById(R.id.occupation);
        description = profileView.findViewById(R.id.description);

        userName.setText(this.getArguments().getString("userName") + ", " + this.getArguments().getInt("age"));
        occupation.setText(this.getArguments().getString("occupation"));
        description.setText(this.getArguments().getString("description"));

        return profileView;
    }

}