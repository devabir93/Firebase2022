package ucas.edu.android.firebase2022.ui.Model;

import com.google.gson.annotations.SerializedName;

public class Dinar {
    @SerializedName("dollar")
    double dollar;
    @SerializedName("shiekl")
    double shiekl;

    public Dinar() {
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
        return "Dinar{" +
                "dollar=" + dollar +
                ", shiekl=" + shiekl +
                '}';
    }
}
