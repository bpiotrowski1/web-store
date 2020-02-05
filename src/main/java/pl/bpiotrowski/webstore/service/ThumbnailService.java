package pl.bpiotrowski.webstore.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class ThumbnailService {

    public void save(MultipartFile thumbnail) {
        try {
            byte[] bytes = thumbnail.getBytes();
            File file = new File("test.jpg");
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(bytes);
            stream.close();
        }
        catch (Exception e) {
            return;
        }
    }

}
