package Utils.State;

import Model.Value.StringValue;
import Utils.ADT.MyException;

import java.util.Set;

public interface IMyFileTbl {
    void openFile(StringValue fileName) throws MyException;
    void closeFile(StringValue fileName) throws MyException;
    int readFile(StringValue fileName) throws MyException;
    String getFileTableList();
    Iterable<StringValue> getKeys();

    Set<String> keySet();
}
