# ethereum-local

- 1
  docker pull ethereum/client-go
- 2
  docker run --name ethereum-node -d -p 8545:8545 ethereum/client-go --dev --http --http.addr 0.0.0.0 --http.port 8545 --http.api eth,net,web3,personal
- 3
  docker logs ethereum-node

Aquí, las opciones adicionales hacen lo siguiente:

--http: Habilita el servidor HTTP RPC.
--http.addr 0.0.0.0: Establece la dirección del servidor RPC para aceptar conexiones desde cualquier origen.
--http.port 8545: Establece el puerto en el que se escuchan las conexiones RPC.
--http.api eth,net,web3,personal: Define las API que están disponibles a través de RPC.

- verificar el estado de docker
  docker ps -a
