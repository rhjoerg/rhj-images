package ch.rhj.images.svg;

import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ch.rhj.images.svg.model.Svg;

public interface Svgs {

	public static Svg svg(int width, int height) {

		return new Svg(width, height);
	}

	public static Svg read(Path path) throws Exception {

		return new XmlMapper().readValue(Files.readAllBytes(path), Svg.class);
	}

	public static String write(Svg svg) throws Exception {

		XmlMapper mapper = new XmlMapper();
		ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();

		return objectWriter.writeValueAsString(svg);
	}
}
