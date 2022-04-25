package utils;

import web.base.Command;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.testfolders.case1.ACase1;
import utils.testfolders.case1.ACommandCase1;
import utils.testfolders.case1.BCase1;
import utils.testfolders.case1.CCase1;
import utils.testfolders.case3.ACommandCase3;
import utils.testfolders.case3.BCommandCase3;
import utils.testfolders.case3.CCommandNoAnnotationCase3;
import utils.testfolders.case3.inner.DCommandCase3;
import utils.testfolders.case3.inner.ECase3;
import utils.testfolders.case3.inner.inner.FCase3;
import utils.testfolders.case3.inner.inner.HCommandCase3;
import utils.testfolders.case4.ACase4;
import utils.testfolders.case4.BCase4;
import utils.testfolders.case4.CCase4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassUtilsTest {

    @ParameterizedTest
    @MethodSource("getClassesInPackageCases")
    void testGetClassesInPackage(String packageName, List<Class<?>> expected){
        List<Class<?>> classes = ClassUtils.getClassesInPackage(packageName);
        assertEquals(expected, classes);
    }

    @ParameterizedTest
    @MethodSource("getAnnotatedCommandClassesInPackageCases")
    void testGetAnnotatedCommandClassesInPackage(String packageName, List<Class<?>> expected){
        List<Class<Command>> classes = ClassUtils.getAnnotatedCommandClassesInPackage(packageName);
        assertEquals(expected, classes);
    }

    public static Stream<Arguments> getClassesInPackageCases(){
        return Stream.of(
                Arguments.of("utils.testfolders.case1", new ArrayList<>(
                        Arrays.asList(ACase1.class, ACommandCase1.class, BCase1.class, CCase1.class)
                )),
                Arguments.of("utils.testfolders.case2", new ArrayList<>(

                )),
                Arguments.of("utils.testfolders.case3", new ArrayList<>(
                        Arrays.asList(ACommandCase3.class, BCommandCase3.class, CCommandNoAnnotationCase3.class, DCommandCase3.class, ECase3.class, FCase3.class, HCommandCase3.class)
                )),
                Arguments.of("utils.testfolders.case4", new ArrayList<>(
                        Arrays.asList(ACase4.class, BCase4.class, CCase4.class)
                ))
        );
    }

    public static Stream<Arguments> getAnnotatedCommandClassesInPackageCases(){
        return Stream.of(
                Arguments.of("utils.testfolders.case1", new ArrayList<>(
                        Arrays.asList(ACommandCase1.class)
                )),
                Arguments.of("utils.testfolders.case2", new ArrayList<>(

                )),
                Arguments.of("utils.testfolders.case3", new ArrayList<>(
                        Arrays.asList(ACommandCase3.class, BCommandCase3.class, DCommandCase3.class, HCommandCase3.class)
                )),
                Arguments.of("utils.testfolders.case4", new ArrayList<>(

                ))
        );
    }
}
