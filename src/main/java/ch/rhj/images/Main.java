package ch.rhj.images;

import static ch.rhj.images.action.CreateScrewedLogo.createScrewedLogo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws Exception {

		Path directory = Paths.get("target", "images");

		Files.createDirectories(directory);

		createScrewedLogo(directory.resolve("screwed-logo")).run();
	}
}
