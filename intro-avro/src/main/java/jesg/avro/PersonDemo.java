package jesg.avro;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class PersonDemo {

    public static void main(String[] args) throws IOException {
        Person person = Person.newBuilder()
                .setName("Bill")
                .setAddress(Address.newBuilder()
                        .setState("TX")
                        .setCity("Austin")
                        .setStreet("42 Wishbone")
                        .setZipcode("00000")
                        .build())
                .setPhonenumber("000000000")
                .build();

        DatumWriter<Person> writer = new SpecificDatumWriter<Person>(Person.class);
        FileOutputStream out = new FileOutputStream("person-store");
        Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
        writer.write(person, encoder);
        encoder.flush();
        out.close();

        DatumReader<Person> reader = new SpecificDatumReader<Person>(Person.class);
        FileInputStream in = new FileInputStream("person-store");
        Decoder decoder = DecoderFactory.get().binaryDecoder(in, null);
        Person result = reader.read(null, decoder);
        System.out.println("Identity: " + (result == person) + " Equality: " + result.equals(person));

    }

}
