# Contexte

Il s'agit d'une application Spring (Java 17) qui envoie un message sur un cluster Kafka.
Il envoie un message de type Facture(id, sens, montant, date) dans un topic test-facture.

L'application est réalisé de sorte qu'au démarrage 1 message soit envoyé.


Le cluster Kafka est composé de 3 brokers data et 1 controller.
On y trouvera 3 topics test-facture, test-audit et test-dlt. Chaque topic est composé de 3 partitions et redondé qu'une seule fois.

Pour visualiser le contenu du topic, on utilisera akhq, outil de visualisation du cluster Kafka 

## Objectif

L'application actuelle est bloquante car non reactive, de plus on n'a pas la possibilité d'envoyer des message dès que l'on souhaite.

L'objectif sera donc de transformer cette application en la rendant reactive, en utilisant :
 
* une route http permettant de prendre uen facture en entrée
* le controller devra interagir avec le service de publication de message Kafka.
* Faire les tests des unitaires

### Note
Le service de publication de message est un service non reactive.
Pour transformer le service, il suffit de transformer **KafkaTemplate** par **ReactiveKafkaProducerTemplate**

1. Modifier la classe de configuration pour utiliser la classe de spring **ReactiveKafkaProducerTemplate**

  * Cette classe permet de construire [un sender Kafka reactive](https://docs.spring.io/spring-kafka/api/org/springframework/kafka/core/reactive/ReactiveKafkaProducerTemplate.html) 
  * Afin de faciliter cette construction, on utilisera le [SenderOptions](https://projectreactor.io/docs/kafka/release/api/reactor/kafka/sender/SenderOptions.html) qui est un helper de construction

2. Construire un record Kafka en utilisant la méthode **create** de la classe[SenderRecords](https://projectreactor.io/docs/kafka/release/api/reactor/kafka/sender/SenderRecord.html)
3. Utiliser la méthode send() **ReactiveKafkaProducerTemplate**

