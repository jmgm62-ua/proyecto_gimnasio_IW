Comando para ejecutar seeders

docker cp /ruta/a/tu/seeder.sql postgres-develop-iw:/seeder.sql

docker exec -it postgres-develop-iw bash

psql -U iw -d iw -f /seeder.sql

exit

