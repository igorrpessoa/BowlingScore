package com.igorrpessoa.bowling.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.InvalidPathException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GameService.class, ScoreService.class, PrintService.class, ReaderService.class})
@EnableConfigurationProperties
public class GameServiceTest {

    @Mock
    ScoreService scoreService;
    @Mock
    PrintService printService;
    @Mock
    ReaderService readerService;

    @Autowired
    GameService gameService;

    @Test
    public void givenPathWhenCallTreatFilePathThenAddTextFileExtension() throws IOException {

        String path = "anyPath";
        String formatedPath = gameService.treatFilePath(path);

        Assert.assertNotNull(formatedPath);
        Assert.assertEquals("anyPath.txt", formatedPath);
    }

    @Test
    public void givenInvalidPathWhenCallTreatFilePathThrowException() throws IOException {

        String path = "";
        InvalidPathException invalidPathException = Assert.assertThrows(InvalidPathException.class,
                () -> {
                    gameService.treatFilePath(path);
                });

        Assert.assertTrue(invalidPathException.getMessage().contains("The path is null or empty. Please insert a correct value"));

    }
}