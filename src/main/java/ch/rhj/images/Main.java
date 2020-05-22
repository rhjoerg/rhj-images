package ch.rhj.images;

import static ch.rhj.images.action.CreateLogo.createLogo;
import static ch.rhj.images.action.CreateScrewedLogo.createScrewedLogo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws Exception {

		Path directory = Paths.get("target", "images");

		Files.createDirectories(directory);

		Fonts.availableFontNames().forEach(name -> System.out.println(name));

		createScrewedLogo(directory.resolve("screwed-logo")).run();
		createLogo(directory.resolve("logo")).run();
	}
}
