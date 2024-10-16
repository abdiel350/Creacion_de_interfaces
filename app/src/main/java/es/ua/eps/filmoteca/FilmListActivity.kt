package es.ua.eps.filmoteca
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.ua.eps.filmoteca.databinding.ActivityFilmListBinding

class FilmListActivity : AppCompatActivity() {
    private val mode = AppConfig.mode

    private lateinit var binding: ActivityFilmListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Llamar a la inicialización de la UI basada en el modo seleccionado
        initUI()
    }

    // Función que decide qué UI inicializar
    private fun initUI() {
        when (mode) {
            Mode.Layouts -> initLayouts()  // Usar XML Layouts
            Mode.Compose -> initCompose()  // Usar Jetpack Compose
        }
    }

    // Inicializar UI usando XML y ViewBinding
    private fun initLayouts() {
        binding = ActivityFilmListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración de los botones con ViewBinding
        binding.button3.setOnClickListener {
            val intent = Intent(this, FilmDataActivity::class.java)
            intent.putExtra(FilmDataActivity.EXTRA_FILM_TITLE, "Regreso al futuro")
            startActivity(intent)
        }

        binding.button4.setOnClickListener {
            val intent = Intent(this, FilmDataActivity::class.java)
            intent.putExtra(FilmDataActivity.EXTRA_FILM_TITLE, "Mision rescate")
            startActivity(intent)
        }

        binding.button5.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    // Inicializar UI usando Jetpack Compose
    private fun initCompose() {
        setContent {
            FilmotecaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FilmListScreen(
                        onFilmAClick = { openFilmData("Regreso al futuro") },
                        onFilmBClick = { openFilmData("Mision rescate") },
                        onAboutClick = { openAboutActivity() }
                    )
                }
            }
        }
    }

    // Funciones para navegar a otras actividades
    private fun openFilmData(filmName: String) {
        val intent = Intent(this, FilmDataActivity::class.java)
        intent.putExtra(FilmDataActivity.EXTRA_FILM_TITLE, filmName)

        startActivity(intent)
    }

    private fun openAboutActivity() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    @Composable
    fun FilmotecaTheme(content: @Composable () -> Unit) {
        MaterialTheme {
            content()
        }
    }
}

@Composable
fun FilmListScreen(onFilmAClick: () -> Unit, onFilmBClick: () -> Unit, onAboutClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onFilmAClick, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text("Ver película A")
        }
        Button(onClick = onFilmBClick, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text("Ver película B")
        }
        Button(onClick = onAboutClick, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text("Acerca de")
        }
    }
}