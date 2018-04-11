package oorg.cokcc.christourkingcommunitychurch.model;

import java.util.ArrayList;

/**
 * Created by LabUser on 4/10/2018.
 */

public class Events
{
    private ArrayList<Today> today;

    public ArrayList<Today> getToday() { return this.today; }

    public void setToday(ArrayList<Today> today) { this.today = today; }

    private ArrayList<Future> future;

    public ArrayList<Future> getFuture() { return this.future; }

    public void setFuture(ArrayList<Future> future) { this.future = future; }
}

