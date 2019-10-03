package pl.javastart;

import pl.javastart.controller.TaskControler;

public class Main {
    public static void main(String[] args) {
        TaskControler taskControler = new TaskControler();
        taskControler.menu();
    }
}
