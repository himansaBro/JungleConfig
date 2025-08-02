import com.fasterxml.jackson.core.JsonProcessingException;
import core.DataModel.TripleMap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MainTest {
    static long counter = System.currentTimeMillis();
    public static void main(String[] args) throws JsonProcessingException {
        timeStamp(1);
//        ConfigHandler handler = new ConfigHandler(new File("D:\\ThisFileNotExist.txt"),true);
        JConfig.Set("user.isWork",false);
        timeStamp(2);
        JConfig.Set("user.age",21);
        timeStamp(3);
        JConfig.Set("user.name","Jhon's Brother");
        timeStamp(4);
        System.out.println("All Saved");
        timeStamp(5);
        Boolean work = JConfig.Get("user.isWork", Boolean.class);
        timeStamp(6);
        Integer age = JConfig.Get("user.age",Integer.class);
        timeStamp(7);
        String name = JConfig.Get("user.name",String.class);
        timeStamp(8);
        System.out.println("Transactions");
        timeStamp(9);
        JConfig.BeginTransaction();
        timeStamp(10);
        JConfig.Set("version.id","0.0.1");
        timeStamp(11);
        JConfig.Set("version.name","BetaBuild");
        timeStamp(12);
        JConfig.Set("version.creator","Himansa");
        timeStamp(13);
        JConfig.Commit();
        timeStamp(14);
        TripleMap<String,Class<?>,Object> data=JConfig.KeysStartsWith("version.");
        timeStamp(15);
        data.getEntryList().forEach(stringClassObjectEntry -> System.out.println(stringClassObjectEntry.getKey()+":"+stringClassObjectEntry.getValue2()));
        timeStamp(16);
        System.out.println("starting PJOS save.");
        timeStamp(17);
        record Person(int id, String name, LocalDate birthday, BigDecimal salary){}
        timeStamp(18);
        Person sam = new Person(12,"Sam",LocalDate.now(),BigDecimal.TEN);
        timeStamp(19);
        JConfig.SerializedSet("person.sam",sam);
        timeStamp(20);
        Optional<Person> gotSam = JConfig.SerializedGet("person.sam",Person.class);
        timeStamp(21);
        gotSam.ifPresent(person -> System.out.println(person.name));
        timeStamp(22);
    }
    private static void timeStamp(int count){
        System.out.println("[TimeStamp:"+count+"] "+(System.currentTimeMillis()-counter));
        counter = System.currentTimeMillis();
    }
}
