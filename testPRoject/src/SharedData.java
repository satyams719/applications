import java.util.concurrent.locks.ReentrantLock;

public class SharedData {

    public synchronized void sharedMethod(ReentrantLock reentrantLock){
            reentrantLock.lock();
            System.out.println("inside the shared method accessed by thread "+ Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            }catch (InterruptedException ignore){

            }finally {
                System.out.println("releasing lock by thread "+ Thread.currentThread().getName());
                reentrantLock.unlock();
            }

    }
}
