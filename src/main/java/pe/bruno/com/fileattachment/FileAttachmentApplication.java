package pe.bruno.com.fileattachment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FileAttachmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileAttachmentApplication.class, args);
	}

}
