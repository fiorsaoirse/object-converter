package converter.impl;

import converter.Converter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings({"unchecked"})
public abstract class AbstractConverter<T> implements Converter<T> {
    /*@FunctionalInterface
    interface ConverterRule<R> extends Function<T, R> {};*/

    private Class<T> converterType;

    private Map<Class<?>, Function<T, ?>> rules = new HashMap<>();

    // todo: how to identify target class?
    protected Function<T, Object> nonImplemented = object -> {
        String message = String.format("Can not convert object from %s", converterType);
        throw new IllegalArgumentException(message);
    };

    public AbstractConverter(Class<T> converterType) {
        this.converterType = converterType;
    }

    @Override
    public <R> void addRule(Class<R> cls, Function<T, R> conversion) {
        this.rules.put(cls, conversion);
    }

    @Override
    public boolean canConvert(Object o) {
        return o.getClass().equals(this.converterType);
    }

    @Override
    public <R> R convert(T object, Class<R> cls) {
        return (R) this.rules.getOrDefault(cls, nonImplemented).apply(object);
    }
}