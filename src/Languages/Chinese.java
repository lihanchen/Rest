package Languages;

public class Chinese extends Internationalization {
	static Internationalization theInstance;

	public Chinese() {
	}

	public static Internationalization getInstance() {
		if (theInstance == null) {
			theInstance = new Internationalization();
		}

		return theInstance;
	}
}
