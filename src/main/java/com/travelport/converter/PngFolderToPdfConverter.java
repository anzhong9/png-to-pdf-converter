package com.travelport.converter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;


public class PngFolderToPdfConverter {

    private static final String BASE_DIR =
            "H:\\travelport 22\\2026\\test 3.4lakh";
    private static final String PROGRESS_FILE = ".progress.txt";
    private static final String STOP_FILE = ".stop-after-current";

    public static void main(String[] args) {
        try {
            new PngFolderToPdfConverter().processAllFolders();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processAllFolders() throws IOException, DocumentException {

        File baseDir = new File(BASE_DIR);
        File[] folders = baseDir.listFiles(File::isDirectory);
        File progressFile = new File(baseDir, PROGRESS_FILE);
        File stopFile = new File(baseDir, STOP_FILE);

        if (folders == null || folders.length == 0) {
            System.out.println("No folders found.");
            return;
        }

        // Folder names still sorted normally (200-205, 205-210…)
        Arrays.sort(folders, Comparator.comparing(File::getName));

        for (File folder : folders) {
            System.out.println("\nProcessing folder: " + folder.getName());
            boolean created = createPdfFromFolder(folder);

            if (created) {
                appendProgress(progressFile, folder.getName());
            }

            if (stopFile.exists()) {
                System.out.println("\nStop file detected. Stopping after current folder.");
                break;
            }
        }

        System.out.println("\nAll folders processed.");
    }

    private boolean createPdfFromFolder(File folder)
            throws IOException, DocumentException {

        File pdfFile = getPdfFileForFolder(folder);
        if (pdfFile.exists()) {
            System.out.println("  PDF already exists, skipping: " + pdfFile.getAbsolutePath());
            return false;
        }

        File[] pngFiles = folder.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".png"));

        if (pngFiles == null || pngFiles.length == 0) {
            System.out.println("  No PNGs found, skipping.");
            return false;
        }

        // ✅ CRITICAL FIX: sort by file timestamp, not name
        Arrays.sort(pngFiles, Comparator.comparingLong(File::lastModified));

        Document document = null;
        PdfWriter writer = null;

        try {
            Image firstImage = Image.getInstance(pngFiles[0].getAbsolutePath());
            document = new Document(new Rectangle(firstImage.getWidth(), firstImage.getHeight()));
            document.setMargins(0, 0, 0, 0);
            writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            for (int i = 0; i < pngFiles.length; i++) {
                Image image = i == 0
                        ? firstImage
                        : Image.getInstance(pngFiles[i].getAbsolutePath());

                if (i > 0) {
                    // Set page size BEFORE newPage
                    document.setPageSize(new Rectangle(
                            image.getWidth(),
                            image.getHeight()
                    ));
                    document.newPage();
                }

                image.setAbsolutePosition(0, 0);
                document.add(image);

                if ((i + 1) % 500 == 0) {
                    System.out.println("  Processed " + (i + 1) + " images...");
                }
            }

            System.out.println("  PDF created: " + pdfFile.getAbsolutePath());
            return true;

        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    private File getPdfFileForFolder(File folder) {
        String pdfName = folder.getName() + ".pdf";
        return new File(folder, pdfName);
    }

    private void appendProgress(File progressFile, String folderName) throws IOException {
        try (FileOutputStream stream = new FileOutputStream(progressFile, true)) {
            String line = folderName + System.lineSeparator();
            stream.write(line.getBytes());
        }
    }
}
