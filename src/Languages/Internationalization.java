package Languages;

import java.util.HashMap;

public class Internationalization {
	HashMap<String, String> map = new HashMap();

	protected Internationalization() {
		this.map.put("title", "休息休息 5.0");
		this.map.put("homeTab", "主页");
		this.map.put("History", "休息历史");
		this.map.put("GameMonitor", "游戏时间");
	}

	public String getString(String name) {
		String ret = (String) this.map.get(name);
		return ret == null ? "" : ret;
	}
}