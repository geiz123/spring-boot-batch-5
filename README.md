# spring-boot-batch-5
 Spring boot batch 5 with H2 for metadata and another H2 for jobs

# How to run
- Start H2 in server mode `java -cp .\h2-2.2.224.jar org.h2.tools.Server -ifNotExists`
- Create a database and run `src/main/resources/schema-all.sql` to create the tables for the job to write to

# Reference
https://roytuts.com/using-multiple-datasources-in-spring-batch/
