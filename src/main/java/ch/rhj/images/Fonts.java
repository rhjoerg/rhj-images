package ch.rhj.images;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.util.stream.Stream;

public interface Fonts {

	public static Stream<Font> availableFonts(GraphicsEnvironment graphicsEnvironment) {

		return Stream.of(graphicsEnvironment.getAllFonts());
	}

	public static Stream<Font> availableFonts() {

		return availableFonts(getLocalGraphicsEnvironment());
	}

	public static Stream<String> availableFontNames(GraphicsEnvironment graphicsEnvironment) {

		return availableFonts(graphicsEnvironment).map(Font::getName);
	}

	public static Stream<String> availableFontNames() {

		return availableFonts().map(Font::getName);
	}

	public static boolean hasFont(GraphicsEnvironment graphicsEnvironment, String name) {

		return availableFontNames(graphicsEnvironment).filter(n -> n.equals(name)).findFirst().isPresent();
	}

	public static boolean hasFont(String name) {

		return hasFont(getLocalGraphicsEnvironment(), name);
	}

	public static Font getFont(GraphicsEnvironment graphicsEnvironment, String name) {

		return availableFonts().filter(font -> font.getName().equals(name)).findFirst().orElse(null);
	}

	public static Font getFont(String name) {

		return getFont(getLocalGraphicsEnvironment(), name);
	}

	public static Font scaleFont(Graphics2D graphics, Font font, String string, int width) {

		float tempScale = width * 4;
		Font tempFont = font.deriveFont(tempScale);
		FontMetrics tempMetrics = graphics.getFontMetrics(tempFont);
		int tempWidth = tempMetrics.stringWidth(string);

		float scale = (tempScale * width) / tempWidth;

		return font.deriveFont(scale);
	}
}
