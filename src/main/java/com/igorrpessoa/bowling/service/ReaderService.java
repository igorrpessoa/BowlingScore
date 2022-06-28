package com.igorrpessoa.bowling.service;

import com.igorrpessoa.bowling.exception.ErrorMessagesEnum;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Scanner;

@Service
public class ReaderService {

    public String readFile(String path) throws FileNotFoundException {
        StringBuilder data = new StringBuilder();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data.append(myReader.nextLine()).append("\n");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(ErrorMessagesEnum.INPUT_ERROR_FILE_NOT_FOUND.getDescription());
        }
        return data.toString();
    }

    public String readFromConsole() {
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }
}
