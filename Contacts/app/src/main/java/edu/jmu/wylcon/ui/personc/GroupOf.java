package edu.jmu.wylcon.ui.personc;


import java.util.ArrayList;

public class GroupOf{
    private ArrayList<String> gpL = new ArrayList<>();

    public GroupOf(ArrayList<String> gpL) {
        this.gpL = gpL;
    }

    public ArrayList<String> getGpL() {
        return gpL;
    }

    public void setGpL(ArrayList<String> gpL) {
        this.gpL = gpL;
    }

    @Override
    public String toString() {
        return "GroupOf{"
                + gpL +
                '}';
    }
}