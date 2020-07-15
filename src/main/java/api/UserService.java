package api;

import data.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UserService {
    private static Map<Integer, User> users = new HashMap<>();
    private static AtomicInteger lastId;

    static {
        users.put(0, new User(0, "A", "a@a.java"));
        users.put(1, new User(1, "B", "B@B.java"));
        users.put(2, new User(2, "C", "C@CCC.java"));
        lastId = new AtomicInteger(users.size());
    }

    public static void save(String name, String email) {
        int id = lastId.incrementAndGet();
        users.put(id, new User(id, name, email));
    }

    public static Collection<User> getAll() {
        return users.values();
    }

    public static void update(int userId, String name, String email) {
        users.put(userId, new User(userId, name, email));
    }

    public static User findById(int userId) {
        return users.get(userId);
    }

    public static void delete(int userId) {
        users.remove(userId);
    }
}
