package Languages;

import java.util.HashMap;

public class Internationalization {
	HashMap<String, String> map = new HashMap();

	protected Internationalization() {
		this.map.put("title", "休息休息 5.0");
		this.map.put("signature", "休息休息 5.0  李翰辰");
		this.map.put("homeTab", "主页");
		this.map.put("History", "休息历史");
		this.map.put("GameMonitor", "游戏时间");
		this.map.put("want exit?", "确定要退出本程序");
		this.map.put("exit", "退出");
		this.map.put("RestNow", "立即休息");
		this.map.put("ResetTime", "重新计时");
		this.map.put("reset?", "确定要重新计时吗");
		this.map.put("errorNumberFormat", "输入无效");
		this.map.put("error", "错误");
	}

	public String getString(String name) {
		String ret = (String) this.map.get(name);
		return ret == null ? "" : ret;
	}
}