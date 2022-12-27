package kata5p1.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import kata5p1.model.Mail;

public class MailListReader {
    
    private final String fileName;
    
    public MailListReader(String fileName) {
        this.fileName = fileName;
    }
    
    public Iterable<Mail> mails() {
        return new Iterable<Mail>() {
            @Override
            public Iterator<Mail> iterator() {
                try {
                    return createIterator();
                } catch (FileNotFoundException ex) {
                    return null;
                }
            }
        };
    }
    
    private Iterator<Mail> createIterator() throws FileNotFoundException {
        return new Iterator<Mail>() {
            
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            Mail next = new Mail("");
            
            @Override
            public boolean hasNext() {
                return this.next != null;
            }

            @Override
            public Mail next() {
                try {
                    String line = this.br.readLine();
                    
                    while (line != null &&
                            !line.matches
                           ("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                        line = this.br.readLine();
                    }
                    
                    this.next = (line != null)?new Mail(line):null;
                } catch (IOException ex) {
                    Logger.getLogger(MailListReader.class.getName())
                                    .log(Level.SEVERE, null, ex);
                }
                return this.next;
            }
            
            
        };
    }
}