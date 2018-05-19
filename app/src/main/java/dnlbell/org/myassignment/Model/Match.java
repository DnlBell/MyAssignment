package dnlbell.org.myassignment.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Match implements Parcelable {

    public String uid;
    public String matchImageURL;
    public String named;
    public Boolean liked;

    public Match() {

    }

    public Match (String mId, String mURL, String mNamed, Boolean mLiked){
        this.uid = mId;
        this.matchImageURL = mURL;
        this.named = mNamed;
        this.liked = mLiked;
    }

    public Match(Parcel in) {
        matchImageURL = in.readString();
        liked = in.readByte() != 0;
        named = in.readString();
        uid = in.readString();

    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {

        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("matchId", uid);
        result.put("named", named);
        result.put("liked", liked);
        result.put("matchImageURL", matchImageURL);

        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(matchImageURL);
        dest.writeByte((byte) (liked ? 1 : 0));
        dest.writeString(named);
        dest.writeString(uid);
    }
}
