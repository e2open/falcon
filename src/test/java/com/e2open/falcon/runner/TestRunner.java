package com.e2open.falcon.runner;

import org.junit.Test;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.ArrayList;
import java.util.List;

public class TestRunner extends BlockJUnit4ClassRunner {
    public static final String OS = System.getProperty("os.name");
    public TestRunner(final Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        // First, get the base list of tests
        final List<FrameworkMethod> allMethods = getTestClass()
                .getAnnotatedMethods(Test.class);
        if (allMethods == null || allMethods.size() == 0)
            return allMethods;

        // Filter the list down
        final List<FrameworkMethod> filteredMethods = new ArrayList<FrameworkMethod>(allMethods.size());
        for (final FrameworkMethod method : allMethods) {
            final OperatingSystem customAnnotation = method.getAnnotation(OperatingSystem.class);
            if (customAnnotation != null) {
                String expectedOS = customAnnotation.value();
                if (OS.indexOf(expectedOS) >= 0) filteredMethods.add(method);
            } else {
                filteredMethods.add(method);
            }
        }

        return filteredMethods;
    }
}

