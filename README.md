# Creacion_de_interfaces

-Se procede a la creacion de un nuevo proyecto usando Android Studio Koala Feature Drop version 2024.1.2
-Luego New Project , seguido procederemos a utilizar la plantilla  Empty Activity.
-En nuestra siguiente ventana, en donde dice Name se procede a colocar nuestro nombre de proyecto el cual es Filmoteca, 
seguido de nuestro Package name el cual llevara como nombre es.ua.eps.filmoteca, lenguaje Kotlin, SDK API 24 ("Nougat"; Android 7.0), seguido de Finish para la creación.
-El siguiente paso seria la creación de nuestro dispositivo virtual:
Create Virtual Device, seleccionamos la opcion Phone seguido de Pixel 2, API 34 y la version de Android (14).Se debe utilizar Gradle 8.7 que es la version que da Koala por defecto.

Nota importante para definir la vista que se utilizara se debe ir al archivo MODE.KT seleccionar si quieres usar Layout o Compose.
-Para este nuevo proyecto ó continuación de Filmoteca, cree un archivo llamado Mode.kt para poder establecer el cambio de variable ya sea para usar el modo Compose o el modo Layout.
Este archivo, lo cree en la primera practica mas no lo use para hacer el cambio de vista Layout ó Compose, en este archivo tenemos un objeto definido en el cual tendra una variable llamada mode la cual almacenara el valor del modo actual de la aplicacion sea Layout ó Compose, aqui definimos que vista queremos ver.
 (esto seria una instancia para que se pueda acceder  desde cualquiera actividad y ver dicha variable configurada que le pasara el desarrollador por defecto, Layout ó  Compose)

-Cada actividad tiene su propia configuracion para poder acceder al valor mode.
-Tambien se crearon los itents implicitos para cada situacion, abrir una pagina web, enviar un correo y cerrar con el metodo Finish()
-Para los intents explícitos se configuro cada boton para su uso, los cuales fueron ver el nombre de la pelicula A, ver el nombre de la pelicula B y Acerca.
ver peliculas relacionada con la pelicula se pasa por parametro, la ediccion de la pelicula la cual se controla con una variable REQUEST_EDIT_FILM, los botones
de guardar y cancelar el cual una vez editada la pelicula mostrara edicion exitosa o cancelar se cancelo la edicion. se enlazaron todas las actividades segun los requerimientos.

- Se edito la interfaz de la actividad  FilmDataActivity, cabe destacar que fue facil desarrollarla en Layout pero al realizarla con compose tuve mucha dificulta, debido a que daba muchos errores el compose a la hora de implementarlo, sus botones y columnas entre otras cosas.
- Para la interfaz edicion pelicula, se presentaron los inconvenientes de hacer desplegar el género con las opciones: Acción, Drama, Comedia, Terror y Sci-Fi., al igual que las opciones DVD, Blu-ray u Online, todo se descuadraba a la hora de implementarlo o sucedia que no mostraba la informacion, mientras mas arreglaba un error surgia otro problema, una vez resuelto todo, procedi a utilizar los recuros para traducir a los idiomas español e ingles en mis archivos strings, uno para español y otro para ingles todo funciono de manera perfecta con los idiomas.
  -Para la implementación de  Independencia del hardware, usando disposición horizontal (layout-land),tuve el problema de que me equivoque de nombre en un solo boton y por no ver ese error nunca me mostraba la pantalla y se cerraba mi aplicación, una vez solucionado esto funciono todo de manera correcta.
    
    -EN EL CODIGO SE MUESTRA más entendido todo lo que se comenta en esta parte, hay pequeños comentarios.
  
    -En general los problemas mas fuertes se me presentaron en la integración de Compose.
    
    
    
  

 
