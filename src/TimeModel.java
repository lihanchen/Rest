import java.util.ArrayList;

public class TimeModel {
    private ArrayList<Integer> minutes;

    public void increaseTime(int increasement) {
        int last = minutes.get(minutes.size() - 1);
        minutes.set(minutes.size() - 1, last + increasement);
    }

    public void addItem(int time) {
        minutes.add(time);
    }
}
