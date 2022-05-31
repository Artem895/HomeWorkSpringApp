package com.netcracker.service;

import com.netcracker.exeption.MailAdreesAlweysExist;
import com.netcracker.model.User;
import com.netcracker.repository.Userrepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class Userservice {

    private  final Userrepository userrepository;
    @Autowired
    public Userservice(Userrepository userrepository) {
        this.userrepository = userrepository;
    }

    @Autowired
    MailSender mailSender;

    @Value("${upload.path}")
    private String uploadpath;

    public List<User> findAll(){
        return userrepository.findAll();
    }

    public  List<String> finAllemails(){
        return userrepository.findAllEmail();
    }

    public User saveUser(User user) throws MailAdreesAlweysExist{
        List<String> allemails=userrepository.findAllEmail();
        for(String email:allemails){
            if(user.getMailadress().equals(email)){
                throw new MailAdreesAlweysExist("Mailadress is already exists");
            }
        }
        return userrepository.save(user);
    }

    public List<User> findbynameandlastname(String name,String lastname){
        return userrepository.findbynameadlastname(name,lastname);
    }

    public List<User> uploadfile(MultipartFile file) throws IOException {
        String uudfile= UUID.randomUUID().toString();
        String resultfilename=uudfile+"."+file.getOriginalFilename();
        file.transferTo(new File(uploadpath+"/"+resultfilename));
        List<User> users= new CsvToBeanBuilder(new FileReader(uploadpath+"/"+resultfilename))
                .withType(User.class).build().parse();
        return userrepository.saveAll(users);
    }

    public String data(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("E kk mm");
        Calendar cal = Calendar.getInstance();
        return  dateFormat.format(cal.getTime());
    }

    public boolean sendmessegforuser(int id,String messeg){
        User user=userrepository.findById(id).orElse(null);
        System.out.println(user);
        System.out.println(user.getMailadress());
        if(user!=null) {
            mailSender.send(user.getMailadress(), "Hello", messeg);
            return true;
        }else return false;
    }
}
