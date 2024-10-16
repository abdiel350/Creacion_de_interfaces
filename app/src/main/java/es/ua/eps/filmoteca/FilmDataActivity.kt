package es.ua.eps.filmoteca
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import es.ua.eps.filmoteca.databinding.ActivityFilmDataBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FilmDataActivity : AppCompatActivity() {
    private val mode = AppConfig.mode


    companion object {
        const val EXTRA_FILM_TITLE = "No hay peliculas para Mostrar"
        const val REQUEST_EDIT_FILM = 1  // Código de solicitud para la edición
    }

    private lateinit var binding: ActivityFilmDataBinding

    // Usamos un estado para el mensaje que será utilizado en Compose
    private var messageState = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de la UI dependiendo del modo seleccionado
        initUI()
    }

    // Función que decide qué UI inicializar
    private fun initUI() {
        when (mode) {
            Mode.Layouts -> initLayouts()
            Mode.Compose -> initCompose()
        }
    }

    private fun initLayouts() {
        binding = ActivityFilmDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filmTitle = intent.getStringExtra(EXTRA_FILM_TITLE) ?: "Película no especificada"
        binding.labelMovieDataa.text = filmTitle



        binding.button7.setOnClickListener {
            openEditActivity()
        }

        binding.button8.setOnClickListener {
            val intent = Intent(this, FilmListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun initCompose() {
        val filmName = intent.getStringExtra(EXTRA_FILM_TITLE) ?: "Sin nombre"

        setContent {
            // Solo se establece el contenido una vez, gestionando el estado del mensaje en Compose
            FilmotecaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        FilmDataScreen(
                            filmName = filmName,
                            onRelatedFilmClick = { restartActivity() },
                            onEditFilmClick = { openEditActivity() },
                            onBackClick = { goToMain() }
                        )

                        // Mostramos el mensaje en la parte inferior de la pantalla
                        if (messageState.value.isNotEmpty()) {
                            MessageScreen(message = messageState.value)
                        }
                    }
                }
            }
        }
    }

    private fun restartActivity() {
        val intent = Intent(this, FilmDataActivity::class.java)
        intent.putExtra(EXTRA_FILM_TITLE, EXTRA_FILM_TITLE)
        startActivity(intent)
    }

    private fun openEditActivity() {
        val intent = Intent(this, FilmEditActivity::class.java)
        // Usamos startActivityForResult
        startActivityForResult(intent, REQUEST_EDIT_FILM)
    }

    private fun goToMain() {
        val intent = Intent(this, FilmListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    // Sobrescribimos onActivityResult para capturar el resultado
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_EDIT_FILM) {
            if (resultCode == RESULT_OK) {
                showMessage("Película editada exitosamente")
                hideMessageAfterDelay()  // Oculta el mensaje después de 3 segundos

            } else if (resultCode == RESULT_CANCELED) {
                showMessage("Edición cancelada")
                hideMessageAfterDelay()
            }
        }
    }

    // Función para ocultar el mensaje después de 3 segundos
    private fun hideMessageAfterDelay() {
        if (mode == Mode.Layouts) {
            // Usamos un Handler para hacer un post-delay en Layouts
            Handler(Looper.getMainLooper()).postDelayed({
                binding.labelMovieData.text = ""  // Limpiar el texto del TextView
            }, 3000)  // 3 segundos
        } else {
            // En Compose usamos una coroutine para actualizar el estado después de 3 segundos
            lifecycleScope.launch {
                delay(3000)  // Esperamos 3 segundos
                messageState.value = ""  // Limpiamos el estado del mensaje
            }
        }
    }

    // Función showMessage para manejar mensajes en Layouts y Compose
    private fun showMessage(message: String) {
        if (mode == Mode.Layouts) {
            binding.labelMovieData.text = message
            binding.labelMovieData.visibility = View.VISIBLE
        } else {
            // Actualizamos el estado del mensaje en Compose
            messageState.value = message
        }
    }

    @Composable
    fun FilmotecaTheme(content: @Composable () -> Unit) {
        MaterialTheme {
            content()
        }
    }

    // Función composable para mostrar el mensaje en Compose
    @Composable
    fun MessageScreen(message: String) {
        Text(text = message, modifier = Modifier.padding(16.dp))
    }

    @Composable
    fun FilmDataScreen(
        filmName: String,
        onRelatedFilmClick: () -> Unit,
        onEditFilmClick: () -> Unit,
        onBackClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            // Imagen de la película y detalles
            Row(modifier = Modifier.fillMaxWidth()) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.tigre),
                    contentDescription = "Imagen de la película",
                    modifier = Modifier
                        .size(180.dp, 200.dp)
                        .padding(end = 16.dp)
                )
                // Column para mostrar la información de la película
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    // Mensaje (inicialmente oculto, manejo con estado)
                    if (messageState.value.isNotEmpty()) {
                        Text(
                            text = messageState.value,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    // Datos de la película
                    Text(
                        text = "$filmName",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Director
                    Text(text = stringResource(id=R.string.hint_director)+ ":",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                    Text(text = "Nombre del Director", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Año de estreno
                    Text(text = stringResource(id = R.string.hint_year) + ":",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                    Text(text = "2022", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Género
                    Text(text = stringResource(id=R.string.genero)+ ":",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                    Text(text = "Acción", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Formato
                    Text(text = stringResource(id=R.string.formato)+ ":",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                    Text(text = "Blu-ray", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Notas
                    Text(
                        text = stringResource(id = R.string.hint_notes)+ ":",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Aquí puedes escribir comentarios personales sobre la película...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón Ver en IMDb
                    Button(
                        onClick = { /* Acción para ver en IMDb */ },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD6D7D7),
                            contentColor = Color.Black
                    )
                    )   {
                        Text(text = "Ver en IMDB")
                    }

                }
            }

            // Parte inferior de la pantalla: botones centrados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD6D7D7),
                        contentColor = Color.Black
                    )
                )
                 {
                     Text(text = stringResource(id=R.string.volver_principal))
                }

                Button(
                    onClick = onEditFilmClick,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD6D7D7),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = stringResource(id=R.string.editar_peli))
                }
            }
        }
    }
}