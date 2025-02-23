package Controller;

import Model.PrgState;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.IRepository;
import Utils.ADT.MyException;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Pair {
    final PrgState first;
    final MyException second;

    public Pair(PrgState first, MyException second) {
        this.first = first;
        this.second = second;
    }
}

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public List<PrgState> getProgramStates() {
        return repo.getPrgList();
    }

    public Controller(IRepository repo){
        this.repo = repo;
    }
    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddresses, List<Integer> heapAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> ( symTableAddresses.contains(e.getKey()) || heapAddresses.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> p.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(symTableAddresses, getAddressesFromHeap(p.getHeap().getContent().values()), p.getHeap().getContent())));
    }

//    public void oneStepForAllPrograms(List<PrgState> programStates) throws MyException, InterruptedException {
//        if (executor == null || executor.isShutdown()) {
//            executor = Executors.newFixedThreadPool(2);
//        }
//
//        programStates.forEach(programState -> {
//            try {
//                repo.logPrgStateExec(programState);
//            } catch (MyException e) {
//                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
//            }
//        });
//
//        List<Callable<PrgState>> callList = programStates.stream()
//                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
//                .collect(Collectors.toList());
//
//        List<Pair> newProgramList;
//        newProgramList = executor.invokeAll(callList).stream()
//                .map(future -> {
//                    try {
//                        return new Pair(future.get(), null);
//                    } catch (ExecutionException | InterruptedException e) {
//                        if (e.getCause() instanceof MyException)
//                            return new Pair(null, (MyException) e.getCause());
//                        System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
//                        return null;
//                    }
//                }).filter(Objects::nonNull)
//                .filter(pair -> pair.first != null || pair.second != null)
//                .collect(Collectors.toList());
//
//        for (Pair error : newProgramList) {
//            if (error.second != null)
//                throw error.second;
//        }
//        programStates.addAll(newProgramList.stream().map(pair -> pair.first).toList());
//
//        programStates.forEach(programState -> {
//            try {
//                repo.logPrgStateExec(programState);
//            } catch (MyException e) {
//                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
//            }
//        });
//
//        repo.setProgramStates(programStates);
//    }

    public void oneStepForAllPrograms(List<PrgState> programStates) throws MyException, InterruptedException {
        programStates.forEach(programState -> {
            try {
                repo.logPrgStateExec(programState);
                //display();
            } catch (MyException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        List<Callable<PrgState>> callList = programStates.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        List<Pair> newProgramList;
        newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return new Pair(future.get(), null);
                    } catch (ExecutionException | InterruptedException e) {
                        if (e.getCause() instanceof MyException)
                            return new Pair(null, (MyException) e.getCause());
                        System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                        return null;
                    }
                }).filter(Objects::nonNull)
                .filter(pair -> pair.first != null || pair.second != null)
                .collect(Collectors.toList());

        for (Pair error: newProgramList)
            if (error.second != null)
                throw error.second;
        programStates.addAll(newProgramList.stream().map(pair -> pair.first).toList());

        programStates.forEach(programState -> {
            try {
                repo.logPrgStateExec(programState);
                //display();
            } catch (MyException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        repo.setProgramStates(programStates);
        executor.shutdownNow();
    }

    public void oneStep() throws InterruptedException, MyException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programStates = removeCompletedPrg(repo.getPrgList());
        oneStepForAllPrograms(programStates);
        conservativeGarbageCollector(programStates);
        //programStates = removeCompletedPrg(repository.getProgramList());
        executor.shutdownNow();
        //repository.setProgramStates(programStates);
    }

    public void allStep() throws MyException, InterruptedException {
        if (executor == null || executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(2); // Initialize the executor
        }
        List<PrgState> programStates = removeCompletedPrg(repo.getPrgList());
        while (!programStates.isEmpty()) {
            oneStepForAllPrograms(programStates);
            conservativeGarbageCollector(programStates);
            programStates = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow(); // Clean up the executor after execution
        repo.setProgramStates(programStates);
    }


    public void display() {
        this.repo.getPrgList().forEach(prg -> System.out.println(prg.toString() + "\n"));
    }
    public Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue) //gets all the refValues
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();}) //each refValue gets replace by it s adress
                .collect(Collectors.toList());
    }
    public List<Integer> getAddressesFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream().filter(p -> !p.isNotCompleted()).collect(Collectors.toList());
    }

    public void setProgramStates(List<PrgState> programStates) {
        repo.setProgramStates(programStates);
    }
}
