package ch.rhj.images.action;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public class CreateLogo implements Runnable {

	private final Path directory;

	public CreateLogo(Path directory) {

		this.directory = directory;
	}

	@Override
	public void run() {

		DOMImplementation domImplementation = SVGDOMImplementation.getDOMImplementation();
		Document document = domImplementation.createDocument("http://www.w3.org/2000/svg", "svg", null);
		SVGGeneratorContext generatorContext = SVGGeneratorContext.createDefault(document);
		SVGGraphics2D graphics2D = new SVGGraphics2D(generatorContext, true);

		graphics2D.setSVGCanvasSize(new Dimension(256, 256));

		paint(graphics2D);
		write(graphics2D, directory.resolve("logo.svg"));
	}

	private void paint(SVGGraphics2D graphics2D) {

		Font font = Font.decode("Consolas").deriveFont(176f).deriveFont(Font.BOLD);

		graphics2D.setColor(new Color(25, 25, 112));
		graphics2D.fillRoundRect(16, 16, 256 - 16, 256 - 32, 80, 80);

		graphics2D.setFont(font);
		graphics2D.setColor(Color.WHITE);
		graphics2D.drawString("RJ", 44, 180);
	}

	private void write(SVGGraphics2D graphics2D, Path path) {

		try {

			Files.deleteIfExists(path);

			StringWriter writer = new StringWriter();

			graphics2D.stream(writer);
			Files.write(path, writer.toString().getBytes(UTF_8));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static CreateLogo create(Path directory) {

		return new CreateLogo(directory);
	}
}
