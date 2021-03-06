package io.github.phantamanta44.discord4j.util.reflection;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.phantamanta44.discord4j.util.math.BitUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TypeFilter extends MemberFilter<Class<?>> {

    TypeFilter(FastClasspathScanner scanner) {
        super(scanner);
    }

    TypeFilter(MemberFilter<Class<?>> parent, Predicate<Class<?>> test) {
        super(parent, test);
    }

    @Override
    Stream<Class<?>> accumulate() {
        Set<Class<?>> types = new HashSet<>();
        getScanner().scan().getNamesOfAllClasses().forEach(cn -> {
            try {
                types.add(Class.forName(cn));
            } catch (NoClassDefFoundError | ClassNotFoundException | ExceptionInInitializerError ignored) { }
        });
        return types.stream();
    }

    public TypeFilter enums() {
        return new TypeFilter(this, Class::isEnum);
    }

    public TypeFilter extending(Class<?> superClass) {
        return new TypeFilter(this, superClass::isAssignableFrom);
    }

    public TypeFilter interfaces() {
        return new TypeFilter(this, Class::isInterface);
    }

    public TypeFilter mask(int mods) {
        return new TypeFilter(this, t -> BitUtils.hasFlags(t.getModifiers(), mods));
    }

    public TypeFilter mod(int... mods) {
        return mask(BitUtils.foldFlags(mods));
    }

    public TypeFilter name(String name) {
        return new TypeFilter(this, t -> t.getName().equals(name));
    }

    public TypeFilter supering(Class<?> childClass) {
        return new TypeFilter(this, t -> t.isAssignableFrom(childClass));
    }

    @SuppressWarnings("unchecked")
    public TypeFilter tagged(Class<?>... annotations) {
        return new TypeFilter(this, t -> Arrays.stream(annotations).allMatch(a -> t.isAnnotationPresent((Class<? extends Annotation>)a)));
    }

}
