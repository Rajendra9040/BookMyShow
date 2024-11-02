package com.scaler.bookmyshow.utils;

import com.scaler.bookmyshow.advice.exception.ProgramException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BmsFileUtils {
    public static final int THRESHOLD_ENTRIES = 50;
    public static final long THRESHOLD_SIZE = 1_000_000_000L;
    public static final double THRESHOLD_RATIO = 10.0;

    public static void deleteFileQuietly(File file) {
        try {
            if (null != file) {
                java.nio.file.Files.delete(file.toPath());
            }
        } catch (Exception ex) {
            log.error("Error occurred while deleting file", ex);
        }
    }

    public static void deleteAllFileQuietly(List<File> files) {
        files.forEach(BmsFileUtils::deleteFileQuietly);
    }


    public static String getFileNameFromPath(String filePath) {
        String fileNameWithExtension = filePath.substring(filePath.lastIndexOf("/") + 1);
        return fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.'));
    }

    public static List<File> unZip(File zipFile) {
        List<File> extractedFiles = new ArrayList<>();
        File parentDir = createTempDirectory();
        try (ZipFile zip = new ZipFile(zipFile)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            int totalEntries = 0;
            long totalUncompressedSize = 0;
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                totalEntries++;
                Path entryPath = parentDir.toPath().resolve(entry.getName()).normalize();
                validateEntry(entry, parentDir, extractedFiles, totalEntries);
                if (entry.isDirectory()) {
                    Files.createDirectories(entryPath);
                } else {
                    extractFileEntry(zip, entry, entryPath, extractedFiles, totalUncompressedSize);
                }
            }
            log.info("Extraction complete. Total entries: {}", totalEntries);
        } catch (IOException e) {
            deleteAllFileQuietly(extractedFiles);
            throw new ProgramException("Exception while unzipping file.", e);
        }
        return extractedFiles;
    }

    private static void extractFileEntry(ZipFile zip, ZipEntry entry, Path entryPath, List<File> extractedFiles, long totalUncompressedSize) throws IOException {
        Files.createDirectories(entryPath.getParent());
        try (InputStream inputStream = new BufferedInputStream(zip.getInputStream(entry)); OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(entryPath))) {
            byte[] buffer = new byte[2048];
            int bytesRead;
            long entryUncompressedSize = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                entryUncompressedSize += bytesRead;
                totalUncompressedSize += bytesRead;
                validateCompressionAndSize(entryUncompressedSize, entry, totalUncompressedSize, entryPath, extractedFiles);
            }
        }
        extractedFiles.add(entryPath.toFile());
    }

    public static File createTempDirectory() {
        String tempDir = "temp" + UUID.randomUUID();
        File parentDir = new File(tempDir);
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new ProgramException("Failed to create directory: " + parentDir);
        }
        return parentDir;
    }

    private static void validateEntry(ZipEntry entry, File parentDir, List<File> extractedFiles, int totalEntries) {
        // Abort if too many entries
        if (totalEntries > THRESHOLD_ENTRIES) {
            deleteAllFileQuietly(extractedFiles);
            throw new ProgramException("Too many entries in the ZIP file.");
        }
        // Validate path for Zip Slip vulnerability
        Path entryPath = parentDir.toPath().resolve(entry.getName()).normalize();
        if (!entryPath.startsWith(parentDir.toPath())) {
            deleteAllFileQuietly(extractedFiles);
            throw new ProgramException("Invalid ZIP entry: " + entry.getName());
        }
    }

    private static void validateCompressionAndSize(long entryUncompressedSize, ZipEntry entry, long totalUncompressedSize, Path entryPath, List<File> extractedFiles) throws IOException {
        double compressionRatio = (double) entryUncompressedSize / entry.getCompressedSize();
        if (compressionRatio > THRESHOLD_RATIO || totalUncompressedSize > THRESHOLD_SIZE) {
            deleteAllFileQuietly(extractedFiles);
            Files.deleteIfExists(entryPath);
            throw new ProgramException("Suspicious compression ratio or Uncompressed data size detected.");
        }
    }
}

