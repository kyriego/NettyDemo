package nettyProtoStickAndUnpack;

import POJO.Person.PersonOuter;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        for(int i = 0 ; i < 1000000; i++){
            String str = UUID.randomUUID().toString();
            int id = i  + 1;
            int str_byte_count = str.getBytes(StandardCharsets.UTF_8).length;
            int id_byte_count = 4;

            PersonOuter.Person.Builder builder = PersonOuter.Person.newBuilder();
            builder.setName(str);
            builder.setId(id);
//            int fill_byte_count = 126 str_byte_count - id_byte_count;
//            if(fill_byte_count < 0){
//                continue;
//            }
//            byte[] fill = new byte[fill_byte_count];
//            builder.setFill(ByteString.copyFrom(fill));
            PersonOuter.Person person = builder.build();
            System.out.printf("i=%d\tstr=%d id=%d   total=%d\n", i,str_byte_count, 4, person.toByteArray().length);
        }
    }
}
