package com.travelport.converter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class PngToPdfBatchConverter {
    
    private static final int BATCH_SIZE = 5010;
    private static final String SOURCE_DIR = "H:\\travelport 22\\2026\\jan 26\\Deal 6 Rs 15 6 lakh\\6lakhpending";
    private static final String OUTPUT_DIR = "H:\\travelport 22\\2026\\jan 26\\Deal 6 Rs 15 6 lakh\\PDF_Output";
    
    public static void main(String[] args) {
        try {
            PngToPdfBatchConverter converter = new PngToPdfBatchConverter();
            converter.convertPngsToPdfInBatches();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void convertPngsToPdfInBatches() throws IOException, DocumentException {
        // Create output directory if it doesn't exist
        File outputDir = new File(OUTPUT_DIR);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
            System.out.println("Created output directory: " + OUTPUT_DIR);
        }
        
        // Get all PNG files from source directory
        File sourceDir = new File(SOURCE_DIR);
        File[] pngFiles = sourceDir.listFiles((dir, name) -> 
            name.toLowerCase().endsWith(".png"));
        
        if (pngFiles == null || pngFiles.length == 0) {
            System.out.println("No PNG files found in directory: " + SOURCE_DIR);
            return;
        }
        
        // Sort files by name to ensure consistent ordering
        Arrays.sort(pngFiles, Comparator.comparing(File::getName));
        
        int totalFiles = pngFiles.length;
        int totalBatches = (int) Math.ceil((double) totalFiles / BATCH_SIZE);
        
        System.out.println("Total PNG files found: " + totalFiles);
        System.out.println("Batch size: " + BATCH_SIZE);
        System.out.println("Total batches to create: " + totalBatches);
        System.out.println("Starting conversion...\n");
        
        // Process files in batches
        for (int batchNum = 0; batchNum < totalBatches; batchNum++) {
            int startIndex = batchNum * BATCH_SIZE;
            int endIndex = Math.min(startIndex + BATCH_SIZE, totalFiles);
            
            String pdfFileName = String.format("Batch_%03d_Images_%d_to_%d.pdf", 
                batchNum + 1, startIndex + 1, endIndex);
            String pdfPath = OUTPUT_DIR + File.separator + pdfFileName;
            
            System.out.println("Processing Batch " + (batchNum + 1) + " of " + totalBatches);
            System.out.println("  Images: " + (startIndex + 1) + " to " + endIndex);
            System.out.println("  Output: " + pdfFileName);
            
            createPdfFromImages(pngFiles, startIndex, endIndex, pdfPath);
            
            System.out.println("  Status: Completed\n");
        }
        
        System.out.println("All batches completed successfully!");
        System.out.println("Total PDFs created: " + totalBatches);
    }
    
    private void createPdfFromImages(File[] allFiles, int startIndex, int endIndex, String pdfPath) 
            throws IOException, DocumentException {
        
        Document document = null;
        PdfWriter writer = null;
        
        try {
            // Create the first image to get dimensions
            File firstImageFile = allFiles[startIndex];
            Image firstImage = Image.getInstance(firstImageFile.getAbsolutePath());
            
            // Create document with page size matching the image
            document = new Document(new com.itextpdf.text.Rectangle(
                firstImage.getWidth(), firstImage.getHeight()));
            
            writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
            
            // Add all images in this batch
            for (int i = startIndex; i < endIndex; i++) {
                File imageFile = allFiles[i];
                
                try {
                    Image image = Image.getInstance(imageFile.getAbsolutePath());
                    
                    // Set page size to match current image
                    document.setPageSize(new com.itextpdf.text.Rectangle(
                        image.getWidth(), image.getHeight()));
                    
                    // Add new page for each image (except the first one)
                    if (i > startIndex) {
                        document.newPage();
                    }
                    
                    // Position image at (0,0)
                    image.setAbsolutePosition(0, 0);
                    document.add(image);
                    
                    // Progress indicator every 500 images
                    if ((i - startIndex + 1) % 500 == 0) {
                        System.out.println("    Processed " + (i - startIndex + 1) + 
                            " images in current batch...");
                    }
                    
                } catch (Exception e) {
                    System.err.println("  Error processing image: " + imageFile.getName());
                    System.err.println("  Error: " + e.getMessage());
                    // Continue with next image
                }
            }
            
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
