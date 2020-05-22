package ch.rhj.images.action;

import static org.apache.batik.apps.rasterizer.DestinationType.PNG;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.apache.batik.apps.rasterizer.SVGConverter;

import ch.rhj.images.Fonts;

public class CreateScrewedLogo implements Runnable {

	public final static String SVG_NAME = "screwed-logo.svg";

	private final Path directory;

	public CreateScrewedLogo(Path directory) {

		this.directory = directory;
	}

	public static CreateScrewedLogo createScrewedLogo(Path directory) {

		return new CreateScrewedLogo(directory);
	}

	@Override
	public void run() {

		try {

			if (checkFont()) {

				Files.createDirectories(directory);

				exportSvg();
				renderSvg();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void renderSvg() {

		IntStream.of(256, 128, 64, 48, 32, 24, 16).forEach(this::renderSvg);
	}

	private void renderSvg(int size) {

		try {

			Path srcPath = directory.resolve(SVG_NAME);
			Path dstPath = directory.resolve(String.format("logo@%1$d.png", size));
			SVGConverter converter = new SVGConverter();

			Files.deleteIfExists(dstPath);

			converter.setSources(new String[] { srcPath.toString() });
			converter.setDestinationType(PNG);
			converter.setDst(dstPath.toFile());
			converter.setWidth(size);
			converter.setHeight(size);
			converter.execute();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void exportSvg() throws Exception {

		ClassLoader classLoader = getClass().getClassLoader();
		Path srcPath = Paths.get(classLoader.getResource(SVG_NAME).toURI());
		Path dstPath = directory.resolve(SVG_NAME);

		Files.deleteIfExists(dstPath);
		Files.copy(srcPath, dstPath);
	}

	private boolean checkFont() {

		if (Fonts.hasFont("Consolas")) {
			return true;
		}

		System.out.println("skipping, font 'Consolas' not available");

		return false;
	}
}
