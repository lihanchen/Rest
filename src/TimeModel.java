import java.util.ArrayList;
import java.util.Calendar;

class Record {
	Calendar time;
	int period;

	public Record(Calendar time, int period) {
		this.time = time;
		this.period = period;
	}
}

public class TimeModel {
	private ArrayList<Record> records;
	private Calendar lastTime;
	private int period, interval, lastActionTime, secondLastActionTime;

	public TimeModel(int interval, int period) {
		this.period = period;
		this.interval = interval;
		this.records = new ArrayList<>(30);
		lastTime = Calendar.getInstance();
		this.lastActionTime = interval;
	}

	public void change(int interval, int period) {
		this.period = period;
		this.interval = interval;
		this.lastActionTime = interval;
	}

	public ArrayList<Record> getRecords() {
		return records;
	}

	public int startRest() {
		if (records.size() % 2 != 0) System.out.println("ERROR1");
		Calendar current = Calendar.getInstance();
		int playingTime = difference(current, lastTime);
		records.add(new Record(lastTime, playingTime / 60));
		lastTime = current;
		secondLastActionTime = lastActionTime;
		if (playingTime <= lastActionTime)
			lastActionTime = period;
		else
			lastActionTime = period * playingTime / lastActionTime;
		return lastActionTime > 0 ? lastActionTime : 1;
	}

	public int stopRest() {
		if (records.size() % 2 == 0) System.out.println("ERROR2");
		Calendar current = Calendar.getInstance();
		int restingTime = difference(current, lastTime);
		if (restingTime < 30) { //Too short, assume keep playing
			lastTime = records.get(records.size() - 1).time;
			records.remove(records.size() - 1);
			lastActionTime = secondLastActionTime;
			return keepPlaying();
		}
		records.add(new Record(lastTime, restingTime / 60));
		lastTime = current;
		if (restingTime >= lastActionTime)
			lastActionTime = interval;
		else
			lastActionTime = interval * restingTime / lastActionTime;
		return lastActionTime > 0 ? lastActionTime : 1;
	}

	public int keepPlaying() {
		return (int) (0.2 * interval);
	}

	private int difference(Calendar cal1, Calendar cal2) {
		int difference = 0;
		difference += cal1.get(Calendar.SECOND) - cal2.get(Calendar.SECOND);
		difference += 60 * (cal1.get(Calendar.MINUTE) - cal2.get(Calendar.MINUTE));
		difference += 3600 * (cal1.get(Calendar.HOUR_OF_DAY) - cal2.get(Calendar.HOUR_OF_DAY));
		if (cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH)) difference += 86400;
		return difference;
	}

	public int getPeriod() {
		return period;
	}

	public int getInterval() {
		return interval;
	}
}
