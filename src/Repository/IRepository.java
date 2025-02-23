package Repository;

import Model.PrgState;
import Utils.ADT.MyException;

import java.util.List;

public interface IRepository {
    void add (PrgState state);
    void logPrgStateExec(PrgState p) throws MyException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> l);

    void setProgramStates(List<PrgState> programStates);
}
