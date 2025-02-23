package View.Command;

import Controller.Controller;
import Utils.ADT.MyException;

public class RunExample extends Command {
    private Controller ctr;
    public RunExample(String key, String description, Controller ctr) {
        super(key, description);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try{
            ctr.allStep();
            System.out.println("Run command executed");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
