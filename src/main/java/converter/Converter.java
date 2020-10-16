package converter;

import java.util.function.Function;

public interface Converter<T> {

    <R> void addRule(Class<R> cls, Function<T, R> conversion);

    boolean canConvert(Object o);

    <R> R convert(T object, Class<R> cls);
}
