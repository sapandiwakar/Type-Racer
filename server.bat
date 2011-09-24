javac SampleServer.java
javac SampleServerImp.java
javac Notifiable.java
javac SampleClient.java
rmic SampleServerImp
rmic SampleClient
start rmiregistry
java -Djava.security.policy=policy.all SampleServerImp 3