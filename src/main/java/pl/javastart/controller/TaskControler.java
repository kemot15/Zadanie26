package pl.javastart.controller;

import pl.javastart.entity.Category;
import pl.javastart.entity.Colors;
import pl.javastart.entity.Task;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.sound.midi.Soundbank;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TaskControler {

    private Scanner scanner = new Scanner(System.in);
    private EntityManager entityManager;

    public void menu() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
        entityManager = entityManagerFactory.createEntityManager();
        addTestData();


        while(true) {

            System.out.println(Colors.BLACK.getColor()+ "Co chcesz zrobić?");
            System.out.println("1. Pokaz zadania do zrobienia");
            System.out.println("2. Dodaj nowe zadanie");
            System.out.println("3. Pokaz wykonane zadania");
            System.out.println("4. Edytuj zadanie");
            System.out.println("5. Zaznacz wykonane zadanie");
            System.out.println("6. Wyświetl zadania wykonane");
            System.out.println("7. Wyświetl wg kategorii");
            System.out.println("0. Koniec");


            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    showTasksToDoOrArchive(0);
                    break;
                case 2:
                    addTask();
                    break;
                case 3:
                    showTasks();
                    break;
                case 4:
                    editTask();
                    break;
                case 5:
                    markAsDone();
                    break;
                case 6:
                    showTasksToDoOrArchive(1);
                    break;
                case 7:
                    showByCategory();
                    break;
                case 0:
                    entityManager.close();
                    scanner.close();
                    return;
                default:
                    System.out.println("Błędny wybór!");
            }
        }
    }
    private void addTestData() {
        entityManager.getTransaction().begin();
        entityManager.persist(new Task("test", "opis testwo", false, LocalDateTime.now(), LocalDateTime.parse("2019-10-12 12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) , Category.HOME));
        entityManager.persist(new Task("test2", "opis testwo2", true, LocalDateTime.now(), LocalDateTime.parse("2019-10-11 12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), Category.OTHER));
        entityManager.persist(new Task("test3", "opis testwo3", true, LocalDateTime.now(), LocalDateTime.parse("2019-09-11 12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), Category.WORK));
        entityManager.persist(new Task("test4", "opis testwo3", false, LocalDateTime.now(), LocalDateTime.parse("2019-09-11 12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), Category.HOME));
        entityManager.persist(new Task("test4", "opis testwo3", false, LocalDateTime.now(), LocalDateTime.parse("2019-09-11 12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), Category.HOME));
        entityManager.getTransaction().commit();
    }

    private void showTasks() {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t ORDER BY t.endDate, t.complete", Task.class);
        List<Task> resultList = query.getResultList();
        for (Task task : resultList){
            if (task.getEndDate().isAfter(LocalDateTime.now()))
                System.out.println(task.showTask(Colors.BLACK.getColor()));
            else
                System.out.println(task.showTask(Colors.RED.getColor()));
        }
    }

    private int showTasksToDoOrArchive(int complete) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.complete ='"+ complete + "'  ORDER BY t.endDate", Task.class);
        List<Task> resultList = query.getResultList();
        for (Task task : resultList){
            if (!task.isComplete()){
                System.out.println(task.showTask(Colors.BLACK.getColor()));
            }
        }
        return resultList.size();

    }

    private void addTask() {
        System.out.println("Podaj nazwę zadania");
        String taskName = scanner.nextLine();
        System.out.println("Podaj opis zadania");
        String description = scanner.nextLine();
        System.out.println("Podaj termin wykonania w formacie yyyy-MM-dd HH:mm");
        LocalDateTime endDate = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime startDate = LocalDateTime.now();
        System.out.println("Podaj numer kategorii");
        Category[] categories = Category.values();
        int catNum = 1;
        for (Category cat : categories){
            System.out.println(catNum + " " + cat.getCategoryName());
            catNum++;
        }
        int category = scanner.nextInt();
        scanner.nextLine();
        boolean complete = false;

        Task task = new Task(taskName, description, complete, startDate, endDate, Category.HOME);

        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();

    }

    private void markAsDone () {
        if (showTasksToDoOrArchive(0) == 0)
            System.out.println(Colors.RED.getColor() + "Wszystkie zadania są wykonane");
        else {
            System.out.println("Podaj id wykoannego zadania");
            Long id = scanner.nextLong();
            scanner.nextLine();
            Task task = entityManager.find(Task.class, id);

            entityManager.getTransaction().begin();
            task.setComplete(true);
            entityManager.getTransaction().commit();
        }
    }

    private void editTask () {
        showTasks();
        System.out.println("Podaj id wykoannego zadania");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Task task = entityManager.find(Task.class, id);

        entityManager.getTransaction().begin();
        menuEdition(task);
        entityManager.getTransaction().commit();
    }

    private void menuEdition (Task task){
        while (true){
            System.out.println("Co chesz edytować?");
            System.out.println("1. Nazwę zadania");
            System.out.println("2. Opis zadania");
            System.out.println("3. Date zakonczenia");
            System.out.println("0. Powrot do menu");
            int edit = scanner.nextInt();
            scanner.nextLine();

            switch (edit){
                case 1:
                    System.out.println("Podaj nowa nazwe");
                    task.setName(scanner.nextLine());
                    break;
                case 2:
                    System.out.println("Podaj nowy opis");
                    task.setInfo(scanner.nextLine());
                    break;
                case 3:
                    System.out.println("Podaj nowa date zakonczenia w formacie yyyy-MM-dd HH:mm");
                    task.setEndDate(LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                    break;
                case 0:
                    return;
            }
        }
    }

    private void showByCategory (){
        System.out.println("Podaj po jakiej kategori wyswietlic");
        Category[] categories = Category.values();
        int catNum = 1;
        for (Category cat : categories){
            System.out.println(catNum + " " + cat.getCategoryName());
            catNum++;
        }


        int category = scanner.nextInt()-1;
        scanner.nextLine();
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.category = '" + categories[category].name() + "'" , Task.class);
        List<Task> resultList = query.getResultList();
        for (Task task : resultList){
            if (!task.isComplete()){
                System.out.println(task.showTask(Colors.BLACK.getColor()));
            }
        }
    }

}
