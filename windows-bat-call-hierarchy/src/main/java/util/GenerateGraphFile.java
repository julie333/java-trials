package util;

/**
 * Program to create Output file used for generating graphs with http://www.webgraphviz.com/ 
 * to illustrate the Windows Batch Call Hierarchy 
 * 
 * @author Julie George
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Data;

@Data
public class GenerateGraphFile {

	public static final String DIRECTORY_PATH = System.getProperty("user.home") + "\\Desktop"
			+ "\\Aufgabe-Windows-BAT-Call-Hierarchy\\";
	public static final String OUTPUT_FILE_NAME = "graphvis_output.txt";
	public static final String GRAPH_TYPE = "digraph";
	public static final String GRAPH_NAME = "batfile_call_hierarchy";

	public static void main(String[] args) throws IOException {
		GenerateGraphFile g = new GenerateGraphFile();
		g.createOutputFile();
	}

	// Output File creation for the Graph
	public void createOutputFile() throws IOException {

		Map<String, ShapeDetails> map = setStyles();

		Path path = Paths.get(DIRECTORY_PATH + OUTPUT_FILE_NAME);
		Path tempPath = Paths.get(DIRECTORY_PATH + "temp_" + OUTPUT_FILE_NAME);
		BufferedWriter tempWriter = Files.newBufferedWriter(tempPath);

		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			// Header, Edge, Node Definition
			writer.write(GRAPH_TYPE + " " + GRAPH_NAME + "{" + "\n" + "edge" + " " + "[fontname= "
					+ map.get("edge").getFontname() + "," + "fontsize= " + map.get("edge").getFontsize() + "]" + "\n"
					+ "node" + " " + "[fontname= " + map.get("node").getFontname() + "," + "fontsize= "
					+ map.get("node").getFontsize() + "," + "shape= " + map.get("node").getShape() + "," + "style= "
					+ map.get("node").getStyle() + "," + "fillcolor= " + map.get("node").getFillcolor() + "]" + "\n");

			// Start, End, Batch Definition
			for (String file : findFiles()) {

				Path filePath = Paths.get(file);
				String fileName = file.toUpperCase().substring(file.lastIndexOf("\\") + 1).replace(".", "_");

				try (BufferedReader reader = Files.newBufferedReader(filePath)) {

					List<String> calls = reader.lines()
							.filter(line -> ((line.toUpperCase().startsWith("CALL ")
									&& (line.endsWith("cmd") || line.endsWith("bat"))))
									|| line.matches("([^\\s]+(\\.(bat|cmd))$)"))
							.map(s -> s.toUpperCase().replace(".", "_").replace("-", "_").replace("CALL ", ""))
							.collect(Collectors.toList());

					if (!calls.isEmpty()) {

						writer.write(fileName + "_start " + "[shape=" + map.get("start").getShape() + "," + "fillcolor="
								+ map.get("start").getFillcolor() + "," + "width=" + map.get("start").getWidth()
								+ "]\n");

						writer.write(fileName + "_end " + "[shape=" + map.get("end").getShape() + "," + "fillcolor="
								+ map.get("end").getFillcolor() + "," + "width=" + map.get("end").getWidth() + "]\n");

						for (String call : calls) {
							boolean flag = false;
							boolean cyclic = false;

							for (String fileCheck : findFiles()) {

								String fileNameCheck = fileCheck.toUpperCase()
										.substring(fileCheck.lastIndexOf("\\") + 1).replace(".", "_").trim();

								if (fileNameCheck.equals(call.toUpperCase().trim())) {
									writer.write(call + "_batch" + "[label=" + call + "_batch" + "]\n");
									flag = true;
								}
								
								// Cyclic Check
								Path filePathCyclicCheck = Paths.get(fileCheck);
								String fileNameCyclicCheck = file.toUpperCase().substring(file.lastIndexOf("\\") + 1)
										.replace(".", "_");

//								try (BufferedReader readerCyclicCheck = Files.newBufferedReader(filePathCyclicCheck)) {
//
//									List<String> callsCyclicCheck = readerCyclicCheck.lines()
//											.filter(line -> ((line.toUpperCase().startsWith("CALL ")
//													&& (line.endsWith("cmd") || line.endsWith("bat"))))
//													|| line.matches("([^\\s]+(\\.(bat|cmd))$)"))
//											.map(s -> s.toUpperCase().replace(".", "_").replace("-", "_")
//													.replace("CALL ", ""))
//											.collect(Collectors.toList());
//
//									for (String callCheck : callsCyclicCheck) {
//										
//									}
//								}

							

							}
							if (!flag)
								System.err.println("File Does Not Exist" + call);
						}

						// Directed Graph Nodes & Edges
						try {
							createGraph(fileName, calls, tempWriter);
						} finally {
						}
					}
				}
			}
			tempWriter.close();
			writer.close();
			mergeFiles(path, tempPath);
		}
	}

	// Set custom styles for shapes used like edge,node, start and end points
	public Map<String, ShapeDetails> setStyles() {
		ShapeDetails edge = new ShapeDetails("Courier", 9.0);
		ShapeDetails node = new ShapeDetails("Courier", 10.0, "rect", "filled", "lightyellow");
		ShapeDetails start = new ShapeDetails("point", "black", 0.2);
		ShapeDetails end = new ShapeDetails("point", "black", 0.2);

		Map<String, ShapeDetails> styles = new HashMap<String, ShapeDetails>() {
			private static final long serialVersionUID = 1L;
			{
				put("edge", edge);
				put("node", node);
				put("start", start);
				put("end", end);
			}
		};
		return styles;
	}

	// Search Recursively in Directory for Filetype .cmd & .bat
	public List<String> findFiles() throws IOException {

		try (Stream<Path> stream = Files.walk(Paths.get(DIRECTORY_PATH), FileVisitOption.FOLLOW_LINKS)) {

			List<String> filePaths = stream.filter((p) -> !p.toFile().isDirectory()
					&& (p.toFile().getAbsolutePath().endsWith(".bat") || p.toFile().getAbsolutePath().endsWith(".cmd")))
					.map(String::valueOf).collect(Collectors.toList());

			return filePaths;
		}
	}

	// Traverse the Directed Graph
	public void createGraph(String fileName, List<String> calls, BufferedWriter tempWriter) throws IOException {

		int counter = 0;
		for (int i = 0; i <= calls.size(); i++) {

			if (i == 0)
				tempWriter.write(fileName + "_start" + " -> ");
			else
				tempWriter.write(calls.get(i - 1) + "_batch" + " -> ");

			if (i < calls.size())
				tempWriter.write(calls.get(i) + "_batch" + "[label=" + fileName + "_" + ++counter + "]\n");
			else
				tempWriter.write(fileName + "_end" + " [label=" + fileName + "_" + ++counter + "]\n");
		}
	}

	// To merge the temporary and main output file
	private void mergeFiles(Path path, Path tempPath) throws IOException {

		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
			try (BufferedReader reader = Files.newBufferedReader(tempPath)) {
				String line = "";
				while ((line = reader.readLine()) != null) {
					writer.append(line + "\n");
				}
				reader.close();
				writer.append("}");
			}
			writer.close();
			Files.deleteIfExists(tempPath);
		}
	}
}
