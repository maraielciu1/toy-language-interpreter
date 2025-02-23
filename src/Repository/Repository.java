package Repository;

import Model.PrgState;
import Utils.ADT.MyException;

import java.io.*;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> repo;
    private String logFilePath;
    public Repository(PrgState prg,String file){
        this.logFilePath = file;
        try {
            PrintWriter tmp = new PrintWriter(file);
            tmp.write("");
            tmp.close();
        } catch (FileNotFoundException ignored) {}
        repo = new java.util.ArrayList<>();
        repo.add(prg);
    }

    @Override
    public void add(PrgState state) {
        repo.add(state);
    }

    @Override
    public void logPrgStateExec(PrgState p) throws MyException {
        try {
            PrintWriter logfile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            logfile.println(p.toString());
            logfile.println("-----------------------------------------------------------------------------------");
            logfile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PrgState> getPrgList() {
        return repo;
    }

    @Override
    public void setPrgList(List<PrgState> l) {
        // set the repo to l
        repo=l;
    }

    @Override
    public void setProgramStates(List<PrgState> programStates) {
        this.repo=programStates;
    }

}
