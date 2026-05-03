package io.github.fajzu.shared.scanner;

import com.google.common.reflect.ClassPath;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ClassScanner {

    public synchronized Set<Class<?>> findAnnotatedClasses(
        final @NotNull Class<? extends Annotation> annotation,
        final @NotNull String packageName,
        final @NotNull ClassLoader classLoader) {
        final Set<Class<?>> result = new HashSet<>();

        for (final Class<?> clazz : this.scanClasses(packageName, classLoader)) {
            if (clazz.isAnnotationPresent(annotation)) {
                result.add(clazz);
            }
        }

        return result;
    }

    public synchronized Set<Class<?>> findAnnotatedClasses(
        final @NotNull Class<? extends Annotation> annotation,
        final @NotNull String packageName) {
        return findAnnotatedClasses(annotation, packageName, getClass().getClassLoader());
    }

    public synchronized <T> Set<Class<? extends T>> findClasses(
        final @NotNull String packageName,
        final @NotNull Class<T> type,
        final @NotNull ClassLoader classLoader) {
        final Set<Class<? extends T>> result = new HashSet<>();

        for (final Class<?> clazz : scanClasses(packageName, classLoader)) {
            if (type.isAssignableFrom(clazz)
                && !clazz.isInterface()
                && !Modifier.isAbstract(clazz.getModifiers())) {
                result.add(clazz.asSubclass(type));
            }
        }

        return result;
    }

    public synchronized <T> Set<Class<? extends T>> findClasses(
        final @NotNull String packageName,
        final @NotNull Class<T> type) {
        return findClasses(packageName, type, getClass().getClassLoader());
    }

    public synchronized List<Class<?>> scanClasses(final @NotNull String packageToScan,
                                                   final @NotNull ClassLoader classLoader) {
        final List<Class<?>> loadedClasses = new ArrayList<>();
        final String path = packageToScan.replace('.', '/');

        try {
            final URL jarUrl = getClass().getProtectionDomain().getCodeSource().getLocation();
            final JarFile jarFile = new JarFile(jarUrl.getFile());

            final Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                final String name = entry.getName();

                if (name.endsWith(".class") && name.startsWith(path)) {
                    final String className = name.replace('/', '.').substring(0, name.length() - 6);
                    try {
                        final Class<?> clazz = Class.forName(className, false, classLoader);
                        loadedClasses.add(clazz);
                    } catch (Throwable exception) {
                        exception.printStackTrace();
                    }
                }
            }
            jarFile.close();
        } catch (Exception ex) {
            return scanWithGuava(packageToScan, classLoader);
        }

        return loadedClasses;
    }

    private List<Class<?>> scanWithGuava(final @NotNull String packageToScan,
                                         final @NotNull ClassLoader classLoader) {
        final List<Class<?>> loadedClasses = new ArrayList<>();
        try {
            final ClassPath classPath = ClassPath.from(classLoader);

            for (ClassPath.ClassInfo info : classPath.getTopLevelClassesRecursive(packageToScan)) {
                try {
                    final Class<?> clazz = Class.forName(info.getName(), false, classLoader);
                    loadedClasses.add(clazz);
                } catch (ClassNotFoundException | LinkageError exception) {
                    exception.printStackTrace();
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return loadedClasses;
    }
}