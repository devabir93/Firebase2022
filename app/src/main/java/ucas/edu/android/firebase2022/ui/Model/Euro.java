package ucas.edu.android.firebase2022.ui.Model;

import com.google.gson.annotations.SerializedName;

public class Euro {
    @SerializedName("dollar")
    double dollar;
    @SerializedName("shiekl")
    double shiekl;

    public Euro() {
    }

    public double getDollar() {
        return dollar;
    }

    public void setDollar(double dollar) {
        this.dollar = dollar;
    }

    public double getShiekl() {
        return shiekl;
    }

    public void setShiekl(double shiekl) {
        this.shiekl = shiekl;
    }


    @Override
    public String toString() {
        return "Euro{" +
                "dollar='" + dollar + '\'' +
                ", shiekl='" + shiekl + '\'' +
                '}';
    }
}
