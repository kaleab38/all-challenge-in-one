import java.io.*;

public class RobustFileConfigReader {
    public static void main(String[] args) {
        BufferedReader reader = null;

        try {
            // Try to open the config file
            File configFile = new File("config.txt");
            reader = new BufferedReader(new FileReader(configFile));

            // Read first line (config version)
            String versionLine = reader.readLine();
            int version = Integer.parseInt(versionLine);

            // Check config version
            if (version < 2) {
                throw new Exception("Config version too old!");
            }

            // Read second line (file path)
            String filePath = reader.readLine();
            File pathFile = new File(filePath);

            // Check if the file path exists
            if (!pathFile.exists()) {
                throw new IOException("File path not found: " + filePath);
            }

            // If everything goes well
            System.out.println("✅ Config file read successfully!");
            System.out.println("Version: " + version);
            System.out.println("Path: " + filePath);

        }
        catch (FileNotFoundException e) {
            System.out.println("Error: Config file not found!");
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format in config version!");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        finally {
            // Always executes
            System.out.println("Config read attempt finished.");
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                System.out.println("Error closing the reader.");
            }
        }
    }
}
