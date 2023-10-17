package ru.job4j.cash;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AccountStorageTest {

    @Test
    void whenAdd() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalArgumentException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
        assertThat(storage.add(new Account(2, 200))).isTrue();
        assertThat(storage.add(new Account(2, 300))).isFalse();
    }

    @Test
    void whenUpdate() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.update(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalArgumentException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(200);
        assertThat(storage.update(new Account(1, 300))).isTrue();
        assertThat(storage.update(new Account(2, 300))).isFalse();
    }

    @Test
    void whenDelete() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        assertThat(storage.getById(1)).isEmpty();
    }

    @Test
    void whenTransfer() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalArgumentException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalArgumentException("Not found account by id = 2"));
        assertThat(firstAccount.amount()).isEqualTo(0);
        assertThat(secondAccount.amount()).isEqualTo(200);
        assertThat(storage.transfer(1, 2, 100)).isFalse();
        assertThat(storage.transfer(2, 1, 100)).isTrue();
    }
}