package scanner;

import web.base.Command;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import scanner.testfolders.case1.ACase1;
import scanner.testfolders.case1.ACommandCase1;
import scanner.testfolders.case1.BCase1;
import scanner.testfolders.case1.CCase1;
import scanner.testfolders.case3.ACommandCase3;
import scanner.testfolders.case3.BCommandCase3;
import scanner.testfolders.case3.CCommandNoAnnotationCase3;
import scanner.testfolders.case3.inner.DCommandCase3;
import scanner.testfolders.case3.inner.ECase3;
import scanner.testfolders.case3.inner.inner.FCase3;
import scanner.testfolders.case3.inner.inner.HCommandCase3;
import scanner.testfolders.case4.ACase4;
import scanner.testfolders.case4.BCase4;
import scanner.testfolders.case4.CCase4;
import web.base.annotations.WebMapping;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassPathScannerTest {

    @ParameterizedTest
    @MethodSource("getClassesInPackageCases")
    void testGetClassesInPackage(String packageName, Set<Class<?>> expected){
        ClassPathScanner classPathScanner = new ClassPathScanner();
        Set<Class<?>> classes = classPathScanner.scan(packageName);
        assertEquals(expected, classes);
    }

    @ParameterizedTest
    @MethodSource("getAnnotatedCommandClassesInPackageCases")
    void testGetAnnotatedCommandClassesInPackage(String packageName, Set<Class<?>> expected){
        ClassPathScanner classPathScanner = new ClassPathScanner();
        Set<Class<?>> classes = classPathScanner.scan(packageName, c -> Command.class.isAssignableFrom(c) && c.isAnnotationPresent(WebMapping.class));
        assertEquals(expected, classes);
    }

    public static Stream<Arguments> getClassesInPackageCases(){
        return Stream.of(
                Arguments.of("scanner.testfolders.case1", new HashSet<>(
                        Arrays.asList(ACase1.class, ACommandCase1.class, BCase1.class, CCase1.class)
                )),
                Arguments.of("scanner.testfolders.case2", new HashSet<>(

                )),
                Arguments.of("scanner.testfolders.case3", new HashSet<>(
                        Arrays.asList(ACommandCase3.class, BCommandCase3.class, CCommandNoAnnotationCase3.class, DCommandCase3.class, ECase3.class, FCase3.class, HCommandCase3.class)
                )),
                Arguments.of("scanner.testfolders.case4", new HashSet<>(
                        Arrays.asList(ACase4.class, BCase4.class, CCase4.class)
                ))
        );
    }

    public static Stream<Arguments> getAnnotatedCommandClassesInPackageCases(){
        return Stream.of(
                Arguments.of("scanner.testfolders.case1", new HashSet<>(
                        Arrays.asList(ACommandCase1.class)
                )),
                Arguments.of("scanner.testfolders.case2", new HashSet<>(

                )),
                Arguments.of("scanner.testfolders.case3", new HashSet<>(
                        Arrays.asList(ACommandCase3.class, BCommandCase3.class, DCommandCase3.class, HCommandCase3.class)
                )),
                Arguments.of("scanner.testfolders.case4", new HashSet<>(

                ))
        );
    }
}
