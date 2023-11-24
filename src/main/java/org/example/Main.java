package org.example;

import com.couchbase.client.core.env.PasswordAuthenticator;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.manager.collection.ScopeSpec;

import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        String connectionString = "localhost";//add here your connection string
        String username = "Administrator";
        String password = "password";

        String bucketName = "travel-sample"; //any existing bucket in your cluster should be fine.

        PasswordAuthenticator authenticator = PasswordAuthenticator.builder().username(username)
                .password(password)
                .onlyEnablePlainSaslMechanism() //try to comment this line to check if it stops working
                .build();

        Cluster cluster = Cluster.connect(
                connectionString,
                ClusterOptions.clusterOptions(authenticator));


        Bucket bucket = cluster.bucket(bucketName);
        bucket.waitUntilReady(Duration.ofSeconds(10));

        List<ScopeSpec> scopes = bucket.collections().getAllScopes();

        System.out.println("== Scopes found:");
        for(ScopeSpec spec : scopes) {
            System.out.println(spec.name());
        }
    }
}