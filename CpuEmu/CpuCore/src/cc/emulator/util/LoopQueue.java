package cc.emulator.util;

/**
 * @author Shao Yongqing
 * Date: 2017/9/14.
 */
public class LoopQueue {

    int max = 5;
    int tail=0;
    int head=0;

    public LoopQueue(int max) {
        this.max = max;
    }

    public void reset() {
        head=0;
        tail=0;
    }

    public int getTail() {
        return tail;
    }

    public int getHead() {
        return head;
    }

    public void headNextValue() {
        head = (head+1)%max;
    }
    public void tailNextValue() {
        tail = (tail+1)%max;
//        tail++;
//        if(tail == QUEUE_MAX)
//            tail = 0;
    }

    public boolean isEmpty() {
        return (head == tail);
    }

    public boolean isFull() {
        return (tail+1%max)==head;
    }

    public int size(){
        return (tail - head + max)%max;
    }
    public int getMax(){
        return max;
    }
}
