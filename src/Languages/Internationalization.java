package Languages;

import java.util.HashMap;

public class Internationalization {
	HashMap<String, String> map = new HashMap<>();

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
		this.map.put("cancel", "取消");
		this.map.put("showWindow", "显示主窗口");
		this.map.put("startRest", "立即开始休息");
		this.map.put("skipRest", "跳过休息");
		this.map.put("ContinueResting", "继续休息");
		this.map.put("StartTime", "开始时间");
		this.map.put("Action", "动作");
		this.map.put("Duration", "时长");
		this.map.put("UseComputer", "使用电脑");
		this.map.put("Rest", "休息");
		this.map.put("Minutes", "分钟");
		this.map.put("HomePane", "主页");
		this.map.put("HistoryPane", "休息记录");
		this.map.put("GamePane", "游戏历史");
		this.map.put("labelEvery", "每隔");
		this.map.put("butSet", "设定");
		this.map.put("labelRemain", "距下次休息还有");
		this.map.put("checkFullScreen", "全屏休息");
		this.map.put("checkCloseMonitor", "关闭显示器");
		this.map.put("checkAutoRest", "自动休息");
		this.map.put("butStop", "停止休息");
		this.map.put("butPause", "暂停休息");
		this.map.put("Game", "游戏");
		this.map.put("Time", "时间");
		this.map.put("Hour", "小时");

	}

	public String getString(String name) {
		String ret = this.map.get(name);
		return ret == null ? "" : ret;
	}
}