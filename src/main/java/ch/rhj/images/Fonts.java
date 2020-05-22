package ch.rhj.images;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;

import java.awt.GraphicsEnvironment;
import java.util.stream.Stream;

public interface Fonts {

	public static boolean hasFont(GraphicsEnvironment graphicsEnvironment, String name) {

		return Stream.of(graphicsEnvironment.getAllFonts()) //
				.map(font -> font.getName()) //
				.filter(n -> n.equals(name)) //
				.findFirst().isPresent();
	}

	public static boolean hasFont(String name) {

		return hasFont(getLocalGraphicsEnvironment(), name);
	}
}
