package edu.jmu.wylcon.ui.personc;

import java.util.ArrayList;

public class PhoneNumber{
    private ArrayList<String> phL = new ArrayList<>();

    public PhoneNumber(ArrayList<String> phL) {
        this.phL = phL;
    }

    public ArrayList<String> getPhL() {
        return phL;
    }

    public void setPhL(ArrayList<String> phL) {
        this.phL = phL;
    }

    @Override
    public String toString() {
        return "PhoneNumber{"
                + phL +
                '}';
    }
}
