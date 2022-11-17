package gov.example.appDemo.applicationRunner;

import gov.example.appDemo.domain.Student;
import gov.example.appDemo.repository.StudentRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;


@Order(2)
@Component
public class ParseAndSaveRunner implements ApplicationRunner {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Document doc = Jsoup.connect("https://apl.hongik.ac.kr/lecture/dbms").get();

        Elements degreeElements = doc.getElementsByAttributeValue("class", "CjVfdc");
        Elements studentsByDegree = doc.getElementsByAttributeValue("class", "n8H08c UVNKR");

        String degree, name, email;
        Integer graduation;

        for (int i = 0; i < 3; i++) {
            degree = degreeElements.get(i).text().split(" ")[0].toLowerCase();
            if (degree.length() > 10) {
                degree = degree.substring(0, 9);
            }

            Elements studentList = studentsByDegree.get(i).getElementsByTag("li");
            for (Element studentInfo : studentList) {
                String[] split = studentInfo.text().split(", ");
                name = split[0];
                email = split[1];
                graduation = Integer.valueOf(split[2]);

                studentRepository.insert(new Student(name, email, graduation, degree));
            }
        }
    }
}
