package EransFirstJavaRest.app.Database;

import java.util.List;

/**
 * Created by erans on 24-Aug-14.
 */
public interface IDatabase<T extends DbEntity> {
    List<T> getAll(); // return null if empty
    T getByID(int ID); // return null if not found
    int create(T t); // return 0 if not success, otherwise return ID
    T delete(int ID); // return object or null if not success
    T update(T t); // return object or null if not success
}

