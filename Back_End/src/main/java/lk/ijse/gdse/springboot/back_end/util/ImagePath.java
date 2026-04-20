package lk.ijse.gdse.springboot.back_end.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Component
public class ImagePath {
    public String saveImage(String base64Data){
        if(base64Data == null || base64Data.isEmpty()) return null;

        // Ensure format: data:image/png;base64,xxxx
        String[] parts = base64Data.split(",");
        String imageData = parts.length > 1 ? parts[1] : parts[0];

        byte[] imageBytes = Base64.getDecoder().decode(imageData);
        String extension = "png"; // optionally detect from base64 prefix
        String filename = "uploads/company_" + System.currentTimeMillis() + "." + extension;

        File uploadDir = new File("uploads");
        if (!uploadDir.exists()) uploadDir.mkdirs();

        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(imageBytes);
            System.out.println("Image saved to file system: " + filename);
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getBase64FromFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File does not exist: " + filePath);
                return null;
            }

            // Read file bytes
            byte[] fileBytes = Files.readAllBytes(file.toPath());

            // Encode to base64
            String base64 = Base64.getEncoder().encodeToString(fileBytes);

            // Optionally add data URI prefix (detect extension)
            String extension = "";
            int dotIndex = filePath.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = filePath.substring(dotIndex + 1);
            }

            return "data:image/" + extension + ";base64," + base64;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
