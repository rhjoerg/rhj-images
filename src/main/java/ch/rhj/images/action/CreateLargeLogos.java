package ch.rhj.images.action;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.lang.String.format;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import ch.rhj.images.Fonts;

public class CreateLargeLogos implements Runnable {

	public final static String FONT_NAME = "Neue Haas Grotesk Text Pro";

	public final static Color TRANSPARENT = new Color(0, 0, 0, 0);
	public final static Color MIDNIGHT = new Color(25, 25, 112, 255);
	public final static Color WHITE = new Color(255, 255, 255, 255);

	public final static String INITIALS = "RJ";

	private final Path directory;

	private final Font font = Fonts.getFont(FONT_NAME);

	public CreateLargeLogos(Path directory) {

		this.directory = directory;
	}

	public static CreateLargeLogos createLargeLogos(Path directory) {

		return new CreateLargeLogos(directory);
	}

	@Override
	public void run() {

		try {

			System.out.println("creating large logos");

			if (checkFont()) {

				Files.createDirectories(directory);
				IntStream.of(256, 128, 64, 48).forEach(this::createLogo);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void createLogo(int size) {

		try {

			BufferedImage image = new BufferedImage(size, size, TYPE_INT_ARGB);
			Path path = directory.resolve(format("logo@%1$d.png", size));

			paintLogo(image, size);

			Files.deleteIfExists(path);
			ImageIO.write(image, "PNG", path.toFile());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void paintLogo(BufferedImage image, int size) {

		Graphics2D graphics = createGraphics(image, size);

		paintBackground(graphics, size);
		paintInitials(graphics, size);

		graphics.dispose();
	}

	private Graphics2D createGraphics(BufferedImage image, int size) {

		Graphics2D graphics = image.createGraphics();

		if (size > 48) {

			graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		}

		return graphics;
	}

	private void paintBackground(Graphics2D graphics, int size) {

		int arcWH = size / 2;

		graphics.setColor(TRANSPARENT);
		graphics.fillRect(0, 0, size, size);

		graphics.setColor(MIDNIGHT);
		graphics.fillRoundRect(0, 0, size, size, arcWH, arcWH);
	}

	private void paintInitials(Graphics2D graphics, int size) {

		float fullScale = size;
		Font fullFont = font.deriveFont(fullScale);
		FontMetrics fullMetrics = graphics.getFontMetrics(fullFont);
		int fullWith = fullMetrics.stringWidth(INITIALS);
		float scale = fullScale * size * 6 / 8 / fullWith;
		Font scaledFont = font.deriveFont(scale);

		graphics.setFont(scaledFont);
		graphics.setColor(WHITE);

		FontRenderContext fontRenderContext = graphics.getFontRenderContext();
		TextLayout layout = new TextLayout(INITIALS, scaledFont, fontRenderContext);
		Rectangle bounds = layout.getBounds().getBounds();

		graphics.drawString(INITIALS, (size - bounds.width) / 2 - bounds.x, (size + bounds.height) / 2);
	}

	private boolean checkFont() {

		if (font != null) {
			return true;
		}

		System.out.println(format("skipping, font '%1$s' not available", FONT_NAME));

		return false;
	}
}
