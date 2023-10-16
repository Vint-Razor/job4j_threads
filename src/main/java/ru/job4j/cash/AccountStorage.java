package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final Map<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        boolean isUpdate = false;
        if (accounts.containsKey(account.id())) {
            accounts.remove(account.id());
            isUpdate = add(account);
        }
        return isUpdate;
    }

    public synchronized void delete(int id) {
        validateId(id);
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        Account account = accounts.get(id);
        if (account != null) {
            account = new Account(account.id(), account.amount());
        }
        return Optional.ofNullable(account);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean wasTransmitted = false;
        validateId(fromId);
        validateId(toId);
        var fromAccount = accounts.get(fromId);
        var toAccount = accounts.get(toId);
        if (!fromAccount.equals(toAccount) && fromAccount.amount() >= amount) {
            int fromAmount = fromAccount.amount() - amount;
            int toAmount = toAccount.amount() + amount;
            wasTransmitted = update(new Account(fromId, fromAmount)) && update(new Account(toId, toAmount));
        }
        return wasTransmitted;
    }

    private synchronized void validateId(int id) {
        if (accounts.get(id) == null) {
            throw new IllegalArgumentException(String.format("Not found account by id = %d", id));
        }
    }
}
