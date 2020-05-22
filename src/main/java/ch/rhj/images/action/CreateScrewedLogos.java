package ch.rhj.images.action;

import static java.lang.String.format;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class CreateScrewedLogos implements Runnable {

	public final static String SVG_NAME = "screwed-logo.svg";

	private final Path directory;

	public CreateScrewedLogos(Path directory) {

		this.directory = directory;
	}

	public static CreateScrewedLogos createScrewedLogos(Path directory) {

		return new CreateScrewedLogos(directory);
	}

	@Override
	public void run() {

		try {

			System.out.println("creating screwed logos");

			Files.createDirectories(directory);

			exportSvg();
			exportLogos();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void exportLogos() {

		IntStream.of(256, 128, 64, 48, 32, 24, 16).forEach(this::exportLogo);
	}

	private void exportLogo(int size) {

		try {

			ClassLoader classLoader = getClass().getClassLoader();
			String name = format("logo@%1$d.png", size);
			Path srcPath = Paths.get(classLoader.getResource("screwed-logos/" + name).toURI());
			Path dstPath = directory.resolve(name);

			Files.deleteIfExists(dstPath);
			Files.copy(srcPath, dstPath);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void exportSvg() throws Exception {

		ClassLoader classLoader = getClass().getClassLoader();
		Path srcPath = Paths.get(classLoader.getResource("screwed-logos/" + SVG_NAME).toURI());
		Path dstPath = directory.resolve(SVG_NAME);

		Files.deleteIfExists(dstPath);
		Files.copy(srcPath, dstPath);
	}
}
