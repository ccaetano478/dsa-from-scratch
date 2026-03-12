package dev.me.arrays;
@SuppressWarnings("unchecked")
public class MyGenericArray<T> {
    private Integer size;
    private final T[] data;


    public MyGenericArray(Integer capacity) {
        this.size = 0;
        data = (T[]) new Object[capacity];
    }

    public  Object get(Integer index){
        return data[index];
    }

    public void push(Object obj){
        data[size] = (T) obj;
        size++;
    }

    public Object pop(){
        data[size] = null;
        size--;
        return data[size];
    }
}
