package ucas.edu.android.firebase2022.ui.Model;

import com.google.gson.annotations.SerializedName;

public class Dollar {
    @SerializedName("dinar")
    double dinar;
    @SerializedName("shiekl")
    double shiekl;

    public Dollar() {
    }

    public double getDinar() {
        return dinar;
    }

    public void setDinar(double dinar) {
        this.dinar = dinar;
    }

    public double getShiekl() {
        return shiekl;
    }

    public void setShiekl(double shiekl) {
        this.shiekl = shiekl;
    }

    @Override
    public String toString() {
        return "Dollar{" +
                "dinar='" + dinar + '\'' +
                ", shiekl='" + shiekl + '\'' +
                '}';
    }
}
