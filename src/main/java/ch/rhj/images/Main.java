package ch.rhj.images;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.batik.apps.rasterizer.DestinationType.PNG;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.apache.batik.apps.rasterizer.SVGConverter;

import ch.rhj.images.svg.Svgs;
import ch.rhj.images.svg.model.Rect;
import ch.rhj.images.svg.model.Svg;
import ch.rhj.images.svg.model.Text;

public class Main {

	public static void main(String[] args) throws Exception {

		Path directory = Paths.get("target", "images");

		Files.createDirectories(directory);

		// CreateLogo.create(directory).run();

		Svg svg = Svgs.svg(256, 256);

		// svg.circle = new Circle(128, 128, 64, "red");
		svg.rect = new Rect(0, 0, 256, 256, 80, 80, "#191970");
		svg.text = new Text(31, 188, "RJ");
		svg.text.style = "font-family: Consolas; font-size: 184px; font-weight: 500; fill: #fff;";

		String logo = Svgs.write(svg);
		Path logoPath = directory.resolve("logo.svg");

		Files.deleteIfExists(logoPath);
		Files.writeString(logoPath, logo, UTF_8);

		URL htmlSourceUrl = Main.class.getClassLoader().getResource("logo.html");
		Path htmlSourcePath = Paths.get(htmlSourceUrl.toURI());
		Path htmlTargetPath = directory.resolve("logo.html");
		String html = Files.readString(htmlSourcePath, UTF_8);

		html = html.replace("${svg}", logo);
		Files.writeString(htmlTargetPath, html, UTF_8);

		IntStream.of(256, 128, 64, 48, 32).forEach(s -> {

			try {

				Path pngPath = directory.resolve("logo@" + s + ".png");
				SVGConverter converter = new SVGConverter();

				converter.setSources(new String[] { logoPath.toString() });
				converter.setDestinationType(PNG);
				converter.setDst(pngPath.toFile());
				converter.setWidth(s);
				converter.setHeight(s);
				converter.setQuality(0.99f);
				converter.execute();

			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		});

		BufferedImage image = ImageIO.read(directory.resolve("logo@256.png").toFile());
		int reference = image.getRGB(10, 128);

		for (int x = 10; x < 255; ++x) {

			if (image.getRGB(x, 128) != reference) {

				System.out.println("left: " + x);
				break;
			}
		}

		for (int x = 246; x >= 0; --x) {

			if (image.getRGB(x, 128) != reference) {

				System.out.println("right: " + (256 - x));
				break;
			}
		}

		for (int y = 20; y < 256; ++y) {

			if (image.getRGB(55, y) != reference) {

				System.out.println("top: " + y);
				break;
			}
		}

		for (int y = 236; y >= 0; --y) {

			if (image.getRGB(55, y) != reference) {

				System.out.println("bottom: " + (256 - y));
				break;
			}
		}
	}
}
