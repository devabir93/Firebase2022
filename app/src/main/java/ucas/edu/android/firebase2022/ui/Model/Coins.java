package ucas.edu.android.firebase2022.ui.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abeer on 27,December,2022
 */
public class Coins {
    @SerializedName("name")
    String name;
    @SerializedName("sheikl")
    String sheikl;

    String day;

    public Coins() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSheikl() {
        return sheikl;
    }

    public void setSheikl(String sheikl) {
        this.sheikl = sheikl;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Coins{" +
                "name='" + name + '\'' +
                ", sheikl='" + sheikl + '\'' +
                ", day='" + day + '\'' +
                '}';
    }
}

