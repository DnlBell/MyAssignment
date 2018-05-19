package dnlbell.org.myassignment.ViewModel;

import dnlbell.org.myassignment.Model.Match;
import dnlbell.org.myassignment.DataModel.FirebaseMatchModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MatchesViewModel {

    private FirebaseMatchModel matchModel;

    public MatchesViewModel() {
        matchModel = new FirebaseMatchModel();
    }

    public void addMatch(Match match){
        matchModel.addMatch(match);
    }

    public void getMatches(Consumer<ArrayList<Match>> responseCallback) {
        matchModel.getMatches(
                (DataSnapshot dataSnapshot) -> {
                    ArrayList<Match> matches = new ArrayList<>();
                    for(DataSnapshot matchSnapshot : dataSnapshot.getChildren()) {
                        Match match = matchSnapshot.getValue(Match.class);
                        assert match != null;
                        match.uid = matchSnapshot.getKey();
                        matches.add(match);
                    }
                    responseCallback.accept(matches);
                },
                (databaseError -> System.out.println("Error reading matches: " + databaseError))
        );
    }

    public void updateMatch(Match match) {
        matchModel.updateMatchById(match);
    }

    public void clear() {
        matchModel.clear();
    }
}
