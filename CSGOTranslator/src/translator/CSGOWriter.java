package translator;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CSGOWriter {
	private DataOutputStream writer;
	private String command;

	public CSGOWriter(DataOutputStream _writer) {
		writer = _writer;
	}

	public void changeTextColorToRed() throws IOException, InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
		command = "log_color Console FF0000FF";
		write("");
		
	}
	
	public void changeTextColorToPink() throws IOException, InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
		command = "log_color Console FFC0CBFF";
		write("");
		
	}
	
	public void changeTextColorToNormal() throws IOException, InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
		command = "log_color Console 00000000";
		write("");
		
	}
	
	public void changeTextColorToOrange() throws IOException, InterruptedException{	
		TimeUnit.MILLISECONDS.sleep(100);
		command = "log_color Console FFA500FF";
			write("");
	}
	
	
	public void getName() throws IOException {
		command = "";
		write("name");
	}
	
	public void writeAll(String text) throws IOException {
		command = "say ";
		write(text);
	}

	public void writeTeam(String text) throws IOException {
		command = "say_team ";
		write(text);
	}

	public void writeConsole(String text) throws IOException, InterruptedException {
		command = "echo ";
		write(text);

	}

	public void writeSameManyTimes(int i, String text, String _command) throws IOException {
		
		for(int y = 0; y < i; y++) {
			command = _command;
			write(text);
		}
		
		
	}
	
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void writeInfo() throws IOException, InterruptedException {
		
		changeTextColorToPink();
		
		String[] commands = {
		
			"CSGO Translator is connected!......",
			"type !help for more informations"
				
		};
		
		for(String c : commands) {
			command = "echo ";
			write(c);
		}
			
		changeTextColorToNormal();
		
	}
	
	public void writeWrongKey() throws InterruptedException, IOException {
		
		changeTextColorToRed();
		
		String[] commands = {
		
			"Your Authentication Key is either not set or incorrect.",
			"In order to fix this problem update your Authentication Key with the command echo !key YOUR_KEY",
			"command example: echo !key 12345678-1234-1234-1234-123456789abc:fx",
			"Obtain your Key at DeepL instruction at: ",
			"-> support.deepl.com/hc/de/articles/360021200939-DeepL-API-Free"
				
		};
		
		for(String c : commands) {
			command = "echo ";
			write(c);
		}
			
		changeTextColorToNormal();
		
		
	}
	
	public void writeIntro() throws IOException, InterruptedException{
		
		changeTextColorToOrange();
		
		String[] ASCII = {
		
				"                                                                                                                        ",
				  "  #####    #####    #####   #######      #######                                                                        ",
				  " #     #  #     #  #     #  #     #         #     #####     ##    #    #   ####   #         ##    #####   ####   #####  ",
				  " #        #        #        #     #         #     #    #   #  #   ##   #  #       #        #  #     #    #    #  #    # ",
				  " #         #####   #  ####  #     #         #     #    #  #    #  # #  #   ####   #       #    #    #    #    #  #    # ",
				  " #              #  #     #  #     #         #     #####   ######  #  # #       #  #       ######    #    #    #  #####  ",
				  " #     #  #     #  #     #  #     #         #     #   #   #    #  #   ##  #    #  #       #    #    #    #    #  #   #  ",
				  "  #####    #####    #####   #######         #     #    #  #    #  #    #   ####   ######  #    #    #     ####   #    # ",
				  "                                                                                                                        ",
				
		};
		
		for(String a : ASCII) {
			command = "echo ";
			write(a.replace(" ",  "."));
		}
			
		
		
	}
	
	public void writeHelp() throws IOException, InterruptedException {
		
		changeTextColorToOrange();
		
		String[] ASCII = {
		
				".##.....##..######..########.########.....##.....##....###....##....##.##.....##....###....##.......",
				".##.....##.##....##.##.......##.....##....###...###...##.##...###...##.##.....##...##.##...##.......",
				".##.....##.##.......##.......##.....##....####.####..##...##..####..##.##.....##..##...##..##.......",
				".##.....##..######..######...########.....##.###.##.##.....##.##.##.##.##.....##.##.....##.##.......",
				".##.....##.......##.##.......##...##......##.....##.#########.##..####.##.....##.#########.##.......",
				".##.....##.##....##.##.......##....##.....##.....##.##.....##.##...###.##.....##.##.....##.##.......",
				"..#######...######..########.##.....##....##.....##.##.....##.##....##..#######..##.....##.########."
				
		};
		
		String[] commands = {
				"The CSGO translator translates all ingame messages from other players and writes it into the console.",
				"all translated messages are marked orange",
				"if you want to write in another language use the command echo !saya for all chat and echo !sayt for team chat",
				"in addition, the targeted language must be specified here are two examples:",
				"Console input: echo !saya es Hello, World ->Allchat output is: Hola mundo [es stands for spanish]",
				"Console input: echo !sayt de Hello, World! ->Teamchat output is: Hallo, Welt! [de stands for German]",
				"to see all possible languages type !lang",
				"to close the translator type echo Stop"
		};
		
		for(String c : ASCII) {
			command = "echo ";
			write(c);
		}
		
		changeTextColorToPink();
		
		for(String c : commands) {
			command = "echo ";
			write(c);
		}
			
		changeTextColorToNormal();
		
		
	}
	
	
	private void write(String text) throws IOException {

		command += text;
		command = (command + "\n").replace("\0xFF", "\0xFF\0xFF");

		byte[] arr = command.getBytes("UTF8");

		writer.write(arr, 0, arr.length);
		writer.flush();
	}

}
