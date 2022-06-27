package com.igorrpessoa.bowling.service;

import org.apache.logging.log4j.util.Strings;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GameService.class, ScoreService.class, PrintService.class, ReaderService.class})
@EnableConfigurationProperties
public class ReaderServiceTest {

    @Mock
    ScoreService scoreService;
    @Mock
    PrintService printService;
    @Mock
    GameService gameService;

    @Autowired
    ReaderService readerService;

    @Test
    public void givenFilePathWhenReadFileThenReturnContent() throws IOException {
        String path = FileSystems.getDefault().getPath("").toAbsolutePath() + "\\" +  "src\\test\\resources\\positive\\scores.txt";

        String fileContent = readerService.readFile(path);

        Assert.assertFalse(Strings.isEmpty(fileContent));
    }
}