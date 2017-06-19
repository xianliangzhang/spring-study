package top.kou.will.aop.spring;

/**
 * Created by ZXL on 2017/6/5.
 */
public class MockTask implements ITask {
    public void execute() {
        System.out.println("Execute By MockTask...");
    }
}
