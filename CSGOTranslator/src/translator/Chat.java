package translator;
import java.io.IOException;

public class Chat {
	private String rawText;
	private String text;
	private String language;

	public Chat() throws IOException {
		rawText = "";
		text = "";
		language = "";
	}

	public void getInformation() throws IOException {

		if (!rawText.contains("!saya") && !rawText.contains("!sayt")) {
			text = rawText;
			language = "EN-US";
		} else {
			
			text = rawText.substring(9,rawText.length()-1);
			language = rawText.split(" ", 3)[1];
			
		}
	}

	public String getRawText() {
		return rawText;
	}

	public void setRawText(String rawText) {
		this.rawText = rawText;
	}

	public String getText() {
		return text;
	}

	public void setText(String _text) {
		text = _text;
	}

	public String getLanguage() {
		return language;
	}
}
