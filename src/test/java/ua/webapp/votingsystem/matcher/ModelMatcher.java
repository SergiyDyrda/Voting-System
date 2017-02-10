package ua.webapp.votingsystem.matcher;

import org.junit.Assert;
import org.springframework.test.web.servlet.ResultMatcher;
import ua.webapp.votingsystem.web.json.JsonUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


public class ModelMatcher<T> {

    private Class<T> entityClass;
    private Comparator<T> comparator;

    public static <T> ModelMatcher<T> of(Class<T> entityClass, Comparator<T> comparator) {
        return new ModelMatcher<>(entityClass, comparator);
    }
    private ModelMatcher(Class<T> entityClass, Comparator<T> comparator) {
        this.entityClass = entityClass;
        this.comparator = comparator;
    }

    public interface Comparator<T> {
        boolean compare(T expected, T actual);
    }

    private class Wrapper {
        private T entity;

        public Wrapper(T entity) {
            this.entity = entity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Wrapper that = (Wrapper) o;
            return entity != null ? comparator.compare(entity, that.entity) : that.entity == null;
        }

        @Override
        public String toString() {
            return String.valueOf(entity);
        }
    }

    public void assertEquals(T expected, T actual) {
        Assert.assertEquals(expected, actual);
    }

    public void assertCollectionsEquals(Collection<T> expected, Collection<T> actual) {
        Assert.assertEquals(wrap(expected), wrap(actual));
    }
    private Wrapper wrap(T object) {
        return new Wrapper(object);
    }

    private List<Wrapper> wrap(Collection<T> object) {
        return object.stream().map(this::wrap).collect(Collectors.toList());
    }

    private T fromJsonValue(String json) {
        return JsonUtil.readValue(json, entityClass);
    }

    private Collection<T> fromJsonValues(String json) {
        return JsonUtil.readValues(json, entityClass);
    }

    public ResultMatcher contentMatcher(T expected) {
        return content().string(
                new TestMatcher<T>(expected) {
                    @Override
                    protected boolean compare(T expected, String body) {
                        Wrapper expectedForCompare = wrap(expected);
                        Wrapper actualForCompare = wrap(fromJsonValue(body));
                        return expectedForCompare.equals(actualForCompare);
                    }
                });
    }

    public ResultMatcher contentListMatcher(T... expected) {
        return contentListMatcher(Arrays.asList(expected));
    }

    public ResultMatcher contentListMatcher(List<T> expected) {
        return content().string(
                new TestMatcher<List<T>>(expected) {
                    @Override
                    protected boolean compare(List<T> expected, String actual) {
                        List<Wrapper> expectedListForCompare = wrap(expected);
                        List<Wrapper> actualListForCompare = wrap(fromJsonValues(actual));
                        return expectedListForCompare.equals(actualListForCompare);
                    }
                }
        );
    }
}
