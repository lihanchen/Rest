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
	private int period, interval;

	public TimeModel(int period, int interval) {
		this.period = period * 60;
		this.interval = interval * 60;
		this.records = new ArrayList<Record>(30);
		lastTime = Calendar.getInstance();
	}

	public ArrayList<Record> getRecords() {
		return records;
	}

	public int startRest() throws Exception {
		if (records.size() % 2 == 0) throw new Exception("Illegal Rest");
		Calendar current = Calendar.getInstance();
		int playingTime = difference(current, lastTime);
		records.add(new Record(lastTime, playingTime));
		lastTime = current;
		if (playingTime <= interval)
			return period;
		else
			return period * playingTime / interval / 60;
	}

	public int stopRest() throws Exception {
		if (records.size() % 2 == 1) throw new Exception("Illegal Stop Rest");
		Calendar current = Calendar.getInstance();
		int restingTime = difference(current, lastTime);
		records.add(new Record(lastTime, restingTime));
		lastTime = current;
		if (restingTime >= period)
			return interval;
		else
			return interval * restingTime / period / 60;
	}

	public int keepPlaying() throws Exception {
		lastTime = records.get(records.size() - 1).time;
		records.remove(records.size() - 1);
		return 5;
	}

	private int difference(Calendar cal1, Calendar cal2) {
		int difference = 0;
		difference += cal1.get(Calendar.SECOND) - cal2.get(Calendar.SECOND);
		difference += 60 * (cal1.get(Calendar.MINUTE) - cal2.get(Calendar.MINUTE));
		difference += 3600 * (cal1.get(Calendar.HOUR_OF_DAY) - cal2.get(Calendar.HOUR_OF_DAY));
		if (cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH)) difference += 86400;
		return difference;
	}

}
