package ku.cs.shop.services;

public interface DataSource<T>{
    T readData();
    void writeData(T t);
}
