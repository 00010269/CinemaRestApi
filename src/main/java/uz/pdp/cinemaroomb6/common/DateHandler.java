//package uz.pdp.cinemaroomb6.common;
//
//import com.fasterxml.jackson.core.JacksonException;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//
//public class DateHandler extends StdDeserializer<Date>{
//
//    protected DateHandler(StdDeserializer<?> src) {
//        super(src);
//    }
//
//    public DateHandler(JavaType valueType) {
//        super(valueType);
//    }
//
//    public DateHandler(Class<?> vc) {
//        super(vc);
//    }
//
//    @Override
//    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
//        String date = jsonParser.getText();
//        try {
//            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
//            return simpleDateFormat.parse(date);
//        }catch (Exception e){
//            return null;
//        }
//    }
//}
