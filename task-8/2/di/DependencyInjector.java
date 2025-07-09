package di;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DependencyInjector {
    private final Map<Class<?>, Object> registry = new HashMap<>();

    public <T> void register(Class<T> clazz, T instance) {
        registry.put(clazz, instance);
    }

    public void injectDependencies(Object target) {
        for (Field field : target.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> dependencyType = field.getType();
                Object dependency = registry.get(dependencyType);

                if (dependency == null) {
                    throw new RuntimeException("Зависимость не зарегистрирована: " + dependencyType.getName());
                }

                try {
                    field.setAccessible(true);
                    field.set(target, dependency);

                    injectDependencies(dependency);

                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Не удалось внедрить зависимость: " + field.getName(), e);
                }
            }
        }
    }
}
