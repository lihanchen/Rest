import java.util.ArrayList;

public class TimeModel {
    int balance;
    private ArrayList<Integer> minutes;

    public void increaseTime(int increment) {
        int last = minutes.get(minutes.size() - 1);
        minutes.set(minutes.size() - 1, last + increment);
    }

    public void addItem(int time) {
        minutes.add(time);
    }
}
