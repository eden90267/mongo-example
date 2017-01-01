package personal.eden.mongoexample;

import com.mongodb.*;

import java.util.Arrays;

/**
 * Created by eden90267 on 2017/1/1.
 */
public class main {

    public static void main(String[] args) {
        MongoExample mongoExample = new MongoExample();
        mongoExample.openExample();
        mongoExample.closeExample();
    }

    private static class MongoExample {

        private MongoCredential credentialOne;
        private MongoClient mongoClient;
        private DB db;
        private DBCollection coll;

        public void openExample() {
            credentialOne = MongoCredential.createCredential("test", "test", "password".toCharArray());

            mongoClient = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credentialOne));

            db = mongoClient.getDB("test");
            coll = db.getCollection("bios");

            System.out.println(coll.getFullName());
            System.out.println(coll.count());

            coll = db.getCollection("test");
        }

        public void closeExample() {
            mongoClient.close();
        }

    }





}
