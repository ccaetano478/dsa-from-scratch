package dev.me.arrays;

public class MyArray {

    private Integer size;
    private final Object[] data;

    public MyArray(Integer capacity) {
        this.size = 0;
        data = new Object[capacity];
    }

    public  Object get(Integer index){
        return data[index];
    }

    public void push(Object obj){
        data[size] = obj;
        size++;
    }

    public Object pop(){
        size--;
        data[size] = null;
        return data[size-1];
    }

}
