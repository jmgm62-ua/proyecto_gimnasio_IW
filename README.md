# proyecto_gimnasio_IW

## Herramientas

### Frontend
Vue

### Backend
Spring

BD -> Postgresql

### ¿Cómo correr el backend?


LA PRIMERA VEZ: Ejecutar desde la carpeta `backend/iweb-gym-grupo5` el comando:
`docker compose up --build`

A partir de ahí con ejecutar `docker compose up` sobra. Es la misma idea que teníamos en TAES, tenemos un Docker Compose que genera dos contenedores, uno para el Postgres y otro para la Aplicación SpringBoot. Una vez corrido el comando ya se le pueden hacer peticiones a `localhots:8080`. El funcionamiento es igual que en MADS, es SpringBoot.
