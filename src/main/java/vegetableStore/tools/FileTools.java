package vegetableStore.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTools extends MyLogger {

	public boolean checkIfFileExists(String fileName) {

		Path path = Paths.get(fileName);

		return Files.exists(path);
	}

	public void deleteFile(String fileName) {

		Path path = Paths.get(fileName);

		try {

			Files.delete(path);

		} catch (IOException e) {

			log.error("IOException for file " + fileName);
			e.printStackTrace();
		}

	}

}
