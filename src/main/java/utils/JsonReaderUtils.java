package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonReaderUtils {

    // Method to read test cases and map them directly to test names for fast lookup
    public static <T> Map<String, T> getTestCasesMap(String filePath, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Deserialize the JSON file into a Map
        return objectMapper.readValue(new File(filePath),
                objectMapper.getTypeFactory().constructMapType(Map.class, String.class, clazz));
    }
}
