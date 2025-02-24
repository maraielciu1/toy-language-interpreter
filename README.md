# Toy Language Interpreter

A robust toy language interpreter that transforms high-level instructions into executable statements and processes them step-by-step. This project serves both as an educational tool for language design and as a demonstration of advanced interpreter features such as execution stacks, file and symbol tables, dynamic memory management, multithreading, and a graphical user interface (GUI).

## Overview

This repository houses an interpreter for a custom-designed toy programming language. The interpreter breaks down instructions into individual statements and executes them sequentially, maintaining several key internal structures:
- **Execution Stack**: Tracks the sequence of execution.
- **Symbol Table**: Maintains variable names and their associated values.
- **Heap**: Supports dynamic memory allocation and deallocation.
- **File Table**: Manages file operations during runtime.
- **Out Table**: Collects and manages program output in an organized manner.

Additionally, the interpreter supports advanced multithreading features. It can fork execution paths, and it implements various synchronization constructs—such as barriers, conditional latches, procedures and semaphores—to manage complex concurrent operations, depending on the branch you use. A GUI is also provided for real-time visualization and interactive debugging of these internal structures.

## Features

- **Step-by-Step Execution**
  - Converts high-level instructions into discrete statements.
  - Uses an execution stack to manage the order of statement processing.
- **Memory & Resource Management**
  - **Symbol Table**: Tracks variable names and their values.
  - **Heap**: Manages dynamic memory allocation.
  - **File Table**: Handles file descriptors and file operations.
  - **Out Table**: Organizes and displays program output.
- **Multithreading and Concurrency**
  - **Fork Support**: Allows the interpreter to fork execution paths for parallel processing.
  - **Advanced Synchronization Primitives**: Implements barriers, conditional latches, and semaphores for managing thread synchronization.
- **Graphical User Interface (GUI)**
  - Visualizes the execution stack, symbol table, file table, heap, and out table in real time.
  - Enables interactive stepping through code execution.
- **Robust Error Handling**
  - Provides detailed error messages for both syntax and runtime errors.
- **Extensible Architecture**
  - Modular design that makes it easy to add new language features and improvements.
