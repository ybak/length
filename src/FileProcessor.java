import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileProcessor {

	private LengthCalculator calculator;
	private Scanner scanner;
	private PrintWriter printer;

	public FileProcessor(LengthCalculator lengthCalculator, String inputFile,
			String outputFile) {
		this.calculator = lengthCalculator;
		try {
			this.scanner = new Scanner(new File(inputFile));
			this.printer = new PrintWriter(new File(outputFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws IOException {
		String inputFile = "E:\\Workspaces\\PartTimeWorkspace\\length\\input.txt";
		String outputFile = "E:\\Workspaces\\PartTimeWorkspace\\length\\onput.txt";

		FileProcessor fileProcesser = new FileProcessor(new LengthCalculator(),
				inputFile, outputFile);

		fileProcesser.prepareCalculator();
		fileProcesser.calculateAndOutput();
	}

	public void prepareCalculator() {
		for (int i = 0; i < 6; i++) {
			String nextLine = scanner.nextLine();
			String[] strings = nextLine.split(" ");
			String unit = strings[1];
			Float meter = Float.valueOf(strings[3]);
			calculator.addConversion(unit, meter);
		}
	}

	public void calculateAndOutput() {
		scanner.nextLine();
		printer.write("ybakswu@gmail.com\n");
		printer.write("\n");
		for (int i = 0; i < 10; i++) {
			String arithmetic = scanner.nextLine();
			Float result = processArithmetic(arithmetic);
			printer.write(String.format("%.2f", result) + " m\n");
		}
		printer.flush();
	}

	public Float processArithmetic(String nextLine) {
		String[] strings = nextLine.split(" ");
		Float result = 0F;
		Boolean add = true;
		for (int j = 0; j < strings.length; j++) {
			String string = strings[j];
			if (isOperator(string)) {
				add = string.equals("+");
			} else {
				String unit = strings[j + 1];
				Float value = Float.valueOf(string);
				Float meters = calculator.calculate(unit, value);
				result = add ? result + meters : result - meters;
				j++;
			}
		}
		return result;
	}

	private boolean isOperator(String string) {
		return string.equals("+") || string.equals("-");
	}
}

class LengthCalculator {

	private Map<String, Float> conversionMap = new HashMap<String, Float>();
	private Map<String, String> pluralMap = new HashMap<String, String>();

	public LengthCalculator() {
		pluralMap.put("mile", "miles");
		pluralMap.put("yard", "yards");
		pluralMap.put("inch", "inches");
		pluralMap.put("foot", "feet");
		pluralMap.put("fath", "faths");
		pluralMap.put("furlong", "furlong");
	}

	public void addConversion(String unit, Float meter) {
		conversionMap.put(unit, meter);
		conversionMap.put(pluralMap.get(unit), meter);
	}

	public Float calculate(String unit, Float value) {
		return value * conversionMap.get(unit);
	}

}
