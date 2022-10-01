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
helm install my-release \
--set replicaCount=3 \
--set externalAccess.enabled=true \
--set externalAccess.service.type=ClusterIP \
--set externalAccess.service.ports.external=9094 \
--set externalAccess.service.domain='192.168.49.2' \
bitnami/kafka
```


#### open network port to ip address 192.168.49.2
```
kubectl patch deployment ingress-nginx-controller --patch "$(cat ingress-nginx-controller-patch.yaml)" -n ingress-nginx
kubectl patch configmap tcp-services -n ingress-nginx --patch '{"data":{"9094":"kafka-ns/my-release-kafka-0-external:9094"}}'
kubectl patch configmap tcp-services -n ingress-nginx --patch '{"data":{"9095":"kafka-ns/my-release-kafka-1-external:9094"}}'
kubectl patch configmap tcp-services -n ingress-nginx --patch '{"data":{"9096":"kafka-ns/my-release-kafka-2-external:9094"}}'
```

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
