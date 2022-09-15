package translator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Properties;

public class DeepLAPI {
	private String authenticationKey = "";
	private String domain = "https://api-free.deepl.com/v2";
	private URL url;
	private Chat chat;

	public DeepLAPI() throws IOException {
		chat = new Chat();
	}

	public String getLanguages() throws IOException {

		String link = domain + "/languages?type=target&auth_key=" + authenticationKey;

		return openConnection(link).readLine();
	}

	public String getTranslation(String rawText) throws IOException {

		chat.setRawText(rawText);
		chat.getInformation();

		@SuppressWarnings("deprecation")
		String link = domain + "/translate?auth_key=" + authenticationKey + "&text=" + URLEncoder.encode(chat.getText())
				+ "&target_lang=" + chat.getLanguage();
		
		try {
			return openConnection(link).readLine();
		} catch (IOException ex) {
			setAuthenticationKey(null);
			return null;
		}
	}

	private BufferedReader openConnection(String link) throws IOException {

		url = new URL(link);

		URLConnection con = url.openConnection();
		return new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF8"));

	}

	public void saveAuthenticationKey() throws IOException {
		Properties p = new Properties();
		p.setProperty("AuthenticationKey", this.authenticationKey);
		p.store(new FileWriter("DeepLKey.properties"), "");
	}

	public void readAuthenticationKey() throws FileNotFoundException, IOException {
		Properties p = new Properties();
		try {
			p.load(new FileReader("DeepLKey.properties"));
		} catch (FileNotFoundException ex) {
			saveAuthenticationKey();
		}
		setAuthenticationKey(p.getProperty("AuthenticationKey"));
	}

	public Chat getChat() {
		return chat;
	}

	public String getAuthenticationKey() {
		return authenticationKey;
	}

	public void setAuthenticationKey(String authenticationKey) {
		this.authenticationKey = authenticationKey;
	}

}
