# mdp_project

El siguiente repositorio contiene todos los archivos utilizados para realizar el proyecto "Impacto de películas basadas en libros en la industria editorial" del curso CC5212-1 Procesamiento masivo de datos, a continuación una pequeña descripción de qué es cada archivo: 

wikipedia_dataset.py: Código de python utilizado para crear un dataset de libros que tienen adaptaciones a películas a partir de las páginas de wikipedia listadas en el siguiente URL https://en.wikipedia.org/wiki/Lists_of_works_of_fiction_made_into_feature_films, este termina creando un csv que contiene lo siguiente: 
    "fiction_work": Título del libro
    "Film_adaptation": Nombre de película basada en el libro junto con su fecha de estreno ("Película (año)")
    "releaseDate": Fecha de publicación del libro
    "author": Autor del libro

sortCSV.py: Script utilizado para ordenar un archivo CSV, particularmente se trata de "Books_rating" ordenado por "review/time". Esto es útil y necesario para facilitar el análisis posterior (burst) que posee sentido si los datos están ordenados cronológicamante.

merged.py: Código de python encargado de fusionar dos archivos CSV, específicamente "books_data" y el que se produce con wikipedia_dataset.py en un único archivo. Este script permite unir información dispersa (por ejemplo, libro + reseñas + adaptación) en un solo archivo estructurado. Este script permitió conocer que existen 1421 coincidencias. 
Además, en este código se incluyo generar "nombres.txt" un archivo de texto que contine la columna "fiction_work" y "film_adaptation" (de esta se obtiene solo el año). 

