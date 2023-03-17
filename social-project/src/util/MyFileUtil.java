package util;

import java.io.*;
import java.util.List;

public class MyFileUtil<E> {
    public MyFileUtil() {
    }

    public List<E> readDataFromFile(String filePath) {
        if(filePath == null || filePath.trim().equals("")){
            return null;
        }
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<E>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public void writeDataFromFile(List list, String filePath){
        if(filePath == null || filePath.trim().equals("")){
            return;
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))){
            objectOutputStream.writeObject(list);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
    public void writeDataFromFileAddNew(List<E> list,String filePath){
        if(filePath == null || filePath.trim().equals("")){
            return;
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath, true))){
            objectOutputStream.writeObject(list);
        }catch (IOException e){
            System.out.println(e.getMessage());

        }

    }
}
