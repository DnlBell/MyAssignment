package dnlbell.org.myassignment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import dnlbell.org.myassignment.ViewModel.MatchesViewModel;
import dnlbell.org.myassignment.Model.Match;

public class MatchesFragment extends Fragment {

    private static MatchesViewModel viewModel = new MatchesViewModel();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        Bundle bundle = new Bundle();
        viewModel.getMatches(
                (ArrayList<Match> matches) -> {

                    bundle.putParcelableArrayList("matches", matches);
                    ContentAdapter adapter = new ContentAdapter(bundle);
                    recyclerView.setAdapter(adapter);

                }

        );

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public ImageButton likeButton;
        public Match match;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            picture = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_title);
            description = itemView.findViewById(R.id.card_text);
            likeButton = itemView.findViewById(R.id.like_button);

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    CharSequence text = "You Liked " + new StringBuilder().append(name.getText()).append("!").toString();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    likeButton.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                    match.liked = true;
                    viewModel.updateMatch(match);
                }

            });

        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 6;
        private ArrayList<Match> myMatches;

        public ContentAdapter(Bundle matches) {

            myMatches = matches.getParcelableArrayList("matches");
            //a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //setup picture
            holder.match = myMatches.get(position);
            String url = holder.match.imageUrl;
            Picasso.get().load(url).into(holder.picture);
            holder.name.setText(holder.match.name);
            boolean liked = holder.match.liked;
            if (liked) {
                holder.likeButton.setColorFilter(R.color.red);
            }else{
                holder.likeButton.setColorFilter(R.color.dark_grey);
            }
        }


        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}

