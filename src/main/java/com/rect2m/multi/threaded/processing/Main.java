package com.rect2m.multi.threaded.processing;

import static com.rect2m.multi.threaded.processing.faker.DataGenerator.generateData;
import static com.rect2m.multi.threaded.processing.parallelProcess.ParallelDataProcessing.processData;
import static com.rect2m.multi.threaded.processing.database.CreateDatabase.createTable;
import static com.rect2m.multi.threaded.processing.sqlQueries.DataProcessing.startDataProcessing;

import com.rect2m.multi.threaded.processing.parallelProcess.ParallelDataProcessing;
import com.rect2m.multi.threaded.processing.sqlQueries.DataProcessing;

public class Main {

    public static void main(String[] args) {

        //створення бази даних
        //createTable();

        //створення даних
        //generateData();

        //розділення записів
        //processData();

        //виконання запитів
        //startDataProcessing();

        //порівняння однопотокової та багатопотокової версій
//        long startTime = System.currentTimeMillis();
//        DataProcessing.startDataProcessing(); // Багатопотокова версія
//        long endTime = System.currentTimeMillis();
//        System.out.println(
//                "Час виконання багатопотокової версії: " + (endTime - startTime) + " мс");
//
//        startTime = System.currentTimeMillis();
//        ParallelDataProcessing.processData(); // Однопотокова версія
//        endTime = System.currentTimeMillis();
//        System.out.println("Час виконання однопотокової версії: " + (endTime - startTime) + " мс");

    }
}