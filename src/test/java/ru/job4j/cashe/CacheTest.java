package ru.job4j.cashe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    void whenAddFind() {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        var find = cache.findById(base.id());
        assertThat(find.get().name()).isEqualTo("Base");
    }

    @Test
    void whenAddUpdateFind() throws OptimisticException {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(new Base(1, "Base update", 1));
        var find = cache.findById(base.id());
        assertThat(find.get().name()).isEqualTo("Base update");
    }

    @Test
    void whenAddDeleteFind() {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.delete(1);
        var find = cache.findById(base.id());
        assertThat(find.isEmpty()).isTrue();
    }

    @Test
    void whenMultiUpdateThrowException() throws OptimisticException {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(base);
        assertThatThrownBy(() -> cache.update(base))
                .isInstanceOf(OptimisticException.class);
    }
}