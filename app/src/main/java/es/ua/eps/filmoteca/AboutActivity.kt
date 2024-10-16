package es.ua.eps.filmoteca
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.ua.eps.filmoteca.databinding.ActivityAboutBinding


class AboutActivity : AppCompatActivity() {
    private val mode = AppConfig.mode


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    // Inicializa la UI según el modo seleccionado
    private fun initUI() {
        when (mode) {
            Mode.Layouts -> initLayouts()  // Usar XML Layouts
            Mode.Compose -> initCompose()  // Usar Jetpack Compose
        }
    }

    // Inicializando la interfaz con el layout XML tradicional
    private fun initLayouts() {
       val binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)

        button4.setOnClickListener{
            val url = "https://www.google.com"
            val viewIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            try {
                // Esto intentará abrir el enlace directamente y atrapará la excepción si algo falla
                startActivity(viewIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "No hay un navegador disponible", Toast.LENGTH_SHORT).show()
            }
        }

        button5.setOnClickListener {
            button5.setOnClickListener {
                val email = "contrerasabdiel@gmail.com"  // Reemplaza con tu dirección de correo
                val subject = "Soporte requerido"
                val message = "Escribe tu mensaje aquí"  // Puedes agregar un mensaje predefinido

                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "message/rfc822"  // MIME type para aplicaciones de correo
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email))  // Destinatarios
                    putExtra(Intent.EXTRA_SUBJECT, subject)       // Asunto
                    putExtra(Intent.EXTRA_TEXT, message)          // Mensaje
                }
                try {
                    startActivity(
                        Intent.createChooser(
                            intent,
                            "Selecciona una aplicación de correo"
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al intentar enviar el correo", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        button6.setOnClickListener {
            finish()  // Cierra la actividad actual
        }
    }

    // Inicializando la interfaz utilizando Jetpack Compose
    private fun initCompose() {
        setContent {
            FilmotecaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthorInfoScreen(
                        onWebsiteClick = { openWebsite("https://www.google.com") },
                        onSupportClick = { sendSupportEmail("contrerasabdiel@gmail.com", "Soporte requerido", "Escribe tu mensaje aquí") },
                        onBackClick = { finish() }
                    )
                }
            }
        }
    }

    // Función para abrir una página web
    private fun openWebsite(url: String) {
        val viewIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            startActivity(viewIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "No hay un navegador disponible", Toast.LENGTH_SHORT).show()
        }
    }

    // Función para enviar un correo electrónico
    private fun sendSupportEmail(email: String, subject: String, message: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        try {
            startActivity(Intent.createChooser(intent, "Selecciona una aplicación de correo"))
        } catch (e: Exception) {
            Toast.makeText(this, "Error al intentar enviar el correo", Toast.LENGTH_SHORT).show()
        }
    }

    // Composable para la pantalla de información del autor
    @Composable
    fun AuthorInfoScreen(
        onWebsiteClick: () -> Unit,
        onSupportClick: () -> Unit,
        onBackClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Texto del autor
            Text(
                text = stringResource(id = R.string.text_view_content),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Imagen del autor
            Image(
                painter = painterResource(id = R.drawable.tigre),
                contentDescription = "Imagen del autor",
                modifier = Modifier
                    .size(150.dp)
                    .padding(16.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón: "Ir al sitio web"
            Button(
                onClick = onWebsiteClick,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.button4))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón: "Obtener soporte"
            Button(
                onClick = onSupportClick,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.button5))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón: "Volver"
            Button(
                onClick = onBackClick,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.button6))
            }
        }
    }

    // Previsualización de la UI en Compose
    @Preview(showBackground = true)
    @Composable
    fun ComposeUIPreview() {
        FilmotecaTheme {
            AuthorInfoScreen(
                onWebsiteClick = { /* Acción simulada para vista previa */ },
                onSupportClick = { /* Acción simulada para vista previa */ },
                onBackClick = { /* Acción simulada para vista previa */ }
            )
        }
    }

    // Definición del tema de la app usando MaterialTheme
    @Composable
    fun FilmotecaTheme(content: @Composable () -> Unit) {
        MaterialTheme {
            content()
        }
    }
}