package config;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class ConfigInjector {

    public static void inject(Object target) {
        Class<?> clazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(ConfigProperty.class)) {
                ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);

                String fileName = annotation.configFileName();
                String propertyName = annotation.propertyName();
                if (propertyName.isEmpty()) {
                    propertyName = clazz.getSimpleName() + "." + field.getName();
                }

                try (FileInputStream fis = new FileInputStream(fileName)) {
                    Properties props = new Properties();
                    props.load(fis);

                    String value = props.getProperty(propertyName);
                    if (value == null) continue;

                    field.setAccessible(true);
                    Object convertedValue = convertValue(value, annotation.type(), field.getType());
                    field.set(target, convertedValue);
                } catch (Exception e) {
                    System.err.println("Ошибка при конфигурировании поля " + field.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    private static Object convertValue(String value, Class<?> specifiedType, Class<?> fieldType) {
        Class<?> type = (specifiedType != String.class) ? specifiedType : fieldType;

        if (type == int.class || type == Integer.class)
            return Integer.parseInt(value);
        if (type == boolean.class || type == Boolean.class)
            return Boolean.parseBoolean(value);
        if (type == double.class || type == Double.class)
            return Double.parseDouble(value);
        if (type == long.class || type == Long.class)
            return Long.parseLong(value);
        if (type.isEnum())
            return Enum.valueOf((Class<Enum>) type, value);
        return value;
    }
}