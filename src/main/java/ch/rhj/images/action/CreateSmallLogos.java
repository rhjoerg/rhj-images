package ch.rhj.images.action;

import static ch.rhj.images.Colors.MIDNIGHT;
import static ch.rhj.images.Colors.TRANSPARENT;
import static ch.rhj.images.Colors.WHITE;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.lang.String.format;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

public class CreateSmallLogos implements Runnable {

	private final Path directory;

	private final Map<Integer, Consumer<Graphics2D>> backgroundPainters = Map.of( //
			16, this::paintBackground16, //
			24, this::paintBackground24, //
			32, this::paintBackground32);

	private final Map<Integer, Consumer<Graphics2D>> initialsPainters = Map.of( //
			16, this::paintInitials16, //
			24, this::paintInitials24, //
			32, this::paintInitials32);

	public CreateSmallLogos(Path directory) {
		this.directory = directory;
	}

	public static CreateSmallLogos createSmallLogos(Path directory) {
		return new CreateSmallLogos(directory);
	}

	@Override
	public void run() {

		try {

			System.out.println("creating small logos");

			Files.createDirectories(directory);
			IntStream.of(32, 24, 16).forEach(this::createLogo);

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

		Graphics2D graphics = image.createGraphics();

		paintBackground(graphics, size);
		paintInitials(graphics, size);

		graphics.dispose();
	}

	private void paintBackground(Graphics2D graphics, int size) {

		graphics.setColor(TRANSPARENT);
		graphics.fillRect(0, 0, size, size);

		graphics.setColor(MIDNIGHT);
		backgroundPainters.get(size).accept(graphics);
	}

	private void paintBackground32(Graphics2D graphics) {

		int[] margins = new int[32];

		margins[0] = margins[31] = 5;
		margins[1] = margins[30] = 3;
		margins[2] = margins[29] = 2;
		margins[3] = margins[28] = 1;
		margins[4] = margins[27] = 1;

		paintBackground(graphics, margins);
	}

	private void paintBackground24(Graphics2D graphics) {

		int[] margins = new int[24];

		margins[0] = margins[23] = 3;
		margins[1] = margins[22] = 2;
		margins[2] = margins[21] = 1;

		paintBackground(graphics, margins);
	}

	private void paintBackground16(Graphics2D graphics) {

		int[] margins = new int[16];

		margins[0] = margins[15] = 2;
		margins[1] = margins[14] = 1;

		paintBackground(graphics, margins);
	}

	private void paintBackground(Graphics2D graphics, int[] margins) {

		for (int x = 0, n = margins.length; x < n; ++x) {

			int y = margins[x];
			int h = n - 2 * y;

			graphics.fillRect(x, y, 1, h);
		}
	}

	private void paintInitials(Graphics2D graphics, int size) {

		graphics.setColor(WHITE);
		initialsPainters.get(size).accept(graphics);
	}

	private void paintInitials32(Graphics2D graphics) {

		List<Point> points = new ArrayList<>();

		for (int y = 8; y < 24; ++y) {

			points.add(new Point(8, y));
			points.add(new Point(9, y));
		}

		points.add(new Point(10, 8));
		points.add(new Point(11, 8));
		points.add(new Point(12, 8));
		points.add(new Point(13, 8));

		points.add(new Point(10, 9));
		points.add(new Point(11, 9));
		points.add(new Point(12, 9));
		points.add(new Point(13, 9));
		points.add(new Point(14, 9));

		for (int y = 10; y < 15; ++y) {

			points.add(new Point(13, y));
			points.add(new Point(14, y));
		}

		points.add(new Point(10, 15));
		points.add(new Point(11, 15));
		points.add(new Point(12, 15));
		points.add(new Point(13, 15));

		points.add(new Point(10, 16));
		points.add(new Point(11, 16));
		points.add(new Point(12, 16));
		points.add(new Point(13, 16));

		for (int y = 17; y < 24; ++y) {

			points.add(new Point(13, y));
			points.add(new Point(14, y));
		}

		// J

		points.add(new Point(17, 20));
		points.add(new Point(18, 20));

		points.add(new Point(17, 21));
		points.add(new Point(18, 21));

		points.add(new Point(17, 22));
		points.add(new Point(18, 22));

		points.add(new Point(18, 23));

		for (int x = 18; x < 22; ++x) {

			points.add(new Point(x, 22));
			points.add(new Point(x, 23));
		}

		points.add(new Point(23, 22));

		for (int y = 8; y < 22; ++y) {

			points.add(new Point(22, y));
			points.add(new Point(23, y));
		}

		paintInitials(graphics, points);
	}

	private void paintInitials24(Graphics2D graphics) {

		List<Point> points = new ArrayList<>();

		for (int y = 6; y < 18; ++y) {

			points.add(new Point(5, y));
			points.add(new Point(6, y));
		}

		points.add(new Point(7, 6));
		points.add(new Point(8, 6));

		points.add(new Point(7, 7));
		points.add(new Point(8, 7));
		points.add(new Point(9, 7));

		points.add(new Point(9, 8));
		points.add(new Point(10, 8));

		points.add(new Point(9, 9));
		points.add(new Point(10, 9));

		points.add(new Point(9, 10));
		points.add(new Point(10, 10));

		points.add(new Point(7, 11));
		points.add(new Point(8, 11));
		points.add(new Point(9, 11));

		points.add(new Point(7, 12));
		points.add(new Point(8, 12));
		points.add(new Point(9, 12));

		points.add(new Point(9, 13));
		points.add(new Point(10, 13));

		points.add(new Point(9, 14));
		points.add(new Point(10, 14));

		points.add(new Point(9, 15));
		points.add(new Point(10, 15));

		points.add(new Point(9, 16));
		points.add(new Point(10, 16));

		points.add(new Point(9, 17));
		points.add(new Point(10, 17));

		// J

		points.add(new Point(14, 14));
		points.add(new Point(15, 14));

		points.add(new Point(14, 15));
		points.add(new Point(15, 15));

		points.add(new Point(14, 16));
		points.add(new Point(15, 16));
		points.add(new Point(16, 16));
		points.add(new Point(17, 16));
		points.add(new Point(18, 16));

		points.add(new Point(15, 17));
		points.add(new Point(16, 17));
		points.add(new Point(17, 17));

		for (int y = 6; y < 16; ++y) {

			points.add(new Point(17, y));
			points.add(new Point(18, y));
		}

		paintInitials(graphics, points);
	}

	private void paintInitials16(Graphics2D graphics) {

		List<Point> points = new ArrayList<>();

		for (int y = 4; y < 12; ++y) {
			points.add(new Point(4, y));
		}

		points.add(new Point(5, 4));
		points.add(new Point(6, 4));
		points.add(new Point(7, 5));
		points.add(new Point(7, 6));
		points.add(new Point(7, 7));
		points.add(new Point(6, 8));
		points.add(new Point(5, 8));
		points.add(new Point(6, 9));
		points.add(new Point(7, 10));
		points.add(new Point(7, 11));

		// J

		points.add(new Point(9, 9));
		points.add(new Point(9, 10));
		points.add(new Point(10, 11));
		points.add(new Point(11, 11));

		for (int y = 4; y < 11; ++y) {
			points.add(new Point(12, y));
		}

		paintInitials(graphics, points);
	}

	private void paintInitials(Graphics2D graphics, List<Point> points) {

		points.forEach(p -> graphics.fillRect(p.x, p.y, 1, 1));
	}
}
