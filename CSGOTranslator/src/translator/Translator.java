package translator;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Translator {

	private Telnet connection;
	private CSGOReader csr;
	private CSGOWriter csw;
	private DeepLAPI api;
	private String username;

	Translator() throws IOException, InterruptedException {
		connection = new Telnet();
		if (connection.getSocket() != null) {
			csr = new CSGOReader(new BufferedReader(new InputStreamReader(connection.getSocket().getInputStream())));
			csw = new CSGOWriter(new DataOutputStream(connection.getSocket().getOutputStream()));
			api = new DeepLAPI();
		}
	}

	public String translate(String line) throws IOException {

		String APIText = this.getApi().getTranslation(line);

		if (APIText == null)
			return "";

		return APIText.split("text\":\"")[1].split("\"")[0].trim();

	}

	private void getLanguages(String APItext) throws IOException, InterruptedException {
		String line = "";

		String[] languages = APItext.split("language\":");

		for (int i = 1; i < languages.length; i++) {
			String[] language = languages[i].split("name\":");

			language[0] = language[0].replace("\"", "").replace(",", "").trim();
			language[1] = language[1].split("\",")[0].replace("\"", "").replace(" ", "").replace("(", "[").replace(")",
					"]");
			
			line = language[0] + "=" + language[1];
		
			csw.writeConsole(line);
			line = "";
		
		}

	}

	public boolean equalTranslation(String translation) {

		if (translation.equals(this.getApi().getChat().getText()))
			return true;

		return false;

	}

	private void skipLines(int i) throws IOException {
		while (i > 0) {
			getCsr().read();
			i--;
		}
	}

	public void isKeyWorking() throws IOException, InterruptedException {

		if (getApi().getAuthenticationKey() != null && translate("H").equals("H")) {
			getCsw().writeInfo();
			getApi().saveAuthenticationKey();
		} else {
			getCsw().writeWrongKey();

		}
	}

	private void close() {
		getCsw().close();
		getConnection().close();
		getCsr().close();

	}

	@SuppressWarnings("unused")
	private void debug(String line, String TextToCheck) {
		System.out.println("line bytes:" + Arrays.toString(line.getBytes()));
		System.out.println("TextToCheck bytes:" + Arrays.toString("Stop ".getBytes()));
	}
	
	public Telnet getConnection() {
		return connection;
	}

	public void setConnection(Telnet connection) {
		this.connection = connection;
	}

	public CSGOReader getCsr() {
		return csr;
	}

	public void setCsr(CSGOReader csr) {
		this.csr = csr;
	}

	public CSGOWriter getCsw() {
		return csw;
	}

	public void setCsw(CSGOWriter csw) {
		this.csw = csw;
	}

	public DeepLAPI getApi() {
		return api;
	}

	public void setApi(DeepLAPI api) {
		this.api = api;
	}

	public String getUsername() throws IOException {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public static void main(String[] args)
			throws IOException, InterruptedException, NumberFormatException, URISyntaxException {

		URI uri = new URI("steam://run/730");

		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(uri);
		}

		Translator translator = new Translator();

		String line = "";
		String text = "";
		String translation = "";
		if (translator.getCsr() != null) {
			translator.getApi().readAuthenticationKey();
			translator.getCsw().getName();
			translator.getCsw().writeIntro();
			translator.isKeyWorking();

			while ((line = translator.getCsr().read()) != null) {
				
				if (translator.getCsr().isUserText(line, translator.getUsername())){

					text = line.split(" :", 2)[1].trim();
					translation = translator.translate(text);
					
					if (!translator.equalTranslation(translation)) {
						translator.getCsw().changeTextColorToOrange();
						translator.getCsw().writeConsole(translation);
						translator.getCsw().changeTextColorToNormal();
					}
				}

				if (translator.getCsr().isHelp(line)) {
					translator.getCsw().writeHelp();
					translator.skipLines(17);
				}

				if (translator.getCsr().isAll(line)) {
					translation = translator.translate(line);

					if (!translator.equalTranslation(translation))
						translator.getCsw().writeAll(translation);

				}

				if (translator.getCsr().isTeam(line)) {
					translation = translator.translate(line);

					if (!translator.equalTranslation(translation))
						translator.getCsw().writeTeam(translation);

				}

				if (translator.getCsr().isLang(line)) {
					translator.getCsw().changeTextColorToPink();
					translator.getLanguages(translator.getApi().getLanguages());
					translator.getCsw().changeTextColorToNormal();
				}

				if (translator.getCsr().isName(line)) {
					translator.setUsername(line.split("= \"", 2)[1].split("\"", 2)[0]);
				}

				if (translator.getCsr().isKey(line)) {
					translator.getApi().setAuthenticationKey(line.split("!key ", 2)[1].replace(" ", ""));
					translator.isKeyWorking();
				}

				if (translator.getCsr().isStop(line)) {
					translator.getCsw().writeConsole("disconnected");
					translator.close();
				}

			}

		}

	}

}
