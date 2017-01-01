package personal.eden.mongoexample;

import com.mongodb.*;
import com.mongodb.util.JSON;

import java.util.Arrays;
import java.util.List;

/**
 * Created by eden90267 on 2017/1/1.
 */
public class main {

    public static void main(String[] args) {
        MongoExample mongoExample = new MongoExample();

        mongoExample.openExample();

//        mongoExample.insertExample();

        mongoExample.findExample();

//        mongoExample.updateExample();

        mongoExample.removeExample();

        mongoExample.closeExample();

    }

    private static class MongoExample {

        private MongoCredential credentialOne;
        private MongoClient mongoClient;
        private DB db;
        private DBCollection coll;
        private WriteResult result;

        public void openExample() {
            credentialOne = MongoCredential.createCredential("test", "test", "password".toCharArray());

            mongoClient = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credentialOne));

            db = mongoClient.getDB("test");
            coll = db.getCollection("bios");

            System.out.println(coll.getFullName());
            System.out.println(coll.count());

            coll = db.getCollection("test");
        }

        public void insertExample() {
            // String to JSON
            String json_string = "{\"name\":\"MongoDB\",\"type\":\"database\",\"count\":1,\"info\":{\"x\":203,\"y\":102}}";
            BasicDBObject doc = (BasicDBObject) JSON.parse(json_string);

            coll.insert(doc);

            // JSON object
            BasicDBObject doc2 = new BasicDBObject();
            doc2.put("name","MongoDB");
            doc2.put("type","database");
            doc2.put("count",2);

            BasicDBObject info = new BasicDBObject();
            info.put("x",203);
            info.put("y",102);
            doc2.put("info",info);

            coll.insert(doc2);

            // Multiple documents
            String json_string3 = "{\"name\":\"MongoDB\",\"type\":\"database\",\"count\":3,\"info\":{\"x\":203,\"y\":102}}";
            DBObject doc3 = (DBObject) JSON.parse(json_string3);

            String json_string4 = "{\"name\":\"MongoDB\",\"type\":\"database\",\"count\":4,\"info\":{\"x\":203,\"y\":102}}";
            DBObject doc4 = (DBObject) JSON.parse(json_string4);

            List<DBObject> docs = Arrays.asList(doc3, doc4);

            coll.insert(docs);

        }

        public void findExample() {
            BasicDBObject query = new BasicDBObject("type", "database").append("count", new BasicDBObject("$gt", 1));
            DBCursor cursor = coll.find(query);
            try {
                while (cursor.hasNext()) {
                    System.out.println(cursor.next());
                }
            }finally {
                cursor.close();
            }
        }

        public void updateExample() {

            BasicDBObject query = new BasicDBObject();
            query.put("count", 1);

            BasicDBObject query2 = new BasicDBObject();
            query2.put("count", new BasicDBObject("$gt", 1));

            BasicDBObject update = new BasicDBObject();
            update.put("$set", new BasicDBObject("info.x", 204));

            coll.update(query, update);
            coll.update(query2, update);
            coll.updateMulti(query2, update);

            DBObject obj = coll.findOne(query);
            obj.put("type", "save_test");
            coll.save(obj);

        }

        public void removeExample() {
            result = coll.remove(new BasicDBObject().append("count", 3));
            System.out.println(result.getN());

            result = coll.remove(new BasicDBObject());
            System.out.println(result.getN());

            coll.drop();
        }

        public void closeExample() {
            mongoClient.close();
        }

    }


}
