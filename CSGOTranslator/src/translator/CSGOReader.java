package translator;

import java.io.BufferedReader;
import java.io.IOException;

public class CSGOReader {
	private BufferedReader reader;

	public CSGOReader(BufferedReader _reader) {
		reader = _reader;
	}

	public String read() throws IOException {
		
		String line = "";
		
		if((line = reader.readLine()) != null) 
			return line;
		
		
		return null;
	}
	
	public boolean isStop(String line) {
		if(line.equals("Stop ")) {
			return true;
		}
	return false;
	}
	
	public void close() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public boolean isUserText(String line, String Username) {
		if (line.contains(" :") && !line.contains("!") && !line.contains("pkts")
				&& !line.contains(Username))
			return true;
		return false;
	}

	public boolean isHelp(String line) {
		if (line.contains("!help") && line.contains("Unknown command"))
			return true;
		return false;
	}

	public boolean isLang(String line) {
		if (line.contains("!lang") && line.contains("Unknown command")) 
			return true;
		return false;
	}

	public boolean isName(String line) {
		if (line.contains("\"name\"")) 
			return true;
		return false;
	}

	public boolean isKey(String line) {
		if (line.contains("!key") && !line.contains("command"))
			return true;
		return false;
	}
	
	public boolean isAll(String line) {
		if (line.contains("!saya") && !line.contains("Unknown command"))
			return true;
		return false;
	}
	
	public boolean isTeam(String line) {
		if (line.contains("!sayt") && !line.contains("Unknown command"))
			return true;
		return false;
	}

}
