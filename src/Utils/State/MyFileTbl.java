package Utils.State;

import Model.Value.StringValue;
import Utils.ADT.MyDictionary;
import Utils.ADT.MyException;
import Utils.ADT.MyIDictionary;

import java.io.BufferedReader;
import java.util.Set;

public class MyFileTbl implements IMyFileTbl {
    private MyIDictionary<StringValue, BufferedReader> fileTbl;
    public MyFileTbl(){
        this.fileTbl=new MyDictionary<>();
    }
    @Override
    public void openFile(StringValue fileName) throws MyException {
        // open the file with the name fileName
        // if the file does not exist, throw an exception
        // if the file is already open, throw an exception
        // if the file is opened successfully, add it to the file table
        if(fileTbl.contains(fileName)){
            throw new MyException("File already open");
        }
        try{
            BufferedReader br = new BufferedReader(new java.io.FileReader(fileName.getVal()));
            fileTbl.put(fileName, br);
        }catch (java.io.FileNotFoundException e){
            throw new MyException("File not found");
        }
    }

    @Override
    public void closeFile(StringValue fileName) throws MyException {
        // close the file with the name fileName
        // if the file is not open, throw an exception
        // if the file is closed successfully, remove it from the file table
        if(!fileTbl.contains(fileName)){
            throw new MyException("File not open");
        }
        try{
            fileTbl.lookUp(fileName).close();
            fileTbl.remove(fileName);
        }catch (java.io.IOException e){
            throw new MyException("Error closing file");
        }
    }

    @Override
    public int readFile(StringValue fileName) throws MyException {
        BufferedReader br = fileTbl.lookUp(fileName);
        try{
            String line = br.readLine();
            if(line==null){
                throw new MyException("End of file");
            }
            return Integer.parseInt(line);
        }catch (java.io.IOException e){
            throw new MyException("Error reading file");
        }
    }

    @Override
    public String getFileTableList() {
        // return a string with the list of files from the file table, each on a new line
        StringBuilder result = new StringBuilder();
        result.append("\n");
        for (var key : fileTbl.getKeys()) {
            result.append(key).append("\n");
        }
        return result.toString();
    }
    @Override
    public Iterable<StringValue> getKeys() {
        return fileTbl.getKeys();
    }

    @Override
    public Set<String> keySet() {
        return fileTbl.keySet();
    }
}
