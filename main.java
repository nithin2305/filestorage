package com.memory;
 
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
 
/**
 * Timeout functionnality for the memory store
 
 * @version 0.1
 */
class TimeoutMemoryStore extends TimerTask{
    private String key;
 
    /**
     * Constructor
     * @param key The key to use & store
     */
    public TimeoutMemoryStore(String key){
        this.key = key;
    }
 
    @Override
    public void run() {
        if(this.key != null & this.key.length() > 0){
            MemoryStore.delete(this.key);
        }
    }
}
 
/**
 * The memory store will keep data inside the class, for a specific amout of time

 * @version 0.1
 */
public class MemoryStore {
    //This will store any kind of object, related to a specific key value in string
    private static HashMap<String, Object> mem;
 
    /**
     * Add an object into the memory store
     * @param obj The object to store
     * @return The key stored
     */
    public static String add(Object obj){
        return add(obj, 0);
    }
 
    /**
     * Add an object into the memory store
     * @param obj The object to store
     * @param timeout The delay in ms
     * @return The key store
     */
    public static String add(Object obj, long timeout){
        //If the system is not functionnal
        if(mem == null){
            erase();
        }
 
        //Generating a unique id
        String gen = generate(16);
        while(mem.containsKey(gen)){
            gen = generate(16);
        }
 
        mem.put(gen, obj);
 
        //Create a delete operation after timeout
        if(timeout > 0){
            new Timer().schedule(new TimeoutMemoryStore(gen), timeout);
        }
 
        return gen;
    }
 
    /**
     * Get a specific object regarding key
     * @param key The key to search
     * @return The object retrieve, or null if nothing found
     */
    public static Object get(String key){
        if(mem.containsKey(key)){
            return mem.get(key);
        }else{
            return null;
        }
    }
 
    /**
     * Delete an entry from the memory store
     * @param key The key to delete
     * @return The delete value result (true if the object has been found, false in other case)
     */
    public static boolean delete(String key){
        if(mem.containsKey(key)){
            mem.remove(key);
            return true;
        }
        return false;
    }
 
    /**
     * Empty the memory store
     */
    public static void erase(){
        mem = new HashMap<String, Object>();
    }
 
    /**
     * This generate a random string of the given size
     * @param length The output length to retrieve
     * @return The generated string
     */
    private static String generate(int length){
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int charLength = chars.length();
        String pass = "";
        for(int x=0; x<length; x++){
            int i = (int) Math.floor(Math.random() * charLength);
            pass += chars.charAt(i);
        }
        return pass;
    }
}
