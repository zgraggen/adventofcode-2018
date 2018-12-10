package com.zgraggen.name;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHelper {

	public static ArrayList<String> readFile(String filename){
		ArrayList<String> lines = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			lines = stream
				.collect(Collectors.toCollection(ArrayList::new));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
}
