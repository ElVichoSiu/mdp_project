# mdp_project

El siguiente repositorio contiene todos los archivos utilizados para realizar el proyecto "Impacto de películas basadas en libros en la industria editorial" del curso CC5212-1 Procesamiento masivo de datos, a continuación una pequeña descripción de qué es cada archivo: 

wikipedia_dataset.py: Código de python utilizado para crear un dataset "ffinal_fiction_to_film.csv" de libros que tienen adaptaciones a películas a partir de las páginas de wikipedia listadas en el siguiente [URL](https://en.wikipedia.org/wiki/Lists_of_works_of_fiction_made_into_feature_films), este termina creando un csv que contiene lo siguiente: 
    "fiction_work": Título del libro
    "Film_adaptation": Nombre de película basada en el libro junto con su fecha de estreno 
    "releaseDate": Fecha de publicación del libro
    "author": Autor del libro
    "us_release_date": Fecha de estreno de la película en USA, en formato yyyy-mm-dd
    podremos ocupar los fiction_work jutno con el us_release_date de alguna de sus películas como input para detección de burst

sortCSV.py: Script utilizado para ordenar un archivo CSV, particularmente se trata de "Books_rating" ordenado por "review/time". Esto es útil y necesario para facilitar el análisis posterior (burst) que posee sentido si los datos están ordenados cronológicamante.

merged.py: Código de python encargado de fusionar dos archivos CSV, específicamente "books_data" y el que se produce con wikipedia_dataset.py como primer dataset es decir "combined_fiction_to_films.csv" en un único archivo. Este script permite unir información dispersa (por ejemplo, libro + reseñas + adaptación) en un solo archivo estructurado. Este script permitió conocer que existen 1421 coincidencias. 
Además, en este código se incluyo generar "nombres.txt" un archivo de texto que contine la columna "fiction_work" y "film_adaptation" (de esta se obtiene solo el año). 

En mdp-kafka-project se encuentra el corazon del proyecto, que es el proyecto de Kafka que permite procesar los datos de manera masiva. 

Para ejecutar el proyecto de Kafka, se debe seguir los siguientes pasos:
1. Clonar el repositorio.
2. Importar el proyecto en Eclipse o IntelliJ.
3. Ejecutar el build del proyecto.
4. Subir el archivo .jar generado por el build al equipo en el que se ejecutará el proyecto de Kafka.
5. En el equipo se deberan abrir 3 terminales y ejecutar los siguientes comandos, en el orden indicado:
   - Terminal 1: `java -jar mdp-kafka.jar BurstReview review-burst (fecha de estreno de la película, en formato yyyy-mm-dd)`
   - Terminal 2: `java -jar mdp-kafka.jar MovieFilter reviews-example review-burst (nombre del libro)`
   - Terminal 3: `java -jar mdp-kafka.jar AmazonSimulator /data/2025/uhadoop/projects/grupo2/archivo.csv reviews-example 100000` (En este caso se utiliza el archivo "archivo.csv" que es el resultado de sortCSV.py, pero se puede utilizar cualquier archivo CSV con la misma estructura y la ubicacion es la de la maquina del curso).
6. Una vez que se hayan ejecutado los comandos, se debe esperar a que el proceso termine. Esto puede tardar un tiempo dependiendo del tamaño del archivo CSV y la cantidad de datos que se estén procesando.

## Integrantes del equipo:
- [Javiera Romero Orrego](https://github.com/javiromeroo)
- [Laura Maldonado Lagos](https://github.com/lauraflm)
- [Vicente Thiele Muñoz](https://github.com/ElVichoSiu)
