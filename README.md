### kafka-demo


run spring boot application to start kafka consumer

### Consumer
```shell
mvn spring-boot:run
```

### Producer
Start test to start producer
```shell
mvn test
```


#### minikube kafka cluster setup guide
[minikube kafka cluster setup](minikube-config/readme.md)



k delete statefulset.apps/my-release-kafka --cascade=orphan

kafka-consumer-groups.sh --bootstrap-server my-release-kafka-0-external:9094,my-release-kafka-1-external:9094,my-release-kafka-2-external:9094,my-release-kafka-3-external:9094,my-release-kafka-4-external:9094 --list


kafka-consumer-groups.sh --bootstrap-server my-release-kafka-0-external:9094,my-release-kafka-1-external:9094,my-release-kafka-2-external:9094,my-release-kafka-3-external:9094,my-release-kafka-4-external:9094 --group default --describe --state

kafka-consumer-groups.sh --bootstrap-server my-release-kafka-0-external:9094,my-release-kafka-1-external:9094,my-release-kafka-2-external:9094,my-release-kafka-3-external:9094,my-release-kafka-4-external:9094 --describe --group default
