package io.github.phantamanta44.discord4j.util.reflection;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.phantamanta44.discord4j.util.CollUtils;
import io.github.phantamanta44.discord4j.util.BitUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MethodFilter extends MemberFilter<Method> {

    MethodFilter(FastClasspathScanner scanner) {
        super(scanner);
    }

    MethodFilter(MemberFilter<Method> parent, Predicate<Method> test) {
        super(parent, test);
    }

    @Override
    Stream<Method> accumulate() {
        Set<Class> types = new HashSet<>();
        getScanner().matchAllClasses(types::add).scan();
        return types.stream().flatMap(t -> Arrays.stream(t.getDeclaredMethods()));
    }

    public MethodFilter mask(int mods) {
        return new MethodFilter(this, m -> BitUtils.hasFlags(m.getModifiers(), mods));
    }

    public MethodFilter mod(int... mods) {
        return mask(BitUtils.foldFlags(mods));
    }

    public MethodFilter name(String name) {
        return new MethodFilter(this, m -> m.getName().equals(name));
    }

    public MethodFilter params(Class<?>... paramTypes) {
        return new MethodFilter(this, m -> Arrays.equals(m.getParameterTypes(), paramTypes));
    }

    public MethodFilter returns(Class<?> returnType) {
        return new MethodFilter(this, m -> m.getReturnType().equals(returnType));
    }

    public MethodFilter tagged(Class<?>... annotations) {
        return new MethodFilter(this, m -> CollUtils.containsAll(m.getAnnotations(), (Object[])annotations));
    }

}
