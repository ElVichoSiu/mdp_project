raw1 = LOAD 'hdfs://cm:9000/uhadoop2025/projects/grupo2/Books_rating.csv' USING PigStorage(',') AS (id, title, price, user_id, profilename, reviewhelpfulness, reviewscore, reviewtime, reviewsummary, reviewtext);
raw2 = LOAD 'hdfs://cm:9000/uhadoop2025/projects/grupo2/unique_fiction_works.csv' USING PigStorage(',') AS (fictionwork, filmadaptation, releasedate, author);
raw3 = LOAD 'hdfs://cm:9000/uhadoop2025/projects/grupo2/books_data.csv' USING PigStorage(',') AS (title, description, authors, image, reviewlink, publisher, publisheddate, infolink, categories, ratingscount);

raw1_normalized = FOREACH raw1 GENERATE id, TRIM(LOWER(title)) AS title, price, user_id, profilename, reviewhelpfulness, reviewscore, reviewtime, TRIM(LOWER(reviewsummary)) AS reviewsummary, reviewtext;
raw2_normalized = FOREACH raw2 GENERATE TRIM(LOWER(fictionwork)) AS fictionwork, filmadaptation, releasedate, TRIM(LOWER(author)) AS author;
raw3_normalized = FOREACH raw3 GENERATE TRIM(LOWER(title)) AS title, description, TRIM(LOWER(authors)) AS authors, image, reviewlink, publisher, publisheddate, infolink, categories, ratingscount;

completebook = JOIN raw1_normalized BY title, raw3_normalized BY title;

bookmovie = JOIN completebook BY (raw1_normalized::title, raw3_normalized::authors), raw2_normalized BY (fictionwork, author);

agrupado = GROUP bookmovie ALL;
countbook = FOREACH agrupado GENERATE COUNT(bookmovie) AS count;

STORE countbook INTO 'hdfs://cm:9000/uhadoop2025/projects/grupo2/results3/';
