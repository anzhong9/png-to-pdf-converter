package com.travelport.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class PngBatchFolderConverter {

    private static final String SOURCE_DIR =
            "H:\\travelport 22\\2026\\Deal4img";
    private static final int BATCH_SIZE = 5010;

    public static void main(String[] args) {
        try {
            new PngBatchFolderConverter().batchAndConvert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void batchAndConvert() throws IOException, DocumentException {
        File sourceDir = new File(SOURCE_DIR);
        if (!sourceDir.isDirectory()) {
            System.out.println("Source directory not found: " + SOURCE_DIR);
            return;
        }

        File[] pngFiles = sourceDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
        if (pngFiles == null || pngFiles.length == 0) {
            System.out.println("No PNG files found in: " + SOURCE_DIR);
            return;
        }

        // Use timestamp ordering for random filenames.
        Arrays.sort(pngFiles, Comparator.comparingLong(File::lastModified));

        int totalFiles = pngFiles.length;
        int totalBatches = (int) Math.ceil((double) totalFiles / BATCH_SIZE);

        System.out.println("Total PNG files: " + totalFiles);
        System.out.println("Batch size: " + BATCH_SIZE);
        System.out.println("Total batches: " + totalBatches);

        int index = 0;
        for (int batch = 1; batch <= totalBatches; batch++) {
            int start = index + 1;
            int end = Math.min(index + BATCH_SIZE, totalFiles);

            String folderName = start + "-" + end;
            File batchFolder = new File(sourceDir, folderName);
            if (!batchFolder.exists() && !batchFolder.mkdirs()) {
                throw new IOException("Failed to create batch folder: " + batchFolder.getAbsolutePath());
            }

            System.out.println("\nBatch " + batch + ": " + folderName);

            for (int i = start; i <= end; i++) {
                File fileToMove = pngFiles[index];
                Path targetPath = new File(batchFolder, fileToMove.getName()).toPath();
                Files.move(fileToMove.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                index++;

                if ((i - start + 1) % 500 == 0) {
                    System.out.println("  Moved " + (i - start + 1) + " images...");
                }
            }

            createPdfFromFolder(batchFolder);
        }

        System.out.println("\nAll batches completed.");
    }

    private void createPdfFromFolder(File folder) throws IOException, DocumentException {
        File[] pngFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
        if (pngFiles == null || pngFiles.length == 0) {
            System.out.println("  No PNGs found, skipping.");
            return;
        }

        Arrays.sort(pngFiles, Comparator.comparingLong(File::lastModified));

        String pdfName = folder.getName() + ".pdf";
        File pdfFile = new File(folder, pdfName);

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
                    document.setPageSize(new Rectangle(image.getWidth(), image.getHeight()));
                    document.newPage();
                }

                image.setAbsolutePosition(0, 0);
                document.add(image);

                if ((i + 1) % 500 == 0) {
                    System.out.println("  PDF images processed: " + (i + 1));
                }
            }

            System.out.println("  PDF created: " + pdfFile.getAbsolutePath());
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }
}