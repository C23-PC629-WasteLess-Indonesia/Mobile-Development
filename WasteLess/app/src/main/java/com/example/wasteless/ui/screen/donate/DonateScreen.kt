package com.example.wasteless.ui.screen.donate

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.example.wasteless.BuildConfig
import com.example.wasteless.R
import com.example.wasteless.preferences.UserPreferences
import com.example.wasteless.ui.components.ButtonCustom
import com.example.wasteless.ui.theme.SoftGray
import com.example.wasteless.ui.theme.TextGray
import com.example.wasteless.utils.createImageFile
import java.io.File
import java.util.Objects

@Composable
fun DonateScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    userPreferences: UserPreferences,
    navigateToBack: () -> Unit,
){
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .clickable {
                            onBackClick()
                        },
                    tint = Color.White
                )
                Text(
                    text = stringResource(id = R.string.donasimakanan),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White
                )
            }
        }
    ) { innerPading ->
        DonateContent(modifier = modifier.padding(innerPading), userPreferences = userPreferences, navigateToBack = navigateToBack)
    }
}

@Composable
private fun DonateContent(
    modifier: Modifier = Modifier,
    viewModel: DonateViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    userPreferences: UserPreferences,
    navigateToBack: () -> Unit,
){
    val token = userPreferences.getUser().token.toString()
    val response by remember {
        viewModel.responsePost
    }.collectAsState()
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )

    var captureImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    var namaMakanan by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var deskripsiMakanan by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var jumlahMakanan by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var lokasiMakanan by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var waktuExpiredMakanan by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()){
        captureImageUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
        if (it){
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        }else{
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = modifier.padding(top = 8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            if (captureImageUri.path?.isNotEmpty() == true){
                Image(
                    painter = rememberAsyncImagePainter(model = captureImageUri),
                    contentDescription = "image food",
                    modifier = modifier.size(100.dp)
                )
            }else{
                Box(
                    modifier = modifier
                        .size(100.dp)
                        .background(Color(SoftGray.hashCode()))
                        .clickable {
                            val permissionCheckResult = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            )

                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                cameraLauncher.launch(uri)
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        },
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_camera_alt_24),
                        contentDescription = "camera"
                    )
                }
            }
            Column(
                modifier = modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.tambahkangambarmakanan),
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = stringResource(id = R.string.alert),
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = modifier.padding(top = 4.dp)
                )
            }
        }
        Spacer(modifier = modifier.padding(16.dp))
        Text(
            text = "Nama makanan",
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        TextField(
            value = namaMakanan, 
            onValueChange = {
                namaMakanan = it
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 1.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black
            ),
            placeholder = {
                Text(text = "nama makanan yang ingin kamu bagikan")
            }
        )
        Spacer(modifier = modifier.padding(16.dp))
        Text(
            text = "Deskripsi",
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        TextField(
            value = deskripsiMakanan,
            onValueChange = {
                deskripsiMakanan = it
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 1.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black
            ),
            placeholder = {
                Text(text = "Deskripsi makanan yang ingin kamu bagikan")
            }
        )
        Spacer(modifier = modifier.padding(16.dp))
        Text(
            text = "Jenis makanan",
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            text = "Pilih jenis makanan yang ingin kamu bagikan",
            style = MaterialTheme.typography.caption.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal
            ),
            color = Color(TextGray.hashCode()),
            modifier = modifier.padding(bottom = 16.dp)
        )
        val radioOptions = listOf("Ayam daging", "Ikan seafood", "Tahu tempe telur", "Sayur",
                                    "Sambal", "Nasi mie pasta", "Sop soto bakso", "Kue roti", "Jajanan pasar", "Puding jeli", "Keripik kerupuk", "Buah minuman")

        var selectedOptions by remember {
            mutableStateOf(radioOptions[0])
        }

        var indexJenisMakanan by remember {
            mutableStateOf("0")
        }

        Column(
            modifier = modifier.selectableGroup()
        ) {
            radioOptions.forEach { label ->
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .selectable(
                            selected = (selectedOptions == label),
                            onClick = {
                                selectedOptions = label
                                indexJenisMakanan = radioOptions
                                    .indexOf(label)
                                    .toString()
                            },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedOptions == label),
                        onClick = null,
                        modifier = modifier.padding(end = 16.dp)
                    )
                    Text(text = label)
                }
            }
        }

        Spacer(modifier = modifier.padding(16.dp))
        Text(
            text = "Lokasi makanan",
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        TextField(
            value = lokasiMakanan,
            onValueChange = {
                lokasiMakanan = it
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 1.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black
            ),
            placeholder = {
                Text(text = "Alamat lengkap kamu")
            }
        )
        Spacer(modifier = modifier.padding(16.dp))
        Text(
            text = "Jumlah makanan",
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        TextField(
            value = jumlahMakanan,
            onValueChange = {
                jumlahMakanan = it
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 1.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black
            ),
            placeholder = {
                Text(text = "jumlah makanan yang ingin kamu bagikan")
            }
        )
        Spacer(modifier = modifier.padding(16.dp))
        Text(
            text = "Tanggal kadaluarsa makanan",
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        TextField(
            value = waktuExpiredMakanan,
            onValueChange = {
                waktuExpiredMakanan = it
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 1.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black
            ),
            placeholder = {
                Text(text = "Tanggal kadaluasra exp 07-07-2023")
            }
        )

        ButtonCustom(text = "Posting makanan", color = MaterialTheme.colors.secondary) {
            viewModel.postFood(context,token, captureImageUri, namaMakanan.text,lokasiMakanan.text,deskripsiMakanan.text,jumlahMakanan.text,indexJenisMakanan,waktuExpiredMakanan.text)
            Toast.makeText(context, "Berhasil posting makanan", Toast.LENGTH_LONG).show()
        }
    }
}