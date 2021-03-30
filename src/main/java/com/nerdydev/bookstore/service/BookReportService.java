package com.nerdydev.bookstore.service;

import com.nerdydev.bookstore.model.Book;
import com.nerdydev.bookstore.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

public class BookReportService {

    private BookRepository bookRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookReportService.class);
    @Value("reports")
    String path;
    private final SimpleDateFormat folderNameFormat = new SimpleDateFormat("MM-dd-yyyy");

    @Autowired
    public void setBookRepository(BookRepository bookRepository1) {
        this.bookRepository = bookRepository1;
    }

    public File generateReport() {
        Iterable<Book> bookDtos = bookRepository.findAll();
        final String[] header = new String[] {"id","title","author","price"};
        try {
            Date currentDate = new Date();
            String folderName = folderNameFormat.format(currentDate);
            File folder = new File(path + "/" + folderName);
            if (!folder.exists()) {
                boolean flag = folder.mkdirs();
                if (!flag) {
                    LOGGER.error("Report file or folder could not be created");
                }
            }
            File reportFile = new File(folder.getAbsolutePath()
                    + File.pathSeparator + "book_report" + currentDate.getTime() + ".csv");
            OutputStream outputStream = new FileOutputStream(reportFile);

            OutputStreamWriter fileWriter = new OutputStreamWriter(outputStream);
            ICsvMapWriter mapWriter = new CsvMapWriter(fileWriter, CsvPreference.STANDARD_PREFERENCE);
            mapWriter.writeHeader(header);

            for (Book book: bookDtos) {
                final Map<String, Object> record = new HashMap<>();
                record.put(header[0], book.getBookId());
                record.put(header[1], book.getTitle());
                record.put(header[2], book.getAuthor());
                record.put(header[3], book.getPrice());

                mapWriter.write(record, header);
            }
            fileWriter.flush();
            mapWriter.close();
            fileWriter.close();
            return reportFile;
        } catch (IOException e) {
            LOGGER.error("IOException occurred", e);
        }

        return null;
    }

}
