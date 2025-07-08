package hotel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static ConfigManager instance;
    private final Properties properties;

    private ConfigManager() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("resources/config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Не удалось загрузить config.properties: " + e.getMessage());
        }
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public int getBookingViewLimit() {
        try {
            return Integer.parseInt(properties.getProperty("booking.view.limit", "3"));
        } catch (NumberFormatException e) {
            return 3;
        }
    }

    public boolean isRoomStatusChangeEnabled() {
        return Boolean.parseBoolean(properties.getProperty("room.status.change.enabled", "true"));
    }
}
