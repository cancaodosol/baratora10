package baratora10;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFile {
	public static String readAll(final String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
	}
	public static String readSql(final String SqlFileName) throws IOException {
		String path = "../workspace/baratora10/WebContent/SQL/" + SqlFileName;
		return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
	}
}
