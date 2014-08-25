package EransFirstJavaRest.app.Utils;

public class ArrayWrapper<T extends InstanceGetter<T>>{
    public ArrayWrapper(T singleObject)
    {
        total = 1;
        members = singleObject.GetArrayInstance(1);
    }

    public ArrayWrapper(T[] objectArray)
    {
        total = objectArray.length;
        members = objectArray;
    }

    public int total;
    public T[] members;
}
