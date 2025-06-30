# mdp_project

El siguiente repositorio contiene todos los archivos utilizados para realizar el proyecto "Impacto de películas basadas en libros en la industria editorial" del curso CC5212-1 Procesamiento masivo de datos, a continuación una pequeña descripción de qué es cada archivo: 

wikipedia_dataset.py: Código de python utilizado para crear un dataset de libros que tienen adaptaciones a películas a partir de las páginas de wikipedia listadas en el siguiente URL https://en.wikipedia.org/wiki/Lists_of_works_of_fiction_made_into_feature_films, este termina creando un csv que contiene lo siguiente: 
    "fiction_work": Título del libro
    "Film_adaptation": Nombre de película basada en el libro junto con su fecha de estreno ("Película (año)")
    "releaseDate": Fecha de publicación del libro
    "author": Autor del libro
