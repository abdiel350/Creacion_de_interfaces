package es.ua.eps.filmoteca
import android.os.Bundle
import android.widget.Button
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import es.ua.eps.filmoteca.databinding.ActivityFilmEditBinding

class FilmEditActivity : AppCompatActivity() {

    private val mode = AppConfig.mode

    private lateinit var binding: ActivityFilmEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initUI()
    }

    private fun initUI() {
        when (mode) {
            Mode.Layouts -> initLayouts()
            Mode.Compose -> initCompose()
        }
    }

    private fun initLayouts() {
        binding = ActivityFilmEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Guardar
        val button9: Button = findViewById(R.id.button9)
        button9.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        // Cancelar
        val button10: Button = findViewById(R.id.button10)
        button10.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun initCompose() {
        setContent {
            FilmotecaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FilmEditScreen(
                        onSaveClick = { onSave() },
                        onCancelClick = { onCancel() }
                    )
                }
            }
        }
    }

    private fun onSave() {
        setResult(RESULT_OK)
        finish()
    }

    private fun onCancel() {
        setResult(RESULT_CANCELED)
        finish()
    }

    @Composable
    fun FilmotecaTheme(content: @Composable () -> Unit) {
        MaterialTheme {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmEditScreen(
    onSaveClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    var title by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var releaseYear by remember { mutableStateOf("") }
    var imdbLink by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    // Listas para los géneros y formatos
    val genres = listOf("Action", "Drama","Comedy", "Horror", "Sci-Fi")
    var selectedGenre by remember { mutableStateOf(genres[0]) }
    var expandedGenre by remember { mutableStateOf(false) }

    val formats = listOf("DVD", "Blu-ray", "Online")
    var selectedFormat by remember { mutableStateOf(formats[0]) }
    var expandedFormat by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.title_edit_movie),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Añadimos un Row para que la imagen y los botones estén alineados horizontalmente
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.tigre),
                contentDescription = "Imagen de la película",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp, 150.dp)
            )
            // Los botones alineados horizontalmente
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { /* Acción para capturar fotografía */ },
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD6D7D7),
                    contentColor = Color.Black
                )
                ) {
                    Text(text = stringResource(id = R.string.btn_take_photo))
                }
                Button(
                    onClick = { /* Acción para seleccionar imagen */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD6D7D7),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = stringResource(id = R.string.btn_select_image))
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = stringResource(id = R.string.hint_title)) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )
        TextField(
            value = director,
            onValueChange = { director = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = stringResource(id = R.string.hint_director))},
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )
        TextField(
            value = releaseYear,
            onValueChange = { releaseYear = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = stringResource(id = R.string.hint_year)) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        TextField(
            value = imdbLink,
            onValueChange = { imdbLink = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = stringResource(id = R.string.hint_imdb_link)) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
        )
        Spacer(modifier = Modifier.height(16.dp))
        ExposedDropdownMenuBox(
            expanded = expandedGenre,
            onExpandedChange = { expandedGenre = !expandedGenre }
        ) {
            TextField(
                value = selectedGenre,
                onValueChange = {},
                readOnly = true,
                label = { Text("Género") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(8.dp),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expandedGenre
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expandedGenre,
                onDismissRequest = { expandedGenre = false }
            ) {
                genres.forEach { genre ->
                    DropdownMenuItem(
                        text = { Text(genre) },
                        onClick = {
                            selectedGenre = genre
                            expandedGenre = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expandedFormat,
            onExpandedChange = { expandedFormat = !expandedFormat }
        ) {
            TextField(
                value = selectedFormat,
                onValueChange = {},
                readOnly = true,
                label = { Text("Formato") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(8.dp),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expandedFormat
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expandedFormat,
                onDismissRequest = { expandedFormat = false }
            ) {
                formats.forEach { format ->
                    DropdownMenuItem(
                        text = { Text(format) },
                        onClick = {
                            selectedFormat = format
                            expandedFormat = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = notes,
            onValueChange = { notes = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = stringResource(id = R.string.hint_notes)) },
            maxLines = 5,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onCancelClick,
                modifier = Modifier.padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD6D7D7),
                    contentColor = Color.Black
                )
            ) {
                Text(text = stringResource(id = R.string.btn_cancel))
            }

            Button(
                onClick = onSaveClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD6D7D7),
                    contentColor = Color.Black
                )
            ) {
                Text(text = stringResource(id = R.string.btn_save))
            }
        }
    }
}