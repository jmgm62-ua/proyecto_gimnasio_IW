Comando para ejecutar seeders

docker cp /ruta/a/tu/seed.sql postgres-develop-iw:/seed.sql

docker exec -it postgres-develop-iw bash

psql -U iw -d iw

psql -U iw -d iw -f /seeder.sql

exit

