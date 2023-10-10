package ru.job4j.ref;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserCacheTest {
    private final UserCache userCache = new UserCache();
    private final User jack = User.of("Jack");
    private final User bill = User.of("Bill");
    private final User tod = User.of("Tod");

    @BeforeEach
    public void init() {
        userCache.add(jack);
        userCache.add(bill);
        tod.setId(10);
    }

    @Test
    void whenFindObjectThenNotEqualObject() {
        assertThat(userCache.findById(bill.getId())).isNotEqualTo(bill);
    }

    @Test
    void whenNameUserFindObjectThenEqualUserName() {
        assertThat(userCache.findById(bill.getId()).getName()).isEqualTo(bill.getName());
    }

    @Test
    void whenNotFoundIdThenNull() {
        assertThat(userCache.findById(tod.getId())).isNull();
    }

    @Test
    void whenNotValidIdThenException() {
        int notValidId = -1;
        assertThatThrownBy(() -> userCache.findById(notValidId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenListUsersThenContainCloneUser() {
        assertThat(userCache.findAll()).doesNotContain(bill, jack)
                .isNotEmpty();
    }

    @Test
    void whenNameUserInListUsersThenEqualCloneUser() {
        assertThat(userCache.findAll().get(bill.getId() - 1).getName()).isEqualTo(bill.getName());
    }
}