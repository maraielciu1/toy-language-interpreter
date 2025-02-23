package Utils.State;

import java.util.List;

public interface IMyOut {
    String toString();
    void add(String s);
    String[] toList();
    String getOutList();

    List<String> getList();
}
