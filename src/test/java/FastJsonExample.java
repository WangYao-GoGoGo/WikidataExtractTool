import java.io.StringReader;

import com.alibaba.fastjson.JSONReader;

public class FastJsonExample {
    /**
     * FastJson逐行解析json
     * @author drlyee
     * @date 2015-02-10
     */
    public void ReadWithFastJson()
    {
        String jsonString = "{\"array\":[1,2,3],\"arraylist\":[{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"},{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"},{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"}],\"object\":{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"},\"string\":\"HelloWorld\"}";
 
        JSONReader reader = new JSONReader(new StringReader(jsonString));
        reader.startObject();
        System.out.print("start fastjson");
        while (reader.hasNext())
        {
            String key = reader.readString();
            System.out.print("key " + key);
            if (key.equals("array"))
            {
                reader.startArray();
                System.out.print("start " + key);
                while (reader.hasNext())
                {
                    String item = reader.readString();
                    System.out.print(item);
                }
                reader.endArray();
                System.out.print("end " + key);
            }
            else if (key.equals("arraylist"))
            {
                reader.startArray();
                System.out.print("start " + key);
                while (reader.hasNext())
                {
                    reader.startObject();
                    System.out.print("start arraylist item");
                    while (reader.hasNext())
                    {
                        String arrayListItemKey = reader.readString();
                        String arrayListItemValue = reader.readObject().toString();
                        System.out.print("key " + arrayListItemKey);
                        System.out.print("value " + arrayListItemValue);
                    }
                    reader.endObject();
                    System.out.print("end arraylist item");
                }
                reader.endArray();
                System.out.print("end " + key);
            }
            else if (key.equals("object"))
            {
                reader.startObject();
                System.out.print("start object item");
                while (reader.hasNext())
                {
                    String objectKey = reader.readString();
                    String objectValue = reader.readObject().toString();
                    System.out.print("key " + objectKey);
                    System.out.print("value " + objectValue);
                }
                reader.endObject();
                System.out.print("end object item");
            }
            else if (key.equals("string"))
            {
                System.out.print("start string");
                String value = reader.readObject().toString();
                System.out.print("value " + value);
                System.out.print("end string");
            }
        }
        reader.endObject();
        System.out.print("start fastjson");
    }
}
