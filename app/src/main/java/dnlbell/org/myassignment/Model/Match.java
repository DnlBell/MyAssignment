package dnlbell.org.myassignment.Model;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Match implements Parcelable {

    public String uid;
    public String imageUrl;
    public String name;
    public boolean liked;

    public Match() {

    }


    public static final Creator<Match> CREATOR = new Creator<Match>() {

        @Override
        public Match createFromParcel(Parcel in) {
            return new Match();
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeByte((byte) (liked ? 1 : 0));
        dest.writeString(name);
        dest.writeString(uid);
    }
}
