package ch.rhj.images;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.function.IntFunction;

public class CreateLogo implements IntFunction<BufferedImage> {

	private final static int CRITICAL_SIZE = 40;

	private final String initials;
	private final Color backColor;
	private final Color foreColor;
	private final Font font;

	public CreateLogo(String initials, Color backColor, Color foreColor, Font font) {

		this.initials = initials;
		this.backColor = backColor;
		this.foreColor = foreColor;
		this.font = font;
	}

	@Override
	public BufferedImage apply(int size) {

		BufferedImage image = new BufferedImage(size, size, TYPE_INT_ARGB);
		Graphics2D graphics = createGraphics(image, size);

		paint(graphics, size);
		graphics.dispose();

		return image;
	}

	private void paint(Graphics2D graphics, int size) {

		paintBackground(graphics, size);
		paintInitials(graphics, size);
	}

	private void paintBackground(Graphics2D graphics, int size) {

		graphics.setColor(backColor);

		if (size >= CRITICAL_SIZE) {
			paintLargeBackground(graphics, size);
		} else {
			paintSmallBackground(graphics, size);
		}
	}

	private void paintLargeBackground(Graphics2D graphics, int size) {

		int arcWH = size / 2;

		graphics.fillRoundRect(0, 0, size, size, arcWH, arcWH);
	}

	private void paintSmallBackground(Graphics2D graphics, int size) {

		int cornerWidth = size / 4;
		double cx = cornerWidth;
		double cy = cornerWidth;
		double radius = cornerWidth;

		for (int x = 0; x < cornerWidth; ++x) {

			int y = 0;
			double px = x + 0.5;

			for (; y < cornerWidth; ++y) {

				double py = y + 0.5;
				double dx = px - cx;
				double dy = py - cy;
				double d = Math.sqrt(dx * dx + dy * dy);

				if (d < radius)
					break;
			}

			int h = size - 2 * y;

			graphics.fillRect(x, y, 1, h);
			graphics.fillRect(size - x - 1, y, 1, h);
		}

		graphics.fillRect(cornerWidth, 0, size - 2 * cornerWidth, size);
	}

	private void paintInitials(Graphics2D graphics, int size) {

		int w = initialsWidth(size);
		Font font = scaleFont(graphics, w);
		FontRenderContext fontRenderContext = graphics.getFontRenderContext();
		TextLayout layout = new TextLayout(initials, font, fontRenderContext);
		Rectangle bounds = layout.getBounds().getBounds();
		int x = (size - bounds.width + 1) / 2 - bounds.x;
		int y = (size + bounds.height) / 2;

		System.out.println(layout.getBounds().getBounds());

		graphics.setColor(foreColor);
		graphics.setFont(font);
		graphics.drawString(initials, x, y);
	}

	private int initialsWidth(int size) {

		int width = Math.round(size * initialsWidthFactor(size));

		while ((width % 2) == 1)
			++width;

		System.out.println(size + " -> " + width);

		return width;
	}

	private float initialsWidthFactor(int size) {

		if (size >= CRITICAL_SIZE)
			return 5.5f / 8.0f;

		float x = CRITICAL_SIZE - size;
		float y = 5.5f + 1.225f / CRITICAL_SIZE * x;

		return y / 8.0f;
	}

	private Font scaleFont(Graphics2D graphics, int width) {

		float tempScale = width * 4;
		Font tempFont = font.deriveFont(tempScale);
		FontMetrics tempMetrics = graphics.getFontMetrics(tempFont);
		int tempWidth = tempMetrics.stringWidth(initials);

		float scale = (tempScale * width) / tempWidth + 0.1f;

		return font.deriveFont(scale);
	}

	private Graphics2D createGraphics(BufferedImage image, int size) {

		Graphics2D graphics = image.createGraphics();

		if (size >= CRITICAL_SIZE) {
			graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		}

		return graphics;
	}
}
