package ch.rhj.images;

import static ch.rhj.images.action.CreateLargeLogos.createLargeLogos;
import static ch.rhj.images.action.CreateScrewedLogos.createScrewedLogos;
import static ch.rhj.images.action.CreateSmallLogos.createSmallLogos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws Exception {

		Path directory = Paths.get("target", "images");

		Files.createDirectories(directory);

		createScrewedLogos(directory.resolve("screwed-logo")).run();
		createLargeLogos(directory.resolve("logo")).run();
		createSmallLogos(directory.resolve("logo")).run();
	}
}
