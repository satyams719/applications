import json
from kafka  import KafkaProducer

folderName =  "~/kafkaCerts/kafka-pizza/"
producer = KafkaProducer(
    bootstrap_server = "",
    security_protocol ="SSL",
    ssl_cafile="ca.pem",
    ssl_certfile ="service.cert",
    ssl_keyfile ="service.key",
    value_serializer=lambda v: json.dumps(v).encode('ascii'),
    key_serializer = lambda v: json.dumps(v).encode('ascii')
)