package ch.rhj.images;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import net.sf.image4j.codec.ico.ICOEncoder;

public class Main {

	private final static Path DIRECTORY = createDirectories(Paths.get("target", "images", "logos"));

	private final static String LOGO_PNG_FORMAT = "logo@%1$d.png";

	private final static String LOGO_IMG_STYLE = "padding: 1px; border: 1px solid black;";
	private final static String LOGO_IMG_FORMAT = "<img src='%1$s' style='%2$s' />";

	private final static Color MIDNIGHT_BLUE = new Color(25, 25, 112, 255);
	private final static String FONT_NAME = "Neue Haas Grotesk Text Pro";

	// ----------------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {

		CreateLogo createLogo = new CreateLogo("RJ", MIDNIGHT_BLUE, Color.WHITE, font());
		List<BufferedImage> logos = sizes().mapToObj(createLogo).collect(toList());

		logos.forEach(Main::writeLogo);
		writeLogoPreview();

		writeIco(logos);
	}

	// ----------------------------------------------------------------------------------------------------------------

	private static IntStream sizes() {

		return IntStream.of(128, 64, 48, 32, 24, 16);
	}

	private static Font font() {

		return Stream.of(getLocalGraphicsEnvironment().getAllFonts()) //
				.filter(font -> font.getName().equals(FONT_NAME)) //
				.findFirst().orElse(null);
	}

	// ----------------------------------------------------------------------------------------------------------------

	private static void writeIco(List<BufferedImage> logos) {

		try {

			int[] bpp = new int[logos.size()];
			boolean[] compress = new boolean[logos.size()];
			Path path = DIRECTORY.resolve("favicon.ico");

			Arrays.fill(bpp, 32);
			Arrays.fill(compress, true);

			ICOEncoder.write(logos, bpp, compress, path.toFile());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	private static void writeLogoPreview() {

		String html = sizes() //
				.mapToObj(size -> String.format(LOGO_PNG_FORMAT, size)) //
				.map(src -> String.format(LOGO_IMG_FORMAT, src, LOGO_IMG_STYLE)) //
				.collect(joining("\r\n"));

		writeString(html, DIRECTORY.resolve("logos.html"));
	}

	// ----------------------------------------------------------------------------------------------------------------

	private static void writeLogo(BufferedImage image) {

		try {

			String name = String.format(LOGO_PNG_FORMAT, image.getWidth());
			Path target = DIRECTORY.resolve(name);

			Files.deleteIfExists(target);
			ImageIO.write(image, "PNG", target.toFile());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void writeString(String string, Path target) {

		try {

			Files.deleteIfExists(target);
			Files.writeString(target, string, UTF_8);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	private static Path createDirectories(Path path) {

		try {

			return Files.createDirectories(path);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
