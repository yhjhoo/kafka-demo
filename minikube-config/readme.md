### 
prerequisites: minikube and helm

kubectl create namespace kafka-ns


Reference configuration
```properties
externalAccess.enabled=true
externalAccess.service.type=ClusterIP
externalAccess.service.ports.external=9094
externalAccess.service.domain='ingress-ip'
```

```shell
minikube ip
```
my machine output:
192.168.49.2

install kafka with 3 nodes
```shell
helm upgrade my-release \
--set replicaCount=5 \
--set transactionStateLogMinIsr=1 \
--set externalAccess.enabled=true \
--set externalAccess.service.type=ClusterIP \
--set externalAccess.service.ports.external=9094 \
--set externalAccess.service.domain='192.168.49.2' \
--set deleteTopicEnable=true \
bitnami/kafka
```


helm install my-release `
--set replicaCount=3 `
--set transactionStateLogMinIsr=1 `
--set externalAccess.enabled=true `
--set externalAccess.service.type=ClusterIP `
--set externalAccess.service.ports.external=9094 `
--set externalAccess.service.domain='192.168.49.2' `
--set deleteTopicEnable=true `
bitnami/kafka


#### open network port to ip address 192.168.49.2
```
kubectl patch deployment ingress-nginx-controller --patch "$(cat ingress-nginx-controller-patch.yaml)" -n ingress-nginx
kubectl patch configmap tcp-services -n ingress-nginx --patch '{"data":{"9094":"kafka/my-release-kafka:9092"}}'

kubectl patch configmap tcp-services -n ingress-nginx --patch '{"data":{"9099":"kafka/my-release-kafka-0-external:9094"}}'

kubectl patch configmap tcp-services -n ingress-nginx --patch '{"data":{"9094":"kafka/my-release-kafka-0-external:9094"}}'
kubectl patch configmap tcp-services -n ingress-nginx --patch '{"data":{"9095":"kafka/my-release-kafka-1-external:9094"}}'
kubectl patch configmap tcp-services -n ingress-nginx --patch '{"data":{"9096":"kafka/my-release-kafka-2-external:9094"}}'
kubectl patch configmap tcp-services -n ingress-nginx --patch '{"data":{"9097":"kafka/my-release-kafka-3-external:9094"}}'
kubectl patch configmap tcp-services -n ingress-nginx --patch '{"data":{"9098":"kafka/my-release-kafka-4-external:9094"}}'
```

kafka-console-producer.sh \
--topic test2 \
--request-required-acks all \
--bootstrap-server my-release-kafka-0-external:9094,my-release-kafka-1-external:9094,my-release-kafka-2-external:9094,my-release-kafka-3-external:9094,my-release-kafka-4-external:9094,


kafka-console-consumer.sh \
--topic test2 \
--from-beginning \
--bootstrap-server my-release-kafka-0-external:9094,my-release-kafka-1-external:9094,my-release-kafka-2-external:9094,my-release-kafka-3-external:9094,my-release-kafka-4-external:9094,


kafka-topics.sh --describe \
--topic test2 \
--bootstrap-server my-release-kafka-0-external:9094,my-release-kafka-1-external:9094,my-release-kafka-2-external:9094,my-release-kafka-3-external:9094,my-release-kafka-4-external:9094,


kafka-topics.sh --describe \
--topic quickstart-events2 \
--bootstrap-server my-release-kafka-0-external:9094,my-release-kafka-1-external:9094,my-release-kafka-2-external:9094,my-release-kafka-3-external:9094,my-release-kafka-4-external:9094,

kubectl drain pod/my-release-kafka-2 \
--delete-emptydir-data \
--force \
--ignore-daemonsets


spring boot properties configuration
```properties
spring.kafka.bootstrap-servers=192.168.49.2:9094,192.168.49.2:9095,192.168.49.2:9096
```


### uninstall
```shell
helm uninstall my-release
```


### 
helm install my-release -f values.yaml bitnami/kafka
helm upgrade my-release -f values.yaml bitnami/kafka


#### reference
https://github.com/bitnami/charts/blob/master/bitnami/kafka/README.md
