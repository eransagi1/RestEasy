package EransFirstJavaRest.app.Database;



import EransFirstJavaRest.app.Utils.ICallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erans on 24-Aug-14.
 */
public class Database<T extends DbEntity> implements IDatabase<T> {
    private static Database instance = null;

    protected Database() {

    }

    public static void RegisterOnDBInitialization(ICallback instantiationCallback) {
        OnInstantiation = instantiationCallback;
    }

    public static <T extends DbEntity> Database getInstance() {
        if(instance == null) {
            instance = new Database<T>();
            instance.createDB();
            if(OnInstantiation != null)
            {
                OnInstantiation.OnCallback();
            }
        }
        return instance;
    }

    private static ICallback OnInstantiation = null;

    private void createDB()
    {
        theDB = new ArrayList<T>();
    }


    private List<T> theDB = null;

    @Override
    public  List<T> getAll(){// return null if empty

        List<T> foundItems = new ArrayList<T>();

        for(T item : theDB)
        {
            foundItems.add(item);
        }

        return foundItems;
    }

    @Override
    public T getByID(int ID) { // return null if not found

        for(T item : theDB)
        {
            if(item.DbIdentifier == ID) {
                return item;
            }
        }

        return null;
    }

    @Override
    public int create(T t){

        t.DbIdentifier = generateID();
        theDB.add(t);

        return t.DbIdentifier;
    }

    @Override
    public T update(T t){

        for(int i = 0; i < theDB.size(); i++)
        {
            if(theDB.get(i).DbIdentifier == t.DbIdentifier) {
                theDB.set(i,t);
                return t;
            }
        }

        return null;
    }

    @Override
    public T delete(int ID) { // return object or null if not success

        for(T item : theDB)
        {
            if(item.DbIdentifier == ID) {
                theDB.remove(item);
                return item;
            }
        }

        return null;
    }

    int ID = 0;

    private int generateID(){
        ID++;
        return ID;
    }
}
