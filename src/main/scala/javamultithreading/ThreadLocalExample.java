package javamultithreading;

import java.text.SimpleDateFormat;

public class ThreadLocalExample {
}

class ThreadSafeFormatter{
    public static  ThreadLocal dateFormatter = ThreadLocal.withInitial(()->new SimpleDateFormat("yyyy-mm-dd"));
}
class UserService{
    public String dateOfBirth(int userId){
        String data = "2018-04-19";
        // get the threadlocal vallue for the thread which call the function
        SimpleDateFormat date = (SimpleDateFormat) ThreadSafeFormatter.dateFormatter.get();
        date.format(data);
    }
}